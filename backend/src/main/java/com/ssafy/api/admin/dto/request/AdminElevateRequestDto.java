package com.ssafy.api.admin.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AdminElevateRequestDto {
  @NotEmpty
  private List<Long> ids;
}
