package com.ssafy.api.coach.dto.response;

import lombok.Data;

@Data
public class CoachesResponseDto {
  private Long coachId;
  private String profileImg;
  private String memberName;
  private Float avgScore;
  private int reviewCount;
  private String lessonInfo;
}
