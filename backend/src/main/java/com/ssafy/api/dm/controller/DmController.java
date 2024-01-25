package com.ssafy.api.dm.controller;

import com.ssafy.api.dm.dto.DmDto;
import com.ssafy.api.dm.dto.DmRoomDeleteDto;
import com.ssafy.api.dm.dto.DmRoomEnterDto;
import com.ssafy.api.dm.dto.DmRoomRequestDto;
import com.ssafy.api.dm.service.DmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dm")
@RequiredArgsConstructor
@Slf4j
public class DmController {
  
  private final DmService dmService;
  
  // 디엠방 생성 API
  @GetMapping("/room/enter")
  public ResponseEntity<Map<String, Object>> enterRoom(@RequestBody DmRoomEnterDto requestDto) {
    Map<String, Object> responseMessage = new HashMap<>();
    HttpStatus status;
    try {
      int RoomId = dmService.enterDmRoom(requestDto);
      List<DmDto> dmList = dmService.getDmList(RoomId);

      if(dmList == null){
        responseMessage.put("message", "채팅방 생성 완료");
        responseMessage.put("dmRoomId", RoomId);
        status = HttpStatus.OK;
      }else{
        responseMessage.put("message", "채팅방 이미 있음");
        responseMessage.put("dmRoomId", RoomId);
        responseMessage.put("dmList", dmList);
        status = HttpStatus.OK;
      }

    }catch (Exception e){
      e.printStackTrace();
      responseMessage.put("message", e.getMessage());
      status = HttpStatus.FORBIDDEN;
    }

    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
  }


  // 디엠방 조회 API
  @GetMapping("/room/{id}")
  public ResponseEntity<Map<String, Object>> getDmRoom(@PathVariable int id) {
    Map<String, Object> responseMessage = new HashMap<>();
    HttpStatus status;
    try {
      List<DmRoomRequestDto> dmRoomList = dmService.getDmRoomList(id);
      responseMessage.put("message", "디엠방 조회 성공");
      responseMessage.put("dmRoomList", dmRoomList);
      status = HttpStatus.OK;
    }catch (Exception e){
      e.printStackTrace();
      responseMessage.put("message", e.getMessage());
      status = HttpStatus.FORBIDDEN;
    }

    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<Map<String, Object>> getDmList(@PathVariable int id) {
    Map<String, Object> responseMessage = new HashMap<>();
    HttpStatus status;
    try {
      List<DmDto> dmList = dmService.getDmList(id);
      responseMessage.put("message", "디엠 내역 조회 성공");
      responseMessage.put("dmList", dmList);
      status = HttpStatus.OK;
    }catch (Exception e){
      e.printStackTrace();
      responseMessage.put("message", e.getMessage());
      status = HttpStatus.FORBIDDEN;
    }

    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
  }


  // 디엠방 삭제 API (미완성) - 명세에 없음
  @DeleteMapping("/room")
  public ResponseEntity<Map<String, Object>> deleteRoom(DmRoomDeleteDto dmRoomDeleteDto) {
    Map<String, Object> responseMessage = new HashMap<>();
    HttpStatus status;
    try {
      dmService.deleteDmRoom(dmRoomDeleteDto);
      responseMessage.put("dmRoomList", "DM Room 삭제 성공");
      status = HttpStatus.OK;
    }catch (Exception e){
      e.printStackTrace();
      responseMessage.put("message", e.getMessage());
      status = HttpStatus.FORBIDDEN;
    }

    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
  }
}
