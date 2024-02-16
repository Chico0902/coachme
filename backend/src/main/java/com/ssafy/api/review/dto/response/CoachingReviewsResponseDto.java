package com.ssafy.api.review.dto.response;

import lombok.Data;

@Data
public class CoachingReviewsResponseDto {
  private long reviewId;
  private long coameId;
  private long coachingId;
  private String comment;
  private int score;
  private String nickName;
}
