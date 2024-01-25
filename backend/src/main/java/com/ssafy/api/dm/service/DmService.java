// DmRoomService.java
package com.ssafy.api.dm.service;

import com.ssafy.api.auth.service.CustomUserDetailsService;
import com.ssafy.api.dm.dto.*;
import com.ssafy.api.dm.mapper.DmMapper;
import com.ssafy.api.dm.repository.DMRepository;
import com.ssafy.api.dm.repository.DmRoomRepository;
import com.ssafy.api.dm.util.RedisUtils;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.DMRoom;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
@Slf4j
public class DmService {
  private final DMRepository dmRepository;
  private final DmRoomRepository dmRoomRepository;
  private final MemberRepository memberRepository;
  private final RedisUtils redisUtils;

  /**
   * DmRoom 입장 요청시 (1:1 문의하기 버튼 클릭시)
   * DmRoom이 없는 경우 새로 생성 후 Id return
   * DmRoom이 있는 경우 조회된 Id return
   *
   * @param : DmRoomEnterDto (long coame 코미 ID PK, long coach)
   * @return : roomId (DmRoom PK)
   */
  public int enterDmRoom(DmRoomEnterDto roomDto) throws Exception{
    Member coame = memberRepository.findById(roomDto.getCoame());
    Member coach = memberRepository.findById(roomDto.getCoach());

    log.debug("roomDto : coach {} / coame {}", coach, coame);

    DMRoom room =  dmRoomRepository.findByCoachAndCoame(coach, coame);

    if(room == null){
      // DmRoom이 없으면 새로운 채팅방 생성
      DMRoom newRoom = DMRoom.builder().coach(coach).coame(coame).build();
      newRoom =  dmRoomRepository.save(newRoom);
      return newRoom.getDmroomId();
    }else{
      return room.getDmroomId();
    }
  }


  /**
   * Dm 내역 조회
   *
   * @param : roomId (DmRoom PK)
   * @return : DmDto (int dmId , long member, String message, LocalDateTime createDate)
   */
  public List<DmDto> getDmList(int roomId) throws Exception{
    // mysql에서 읽어온 dm 내역
    Optional<DMRoom> room =  dmRoomRepository.findById(roomId);
    List<DmDto> mySqlDmList = DmMapper.instance.DmToDmDtoList(dmRepository.findByDmRoom(room.get()));
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

  /**
   * member PK 값으로 해당 멤버가 참여하고 있는 Dm Room 내역 조회
   *
   * @param : id (member PK)
   * @return : DmRoomRequestDto List (int roomId , long coameId, long coachId, LocalDateTime createDate)
   */
  public List<DmRoomRequestDto> getDmRoomList(long id) throws Exception{
    Member member = memberRepository.findById(id);
    List<DmRoomRequestDto> list = DmMapper.instance.dmRoomTodmRoomListRequestDto(dmRoomRepository.findByCoachOrCoame(member, member));

    return list;
  }

  // dm방 삭제 (미완성 아직 삭제 잘 안됨)
  public void deleteDmRoom(DmRoomDeleteDto dmRoomDeleteDto) {
    try {
      Member member = memberRepository.findById(dmRoomDeleteDto.getMember());
      dmRoomRepository.deleteByDmroomIdAndMember(dmRoomDeleteDto.getRoomId(), member);

      log.debug("dmRoomDeleteDto : {}", dmRoomDeleteDto.getRoomId());

    } catch (DataIntegrityViolationException e) {
      // 데이터 무결성 위반 예외 처리
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "데이터 무결성 위반: " + e.getMessage(), e);
    } catch (Exception e) {
      // 기타 예외 처리
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류: " + e.getMessage(), e);
    }
  }




}