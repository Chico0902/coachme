package com.ssafy.api.coach.dto.response;

import lombok.Data;

@Data
public class CalenderResponseDto {

  private Long id;
  private String className;
  private String date;
  private String time;

}
