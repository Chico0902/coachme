package com.ssafy.api.dm.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 소켓 통신시 사용되는 메세지 DTO 클래스
@Data
@NoArgsConstructor
public class DmSocketResponseMessage {

  private long memberId;
  private String memberName;
  private String memberProfileUrl;
  private List<String> message;
  private String createDate;

  // JSON 형식의 문자열로 직렬화
  public String toJson() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(this);
  }

  // JSON 문자열을 DmMessage 객체로 역직렬화
  public static DmSocketResponseMessage fromJson(String json) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, DmSocketResponseMessage.class);
  }
}
