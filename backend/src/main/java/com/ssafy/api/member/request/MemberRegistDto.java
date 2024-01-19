package com.ssafy.api.member.request;

import lombok.Data;

@Data
public class MemberRegistDto {
//  private Member member;
  private String id;
  private String pw;
  private String name;
  private String nick;
  private String email;

}
