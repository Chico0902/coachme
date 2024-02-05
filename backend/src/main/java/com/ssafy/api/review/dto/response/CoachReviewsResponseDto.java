package com.ssafy.api.review.dto.response;

import lombok.Data;

@Data
public class CoachReviewsResponseDto {
  private long coameId;
  private long coachId;
  private String comment;
  private int score;
}
