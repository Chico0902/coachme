package com.ssafy.api.dm.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

// Redis에서 MySql로 저장시 사용되는 Dto
@Builder
@Data
public class DmRedisDto {
  private int roomId;
  private long member;
  private String message;
  private LocalDateTime createDate;
}
