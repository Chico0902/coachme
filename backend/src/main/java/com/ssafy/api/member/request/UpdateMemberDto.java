package com.ssafy.api.member.request;

import lombok.Data;
@Data
public class UpdateMemberDto {
  private String pw;
  private String name;
  private String nick;
  private String email;
}
