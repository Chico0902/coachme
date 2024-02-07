package com.ssafy.api.dm.dto.request;

import lombok.Data;

// DmRoom 입장에 사용되는 DTO
@Data
public class DmRoomEnterRequestDto {
  private long member1;
  private long member2;
}
