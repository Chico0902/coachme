package com.ssafy.api.dm.service;

import com.ssafy.api.dm.dto.DmRedisDto;
import com.ssafy.api.dm.dto.request.DmRoomEnterRequestDto;
import com.ssafy.api.dm.dto.response.DmResponseDto;
import com.ssafy.api.dm.dto.response.DmRoomEnterResponseDto;
import com.ssafy.api.dm.dto.response.DmRoomResponseDto;
import com.ssafy.api.dm.mapper.DmMapper;
import com.ssafy.api.dm.repository.DMRepository;
import com.ssafy.api.dm.repository.DmRoomRepository;
import com.ssafy.api.dm.util.RedisUtils;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.DMRoom;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
  public DmRoomEnterResponseDto enterDmRoom(DmRoomEnterRequestDto roomDto) throws Exception{
    Member coame = memberRepository.getReferenceById(roomDto.getCoameId());
    Member coach = memberRepository.getReferenceById(roomDto.getCoachId());

    DMRoom dmRoom = dmRoomRepository.findByCoachAndCoame(coach, coame);
    DmRoomEnterResponseDto dmRoomEnterResponseDto = new DmRoomEnterResponseDto();

    if(dmRoom == null){
      // DmRoom이 없으면 새로운 채팅방 생성
      DMRoom newRoom = DMRoom.builder()
          .coame(coame)
          .coach(coach)
          .build();

      newRoom = dmRoomRepository.save(newRoom);
      dmRoomEnterResponseDto.setRoomId(newRoom.getId());
    }else{
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
  public List<DmRoomResponseDto> getDmRoomList(long id) throws Exception{
    List<DMRoom> dmRoomList = new ArrayList<>();
    dmRoomList.addAll(memberRepository.getReferenceById(id).getCoachDmRooms());
    dmRoomList.addAll(memberRepository.getReferenceById(id).getCoameDmRooms());

    return DmMapper.instance.dmRoomTodmRoomRequestDtoList(dmRoomList);
  }

  /**
   * Dm 내역 조회
   *
   * @param : roomId (DmRoom PK)
   * @return : DmResponseDto (long member, String message, LocalDateTime createDate)
   */
  public List<DmResponseDto> getDmList(long roomId) throws Exception{
    // mysql에서 읽어온 dm 내역
    DMRoom room =  dmRoomRepository.getReferenceById(roomId);
    List<DmResponseDto> mySqlDmList = DmMapper.instance.DmToDmResponseDto(dmRepository.findByDmRoom(room));
    log.debug("MySQL list : {} ", mySqlDmList);


    List<String[]> redisDmList = redisUtils.getKeysAndValuesStartingWithPrefix( roomId+ "_");

    for (int i = 0; i < redisDmList.size(); i++) {
      String key = redisDmList.get(i)[0];
      DmRedisDto data = RedisUtils.parser(key);
      data.setMessage(redisDmList.get(i)[1]);
      mySqlDmList.add(DmMapper.instance.redisDtoToDmDto(data));
    }

    return mySqlDmList;
  }
}
