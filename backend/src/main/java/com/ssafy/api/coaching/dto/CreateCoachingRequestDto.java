package com.ssafy.api.coaching.dto;

import com.ssafy.db.entity.Category;
import lombok.Data;
@Data
public class CreateCoachingRequestDto {
  private String main;
  private String sub;
  private String name;
  private String summary;
  private String htmlDocs;
}
