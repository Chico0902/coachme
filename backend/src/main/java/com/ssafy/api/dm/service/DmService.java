package com.ssafy.api.dm.service;

import com.ssafy.api.dm.dto.DmRedisDto;
import com.ssafy.api.dm.dto.response.DmListDto;
import com.ssafy.api.dm.dto.response.DmResponseDto;
import com.ssafy.api.dm.dto.response.DmRoomEnterResponseDto;
import com.ssafy.api.dm.dto.response.DmRoomResponseDto;
import com.ssafy.api.dm.mapper.DmMapper;
import com.ssafy.api.dm.repository.DMRepository;
import com.ssafy.api.dm.repository.DmRoomRepository;
import com.ssafy.api.dm.util.RedisUtils;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.DM;
import com.ssafy.db.entity.DMRoom;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DmService {
  private final DMRepository dmRepository;
  private final DmRoomRepository dmRoomRepository;
  private final MemberRepository memberRepository;
  private final RedisUtils redisUtils;

  /**
   * member PK 값으로 해당 멤버가 참여하고 있는 Dm Room 내역 조회
   *
   * @param : id (member PK)
   * @return : DmRoomResponseDto List (long roomId , long coameId, long coachId, LocalDateTime createDate)
   */
  public List<DmRoomResponseDto> getDmRoomList(long id) throws Exception {
    List<DmRoomResponseDto> dmRoomList = new ArrayList<>();
    List<DMRoom> member1 = memberRepository.getReferenceById(id).getMember1DmRooms();
    List<DMRoom> member2 = memberRepository.getReferenceById(id).getMember2DmRooms();

    dmRoomList.addAll(DmMapper.instance.dmRoomToDmRoomMember1ResponseDtoList(member1));
    dmRoomList.addAll(DmMapper.instance.dmRoomToDmRoomMember2ResponseDtoList(member2));

    for (DmRoomResponseDto dmroom : dmRoomList) {
      String lastDm = redisUtils.getLastDm(dmroom.getRoomId() + "_");
      if (lastDm.isBlank()) {
        DM mysqlDm = dmRepository.findByLastDm(dmroom.getRoomId());
        dmroom.setLastDm(mysqlDm != null ? mysqlDm.getMessage() : lastDm);
      } else {

        dmroom.setLastDm(lastDm);
      }
    }
    return dmRoomList;
  }

  /**
   * DmRoom 입장 요청시 (1:1 문의하기 버튼 클릭시)
   * DmRoom이 없는 경우 새로 생성 후 Id return / dmList = null
   * DmRoom이 있는 경우 조회된 Id return      / dmList = 이전 대화 내용 리턴
   *
   * @param : DmRoomEnterDto (long coame 코미 ID PK, long coach)
   * @return : DmRoomEnterResponseDto (long roomId, List<DmResponseDto> dmList)
   */
  public DmRoomEnterResponseDto enterDmRoom(long member1Id, long member2Id) throws Exception {
    Member member1 = memberRepository.findById(member1Id).get();
    Member member2 = memberRepository.findById(member2Id).get();

    DMRoom dmRoom = dmRoomRepository.findByMembers(member1, member2);
    DmRoomEnterResponseDto dmRoomEnterResponseDto = new DmRoomEnterResponseDto();

    if (dmRoom == null) {
      // DmRoom이 없으면 새로운 채팅방 생성
      DMRoom newRoom = DMRoom.builder()
          .member1(member1)
          .member2(member2)
          .build();

      newRoom = dmRoomRepository.save(newRoom);
      dmRoomEnterResponseDto.setDmList(new ArrayList<>());
      dmRoomEnterResponseDto.setRoomId(newRoom.getId());
    } else {
      dmRoomEnterResponseDto.setRoomId(dmRoom.getId());
      dmRoomEnterResponseDto.setDmList(getDmList(dmRoom.getId()));
    }
    return dmRoomEnterResponseDto;
  }

  /**
   * Dm 내역 조회
   *
   * @param : roomId (DmRoom PK)
   * @return : DmResponseDto (long member, String message, LocalDateTime createDate)
   */
  public List<DmResponseDto> getDmList(long roomId) throws Exception {
    // mysql에서 읽어온 dm 내역
    DMRoom room = dmRoomRepository.getReferenceById(roomId);
    List<DmListDto> mySqlDmList = DmMapper.instance.DmToDmResponseDto(dmRepository.findByDmRoom(room));

    List<String[]> redisDmList = redisUtils.getKeysAndValuesStartingWithPrefix(roomId + "_");
    Map<Long, String> memberName = new HashMap<>();
    Map<Long, String> memberProfileUrl = new HashMap<>();

    for (int i = 0; i < redisDmList.size(); i++) {
      String key = redisDmList.get(i)[0];
      System.out.println(key);
      DmRedisDto redis = RedisUtils.parser(key);
      long memberId = redis.getMember();

      DmListDto redisToDmList = new DmListDto();

      if (!memberName.containsKey(memberId)) {
        Member member = memberRepository.getReferenceById(memberId);
        memberName.put(memberId, member.getName());
        memberProfileUrl.put(memberId, member.getProfileImage().getUrl());
      }

      redisToDmList.setMemberId(redis.getMember());
      redisToDmList.setMemberName(memberName.get(memberId));
      redisToDmList.setMemberProfileUrl(memberProfileUrl.get(memberId));
      redisToDmList.setMessage(redisDmList.get(i)[1]);
      redisToDmList.setCreateDate(redis.getCreateDate());
      mySqlDmList.add(redisToDmList);
    }

    mySqlDmList.sort(Comparator.comparing(DmListDto::getCreateDate));

    return groupDmListByMemberAndTime(mySqlDmList);
  }


  public static List<DmResponseDto> groupDmListByMemberAndTime(List<DmListDto> dmListDtoList) {
    Map<Long, Map<String, List<String>>> groupedMap = new HashMap<>();
    Map<Long, String> memberNameMap = new HashMap<>();
    Map<Long, String> memberProfileMap = new HashMap<>();

    Long prevMemberId = null; // 이전 멤버 ID를 추적하기 위한 변수
    String prevCreateTime = null; // 이전 생성 시간을 추적하기 위한 변수

    // 그룹화된 데이터 생성
    for (DmListDto dmListDto : dmListDtoList) {
      Long memberId = dmListDto.getMemberId();
      String memberName = dmListDto.getMemberName();
      String memberUrl = dmListDto.getMemberProfileUrl();
      String createTime = dmListDto.getCreateDate().truncatedTo(ChronoUnit.MINUTES).toString();
      List<String> messageList = groupedMap
          .computeIfAbsent(memberId, k -> new HashMap<>())
          .computeIfAbsent(createTime, k -> new ArrayList<>());

      // 연속된 데이터 중에서 ID가 같은 경우에만 분이 같은 것을 묶어줌
      if (prevMemberId != null && prevMemberId.equals(memberId) && prevCreateTime != null && prevCreateTime.equals(createTime)) {
        // 연속된 데이터의 경우에만 메시지를 추가
        messageList.add(dmListDto.getMessage());
      } else {
        // 이전 데이터와 ID가 다르거나 분이 다른 경우에는 새로운 리스트 생성
        messageList = new ArrayList<>();
        messageList.add(dmListDto.getMessage());
        groupedMap.get(memberId).put(createTime, messageList);

        // 멤버 이름 및 프로필 사진 정보 설정
        memberNameMap.put(memberId, memberName);
        memberProfileMap.put(memberId, memberUrl);
      }

      prevMemberId = memberId;
      prevCreateTime = createTime;
    }

    // DmResponseDto로 변환
    List<DmResponseDto> dmResponseDtoList = new ArrayList<>();
    for (Map.Entry<Long, Map<String, List<String>>> entry : groupedMap.entrySet()) {
      Long memberId = entry.getKey();
      String memberName = memberNameMap.get(memberId);
      String memberProfileUrl = memberProfileMap.get(memberId);
      Map<String, List<String>> timeAndMessagesMap = entry.getValue();

      for (Map.Entry<String, List<String>> timeAndMessagesEntry : timeAndMessagesMap.entrySet()) {
        String createDate = timeAndMessagesEntry.getKey();
        List<String> messages = timeAndMessagesEntry.getValue();

        DmResponseDto dmResponseDto = new DmResponseDto();
        dmResponseDto.setMemberId(memberId);
        dmResponseDto.setMemberName(memberName);
        dmResponseDto.setMemberProfileUrl(memberProfileUrl);
        dmResponseDto.setCreateDate(createDate);
        dmResponseDto.setMessage(messages);

        dmResponseDtoList.add(dmResponseDto);
      }
    }


    return dmResponseDtoList;
  }


}


