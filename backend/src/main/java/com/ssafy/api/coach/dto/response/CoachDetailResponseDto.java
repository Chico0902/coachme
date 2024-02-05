package com.ssafy.api.coach.dto.response;

import lombok.Data;

@Data
public class CoachDetailResponseDto {
  private String coachName;
  private String coachProfileImageUrl;
  private String coachingName;
  private String htmlDocs;
  private String summary;
}
