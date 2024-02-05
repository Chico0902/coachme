package com.ssafy.api.admin.dto.response;

import com.ssafy.db.entity.status.MemberStatus;
import com.ssafy.db.entity.type.Privilege;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
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
}