package com.ssafy.api.member.dto.request;

import lombok.Data;

@Data
public class MemberInfoChangeRequestDto {
  private String pw;
  private String nick;
  private String email;
}
