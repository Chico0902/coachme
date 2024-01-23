package com.ssafy.api.member.dto;

import lombok.Data;

@Data
public class UpdateMemberDto {
  private String name;
  private String nick;
  private String email;
  private String pw;
}
