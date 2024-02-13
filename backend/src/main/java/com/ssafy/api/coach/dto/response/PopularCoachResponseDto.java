package com.ssafy.api.coach.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PopularCoachResponseDto {
  long coachId;
  String coachProfileImageUrl;
  String coachName;
  float coachingReviewAvg;
  String coachingInfo;
}
