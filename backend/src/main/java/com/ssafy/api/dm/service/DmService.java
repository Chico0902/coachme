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
   * member PK 값으로 해당 멤버가 참여하고 있는 Dm Room 내역 조회
   *
   * @param : id (member PK)
   * @return : DmRoomResponseDto List (long roomId , long coameId, long coachId, LocalDateTime createDate)
   */
  public List<DmRoomResponseDto> getDmRoomList(long memberId) throws Exception {
    List<DmRoomResponseDto> dmRoomList = new ArrayList<>();
    List<DMRoom> dmrooms = dmRoomRepository.findByMemberId(memberId);

    for (DMRoom dmRoom : dmrooms) {
      DmRoomResponseDto dto = new DmRoomResponseDto();
      dto.setRoomId(dmRoom.getId());
      if (dmRoom.getMember1().getLongId() == memberId) {
        dto.setMemberName(dmRoom.getMember2().getName());
        dto.setMemberProfileUrl(dmRoom.getMember2().getProfileImage().getUrl());
      } else {
        dto.setMemberName(dmRoom.getMember1().getName());
        dto.setMemberProfileUrl(dmRoom.getMember1().getProfileImage().getUrl());
      }
      String lastDm = redisUtils.getLastDm(dmRoom.getId() + "_");
      if (lastDm == null) {
        DM mysqlDm = dmRepository.findByLastDm(dmRoom.getId());
        dto.setLastDm(mysqlDm != null ? mysqlDm.getMessage() : "");
      } else {
        dto.setLastDm(lastDm);
      }
      dmRoomList.add(dto);
    }

    return dmRoomList;
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

    // Redis에서 읽어온 dm 내역
    List<String[]> redisDmList = redisUtils.getKeysAndValuesStartingWithPrefix(roomId + "_");
    Map<Long, String> memberName = new HashMap<>();
    Map<Long, String> memberProfileUrl = new HashMap<>();

    // Redis DM 목록 처리
    for (int i = 0; i < redisDmList.size(); i++) {
      String key = redisDmList.get(i)[0];
      DmRedisDto redis = RedisUtils.parser(key);
      long memberId = redis.getMember();

      DmListDto redisToDmList = new DmListDto();

      // 멤버 정보가 없는 경우 추가
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

  // DM 목록을 멤버와 시간에 따라 그룹화하는 메소드
  public static List<DmResponseDto> groupDmListByMemberAndTime(List<DmListDto> dmListDtoList) {
    // 그룹화된 데이터를 담을 맵
    Map<String, String> memberInfo = new HashMap<>();
    List<String> msgList = new ArrayList<>();
    List<DmResponseDto> dmResponseDtoList = new ArrayList<>();

    // 이전 멤버 이름과 이전 생성 시간을 추적하기 위한 변수들
    long prevMemberId = -1;
    String prevCreateTime = null;

    // DM 목록을 순회하며 그룹화
    for (DmListDto dmListDto : dmListDtoList) {
      long memberId = dmListDto.getMemberId();
      String createTime = dmListDto.getCreateDate().truncatedTo(ChronoUnit.MINUTES).toString();
      if (memberId != prevMemberId || !createTime.equals(prevCreateTime)) {
        if (prevMemberId != -1) {
          // 이전 내역 리스트에 담기
          DmResponseDto dto = new DmResponseDto();
          dto.setMemberId(prevMemberId);
          dto.setMemberName(memberInfo.get("memberName"));
          dto.setMemberProfileUrl(memberInfo.get("memberProfileUrl"));
          dto.setCreateDate(memberInfo.get("createDate"));
          dto.setMessage(msgList);
          dmResponseDtoList.add(dto);
        }

        // 새로운 내역 리스트에 담기
        memberInfo = new HashMap<>();
        memberInfo.put("memberName", dmListDto.getMemberName());
        memberInfo.put("memberProfileUrl", dmListDto.getMemberProfileUrl());
        memberInfo.put("createDate", dmListDto.getCreateDate().truncatedTo(ChronoUnit.MINUTES).toString());

        msgList = new ArrayList<>();
        msgList.add(dmListDto.getMessage());
      } else {
        msgList.add(dmListDto.getMessage());
      }
      prevMemberId = memberId;
      prevCreateTime = createTime;
    }

    if (prevMemberId != -1) {
      DmResponseDto dto = new DmResponseDto();
      dto.setMemberId(prevMemberId);
      dto.setMemberName(memberInfo.get("memberName"));
      dto.setMemberProfileUrl(memberInfo.get("memberProfileUrl"));
      dto.setCreateDate(memberInfo.get("createDate"));
      dto.setMessage(msgList);
      dmResponseDtoList.add(dto);
    }
    return dmResponseDtoList;
  }
}


