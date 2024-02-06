package com.ssafy.api.coach.dto.response;

public interface CoachesResponseDtos {

  Long getCoachId();

  String getProfileImg();

  String getMemberName();

  Long getAvgScore();

  Long getReviewCount();

  String getLessonInfo();
}