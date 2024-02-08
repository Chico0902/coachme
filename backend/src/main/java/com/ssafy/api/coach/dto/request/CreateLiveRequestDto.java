package com.ssafy.api.coach.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateLiveRequestDto {

  private Long coachingId;
  private LocalDateTime date;

}
