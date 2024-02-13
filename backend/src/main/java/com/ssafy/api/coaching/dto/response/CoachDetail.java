package com.ssafy.api.coaching.dto.response;

import lombok.Data;

@Data
public class CoachDetail {
  private long coachingId;
  private String coachingName;
  private String coachingSummary;
  private String coachingMainCategory;
  private String coachingSubCategory;
}