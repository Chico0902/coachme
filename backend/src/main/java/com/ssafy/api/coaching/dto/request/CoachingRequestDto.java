package com.ssafy.api.coaching.dto.request;

import lombok.Data;

@Data
public class CoachingRequestDto {
  private String words;
  private long loginMemberId;
}
