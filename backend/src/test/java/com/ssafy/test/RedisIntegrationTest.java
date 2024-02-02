package com.ssafy.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SpringJUnitConfig
public class RedisIntegrationTest {
  @Autowired
  private StringRedisTemplate stringRedisTemplate;

  // 간단한 Redis 저장 및 검색 테스트
  @org.junit.jupiter.api.Test
  public void saveAndRetrieveDataFromRedis() {
    // Redis에 데이터 저장
    stringRedisTemplate.opsForValue().set("testKey2", "No Config Test!");

    // Redis에서 데이터 검색
    stringRedisTemplate.delete("testKey2");
    String retrievedValue = stringRedisTemplate.opsForValue().get("testKey2");


    // 값 검증
    assertEquals("No Config Test!", retrievedValue);
  }
}
