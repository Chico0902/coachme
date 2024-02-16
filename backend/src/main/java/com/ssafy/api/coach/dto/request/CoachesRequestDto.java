package com.ssafy.api.coach.dto.request;

import lombok.Data;

@Data
public class CoachesRequestDto {
  private String words;
  private long loginMemberId;
}
