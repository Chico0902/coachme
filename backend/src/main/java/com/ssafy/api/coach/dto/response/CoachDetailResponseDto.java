package com.ssafy.api.coach.dto.response;

import com.ssafy.api.coaching.dto.response.CoachDetail;
import lombok.Data;

import java.util.List;

@Data
public class CoachDetailResponseDto {
  private String coachName;
  private String coachProfileImageUrl;

  private String portFolioHtmlDocs;

  private List<CoachDetail> list;

  private long reviewCount;
  private float reviewAvg;
  private long likeCount;
}

