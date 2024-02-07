package com.ssafy.api.dm.controller;

import com.ssafy.api.dm.dto.DmSocketMessage;
import com.ssafy.api.dm.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@Slf4j
@RequiredArgsConstructor
public class DmSocketController {
  private final RedisUtils redisUtils;

  // 디엠방 입장(socket 통신 요청)

  @MessageMapping("/room/{roomId}")
  @SendTo("/topic/room/{roomId}")
  public DmSocketMessage enterDmRoom(DmSocketMessage message, @RequestParam("roomId") String roomId) {
    return message;
  }


  // DM 전송
  @MessageMapping("/sendDm/{roomId}")
  @SendTo("/topic/room/{roomId}")
  public DmSocketMessage sendDm(DmSocketMessage message, @DestinationVariable("roomId") String roomId) {
    String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String key = roomId + "_" + message.getSender() + "_" + time;
    String value = message.getContent();
    redisUtils.save(key, value);

    return message;
  }
}
