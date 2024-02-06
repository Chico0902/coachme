package com.ssafy.api.member.dto.response;

import lombok.Data;

@Data
public class CalendarResponseDto {

  private Long id;
  private String className;
  private String date;
  private String time;

}
