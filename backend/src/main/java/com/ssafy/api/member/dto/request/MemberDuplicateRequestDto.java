package com.ssafy.api.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberDuplicateRequestDto {
  @NotBlank()
  private String stringId;
}
