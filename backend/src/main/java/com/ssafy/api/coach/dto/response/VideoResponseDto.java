package com.ssafy.api.coach.dto.response;

import lombok.Data;

@Data
public class VideoResponseDto {
  private Long videoId;
  private String videoName;
  private String url;
  private Long coachingId;
  private String coachingName;
}
