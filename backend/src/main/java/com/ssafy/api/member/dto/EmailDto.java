package com.ssafy.api.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailDto {
  private String email;
  private String title;
  private String message;
}
