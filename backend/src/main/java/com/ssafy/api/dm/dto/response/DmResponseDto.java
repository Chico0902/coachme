package com.ssafy.api.dm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DmResponseDto {
  private long memberId;
  private String memberName;
  private String memberProfileUrl;
  private String message;
}