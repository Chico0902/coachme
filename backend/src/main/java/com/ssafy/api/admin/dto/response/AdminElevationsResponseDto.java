package com.ssafy.api.admin.dto.response;

import com.ssafy.db.entity.Member;
import lombok.Data;

@Data
public class AdminElevationsResponseDto {

  private Long longId;
  private String name;
  private String portfolio;

  public AdminElevationsResponseDto(Member member) {
    this.longId = member.getLongId();
    this.name = member.getName();
    this.portfolio = member.getPortfolio().getHtmlDocs();
  }

  ;
}
