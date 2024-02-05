package com.ssafy.api.member.dto.response;

import com.ssafy.db.entity.Member;
import lombok.Data;

@Data
public class MemberInfoResponseDto {
  private String stringId;
  private String name;
  private String nick;
  private String email;

  public MemberInfoResponseDto(Member member) {
    this.stringId = member.getStringId();
    this.name = member.getName();
    this.nick = member.getNickName();
    this.email = member.getEmail();
  }
}
