package com.ssafy.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenInfoDto {
  private Long longId;
  private String accessToken;
  private String refreshToken;
}
