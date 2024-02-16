package com.ssafy.api.dm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// DmRoom 조회에 사용되는 DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DmRoomResponseDto {
  private long roomId;
  private String memberProfileUrl;
  private String memberName;
  private String lastDm;
}