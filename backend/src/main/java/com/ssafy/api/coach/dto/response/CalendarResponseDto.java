package com.ssafy.api.coach.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CalendarResponseDto {

  private Long id;
  private String className;
  private LocalDateTime date;
  private Boolean status;
  private Long coachId;
}
