package com.ssafy.api.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileResponseDto {
  private String profileText;
  private String profileImageUrl;
}
