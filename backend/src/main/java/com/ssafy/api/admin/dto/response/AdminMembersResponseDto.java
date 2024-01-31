package com.ssafy.api.admin.dto.response;

import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.status.MemberStatus;
import com.ssafy.db.entity.type.Privilege;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Slf4j
public class AdminMembersResponseDto {

  private String stringId;
  private Privilege priv;
  private String name;
  private String nick;
  private String email;
  private LocalDateTime cdate;
  private LocalDateTime mdate;
  private MemberStatus status;

//
//  public AdminMembersResponseDto(Member member) {
//    log.info("member : {}", member);
//    this.stringId = member.getStringId();
//    this.priv = member.getPrivilege();
//    this.name = member.getName();
//    this.nick = member.getNickName();
//    this.email = member.getEmail();
//    this.cdate = member.getCreateDate();
//    this.mdate = member.getModifyDate();
//    this.status = member.getStatus();
//  };
}