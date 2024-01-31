package com.ssafy.util.file.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class FileRequestDto {
  List<Long> fileId;
}
