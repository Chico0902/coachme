package com.ssafy.api.admin.dto;

import lombok.Data;

@Data
public class MemberListResponseDto {

  private String id;
  private String priv;
  private String name;
  private String nick;
  private String phone;
  private String email;
  private String cdate;
  private boolean elev;

}
