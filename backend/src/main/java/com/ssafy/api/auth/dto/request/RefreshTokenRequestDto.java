package com.ssafy.api.auth.dto.request;

import lombok.Data;

@Data
public class RefreshTokenRequestDto {
  private String stringId;
  private String longId;
  private String privilege;
  private String name;
}
