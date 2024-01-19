package com.ssafy.api.member.dto;

import lombok.Data;

@Data
public class RegistMemberDto {

  private String id;
  private String pw;
  private String name;
  private String nick;
  private String email;

}
