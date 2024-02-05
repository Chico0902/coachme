package com.ssafy.api.auth.dto.request;

import lombok.Data;

@Data
public class LoginRequestDto {

  private String stringId;
  private String pw;

}
