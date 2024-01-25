package com.ssafy.api.dto.request;

import lombok.Data;

@Data
public class TokenResponseDto {

  private String accessToken;
  private String refreshToken;

}
