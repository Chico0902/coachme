package com.ssafy.api.livecoaching.dto.response;

import lombok.Data;

@Data
public class LiveCoachingMemberResponseDto {
  private String token;
  private long memberId;
  private String memberProfileUrl;
  private String memberName;
  private String memberProfileText;
}
