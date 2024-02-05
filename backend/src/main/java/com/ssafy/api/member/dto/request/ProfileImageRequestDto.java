package com.ssafy.api.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfileImageRequestDto {
  @NotBlank
  MultipartFile profileImage;
}
