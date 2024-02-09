package com.ssafy.api.coaching.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoachingPopularResponseDto {
  long coacingId;
  String coacingVideoUrl;
  String coacingName;
  float coacingReviewAvg;
}
