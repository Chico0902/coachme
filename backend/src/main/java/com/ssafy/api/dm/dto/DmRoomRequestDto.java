package com.ssafy.api.dm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// DmRoom 조회에 사용되는 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DmRoomRequestDto {
  private int roomId;
  private long coameId;
  private long coachId;
  private LocalDateTime createDate;
}