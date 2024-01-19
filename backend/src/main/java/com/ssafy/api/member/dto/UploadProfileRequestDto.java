package com.ssafy.api.member.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadProfileRequestDto {

  private MultipartFile file;
  private String memberId;

}
