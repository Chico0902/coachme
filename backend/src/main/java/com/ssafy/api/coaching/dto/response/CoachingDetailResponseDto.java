package com.ssafy.api.coaching.dto.response;

import lombok.Data;

@Data
public class CoachingDetailResponseDto {
  private String coachName;
  private String coachProfileImageUrl;
  private String coachingName;
  private String htmlDocs;
  private String summary;

  private long reviewCount;
  private float reviewAvg;
  private long likeCount;

}
