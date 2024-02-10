package com.ssafy.api.coaching.dto.response;

public interface CoachingResponseDtos {
  Long getCoachId();
  String getMemberName();
  String getProfileImg();
  Long getCoachingId();
  String getCoachingName();
  Long getReviewCount();
  Float getAvgScore();
}