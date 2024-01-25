package com.ssafy.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class MessageDto {

    private Map<String, String> data = new HashMap<>();

    public MessageDto(String message) {
        data.put("message", message);
    }
}
