package com.ssafy.api.review.dto.request;

import lombok.Data;

@Data
public class ReviewUpdateRequestDto {
  private String comment;
  private int score;
}
