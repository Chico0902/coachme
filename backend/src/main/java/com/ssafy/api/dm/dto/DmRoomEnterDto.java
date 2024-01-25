package com.ssafy.api.dm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// DmRoom 입장에 사용되는 DTO
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DmRoomEnterDto {
  private long coame;
  private long coach;
}
