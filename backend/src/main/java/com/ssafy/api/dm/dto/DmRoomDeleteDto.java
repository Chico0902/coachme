package com.ssafy.api.dm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// DmRoom 삭제에 사용되는 DTO
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DmRoomDeleteDto {
  private int roomId;
  private long member;
}
