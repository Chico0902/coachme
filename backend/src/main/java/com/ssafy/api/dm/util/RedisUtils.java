package com.ssafy.api.dm.util;

import com.ssafy.api.dm.dto.DmRedisDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RedisUtils {

  private final StringRedisTemplate stringRedisTemplate;

  public void save(String key, String value) {
    stringRedisTemplate.opsForValue().set(key, value);
  }

  public String get(String key) {
    return stringRedisTemplate.opsForValue().get(key);
  }

  public void delete(String key) {
    stringRedisTemplate.delete(key);
  }

  // 특정 pattern 포함하는 key의 값 리턴
  public List<String[]> getKeysAndValuesStartingWithPrefix(String prefix) {
    String pattern = prefix + "*";
    List<String[]> keysAndValuesList = new ArrayList<>();

    ScanOptions options = ScanOptions.scanOptions().match(pattern).build();

    try (var cursor = stringRedisTemplate.scan(options)) {
      while (cursor.hasNext()) {
        String key = cursor.next();
        String value = stringRedisTemplate.opsForValue().get(key);

        String[] keyAndValue = new String[]{key, value};

        keysAndValuesList.add(keyAndValue);
      }
    }

    return keysAndValuesList;
  }

  public String getLastDm(String prefix) {
    String pattern = prefix + "*";
    ScanOptions options = ScanOptions.scanOptions().match(pattern).build();
    String lastDm = null; // 가장 최근 value를 저장할 변수
    String lastTime = null; // 가장 최근 시간을 저장할 변수

    try (var cursor = stringRedisTemplate.scan(options)) {
      while (cursor.hasNext()) {
        String key = cursor.next();
        String[] parts = key.split("_");
        String timestamp = parts[parts.length - 1];
        if (lastTime == null || timestamp.compareTo(lastTime) > 0) {
          lastTime = timestamp; // 가장 최근 시간 업데이트
          lastDm = stringRedisTemplate.opsForValue().get(key);
        }
      }
    }
    return lastDm;
  }

  public static DmRedisDto parser(String data) {
    Pattern pattern = Pattern.compile("(\\d+)_(\\w+)_(\\d+)");
    Matcher matcher = pattern.matcher(data);

    if (matcher.matches()) {
      // 매칭된 부분을 추출하여 객체로 반환
      int roomId = Integer.parseInt(matcher.group(1));
      Long sender = Long.parseLong(matcher.group(2));
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
      LocalDateTime time = LocalDateTime.parse(matcher.group(3), formatter);

      DmRedisDto dm = DmRedisDto.builder()
          .roomId(roomId)
          .member(sender)
          .createDate(time)
          .build();

      return dm;
    } else {
      return null; // 매칭되지 않으면 null 반환
    }

  }
}