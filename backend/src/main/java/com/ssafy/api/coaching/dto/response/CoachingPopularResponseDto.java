package com.ssafy.api.coaching.dto.response;

import lombok.Data;

@Data
public class CoachingPopularResponseDto {
  long coacingId;
  String coacingVideoUrl;
  String coacingName;
  float coacingReviewAvg;

  CoachingPopularResponseDto(long coacingId, String coacingName, double coacingReviewAvg) {
    this.coacingId = coacingId;
    this.coacingName = coacingName;
    this.coacingReviewAvg = (float) coacingReviewAvg;
  }
}
