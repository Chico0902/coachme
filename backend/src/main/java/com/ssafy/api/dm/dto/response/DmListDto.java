package com.ssafy.api.dm.dto.response;

import lombok.Data;

@Data
public class DmListDto {
  private long memberId;
  private String memberName;
  private String memberProfileUrl;
  private String message;
  private String createDate;
}
