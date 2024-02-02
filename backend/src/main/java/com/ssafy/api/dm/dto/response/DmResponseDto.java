package com.ssafy.api.dm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DmResponseDto {
  private long member;
  private String message;
  private LocalDateTime createDate;
}