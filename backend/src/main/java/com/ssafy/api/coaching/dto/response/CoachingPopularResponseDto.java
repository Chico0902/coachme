package com.ssafy.api.coaching.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoachingPopularResponseDto {
  long coachingId;
  String coachingVideoUrl;
  String coachingName;
  float coachingReviewAvg;
}
