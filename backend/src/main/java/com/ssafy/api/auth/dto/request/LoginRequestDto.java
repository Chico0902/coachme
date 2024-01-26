package com.ssafy.api.auth.dto.request;

import lombok.Data;

@Data
public class LoginRequestDto {

  private String id;
  private String pw;

}
