package com.ssafy.api.coaching.dto.response;

import lombok.Data;

@Data
public class CoachingResponseDto {
  private Long coachId;
  private String memberName;
  private String profileImg;
  private Long coachingId;
  private String coachingName;
  private int reviewCount;
  private Float avgScore;

}