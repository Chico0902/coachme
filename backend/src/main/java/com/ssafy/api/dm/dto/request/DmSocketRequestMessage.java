package com.ssafy.api.dm.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

// 소켓 통신시 사용되는 메세지 DTO 클래스
@Data
@NoArgsConstructor
public class DmSocketRequestMessage {

  private String content;
  private String sender;

  // JSON 형식의 문자열로 직렬화
  public String toJson() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(this);
  }

  // JSON 문자열을 DmMessage 객체로 역직렬화
  public static DmSocketRequestMessage fromJson(String json) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, DmSocketRequestMessage.class);
  }
}
