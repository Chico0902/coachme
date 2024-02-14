package com.ssafy.api.livecoaching.dto.request;

import lombok.Data;

@Data
public class StartRecordingRequestDto {
  private String sessionId;
  private boolean hasAudio;
  private boolean hasVideo;
  private String outputMode;

}
