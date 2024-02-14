package com.ssafy.api.coaching.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LiveCoachingsResponseDto {

  private Long id;
  private LocalDateTime date;
  private Boolean status;

}
