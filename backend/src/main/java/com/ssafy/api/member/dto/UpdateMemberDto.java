package com.ssafy.api.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateMemberDto {
  private String pw;
  private String name;
  private String nick;
  private String email;
}