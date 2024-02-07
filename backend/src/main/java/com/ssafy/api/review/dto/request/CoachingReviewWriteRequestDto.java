package com.ssafy.api.review.dto.request;

import lombok.Data;

@Data
public class CoachingReviewWriteRequestDto {

  private long coameId;
  private long coachingId;
  private String comment;
  private int score;

}