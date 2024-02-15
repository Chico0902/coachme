package com.ssafy.api.coaching.dto.request;

import lombok.Data;

@Data
public class VideoRequestDto {

  private Long coachId;
  private String videoName;
  private String url;

}
