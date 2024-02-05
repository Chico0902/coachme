package com.ssafy.api.review.dto.request;

import lombok.Data;

@Data
public class CoachReviewWriteRequestDto {
  private long coameId;
  private long coachId;
  private String comment;
  private int score;
}
