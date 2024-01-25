package com.ssafy.api.dm.util;

import com.ssafy.api.dm.dto.DmRedisDto;
import com.ssafy.api.dm.repository.DMRepository;
import com.ssafy.api.dm.repository.DmRoomRepository;
import com.ssafy.db.entity.DM;
import com.ssafy.db.entity.DMRoom;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisScheduledTask {

  private final StringRedisTemplate stringRedisTemplate;
  private final DMRepository dmRepository;
  private final DmRoomRepository dmRoomRepository;
  private static final int PAGE_SIZE = 100; // 페이지 크기
  private static List<Long> memberList = new ArrayList<>();
  private static List<Integer> dmRoomList = new ArrayList<>();

  @Transactional
  @Scheduled(cron = "0 0 */6 * * *") // 매 6시간마다 실행
  public void myTask() {
    log.debug("배치 프로그램 실행");
    log.debug("6시간마다 Redis에서 모든 데이터 받아와서 MySql에 저장");

    List<DmRedisDto> dmList = new ArrayList<>();

    try {
      ScanOptions scanOptions = ScanOptions.scanOptions().match("*").count(PAGE_SIZE).build();
      Cursor<String> cursor = stringRedisTemplate.scan(scanOptions);

      int count = 0;

      while (cursor.hasNext()) {
        String keyBytes = cursor.next();
        String key = new String(keyBytes);
        String value = stringRedisTemplate.opsForValue().get(key);

        DmRedisDto data = RedisUtils.parser(key);
        memberList.add(data.getMember());
        dmRoomList.add(data.getRoomId());
        if(!"".equals(value)){
          data.setMessage(value);
        }
        log.debug("파싱한 데이터 확인 {}", data);
        dmList.add(data);
        count++;
      }

      if (count == 0) {
        log.info("No data available. Task completed.");
      }else{
        List<DMRoom> roomLi = dmRoomRepository.findByDmroomIdIn(dmRoomList);
        Map<Integer, DMRoom> roomMap = new HashMap<>();
        for (int i = 0; i < roomLi.size(); i++) {
          roomMap.put(roomLi.get(i).getDmroomId(), roomLi.get(i));
          log.debug("RoomList : {}", roomLi.get(i).getDmroomId());
        }

        List<DM> saveDmList = new ArrayList<>();
        for (int i = 0; i < dmList.size(); i++) {
          DMRoom room = roomMap.get(dmList.get(i).getRoomId());
          Member member = room.getCoach().getId() == dmList.get(i).getMember() ?
              room.getCoach() : room.getCoame();
          DM newDm = DM.builder()
              .dmRoom(room)
              .member(member)
              .message(dmList.get(i).getMessage())
              .createDate(dmList.get(i).getCreateDate())
              .build();
          saveDmList.add(newDm);
        }

        dmRepository.saveAll(saveDmList);

        stringRedisTemplate.delete(stringRedisTemplate.keys("*"));
      }

    } catch (Exception e) {
      log.error("An unexpected error occurred.", e);
    }
  }



}
