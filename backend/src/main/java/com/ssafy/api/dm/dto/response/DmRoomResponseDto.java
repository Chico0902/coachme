package com.ssafy.api.dm.dto.response;

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
public class DmRoomResponseDto {
  private int roomId;
  private String img;
  private String name;
  private String lastDm;
}