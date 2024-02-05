package com.ssafy.api.dm.controller;

import com.ssafy.api.dm.dto.request.DmRoomEnterRequestDto;
import com.ssafy.api.dm.dto.response.DmRoomEnterResponseDto;
import com.ssafy.api.dm.service.DmService;
import com.ssafy.dto.ListDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dm")
@RequiredArgsConstructor
@Slf4j
public class DmController {
  private final DmService dmService;

  // 디엠방 생성 API
  @PostMapping("/room/enter")
  public ResponseEntity<DmRoomEnterResponseDto> enterRoom(@RequestBody DmRoomEnterRequestDto dto) throws Exception{
    DmRoomEnterResponseDto enterDmroom = dmService.enterDmRoom(dto);
    return new ResponseEntity<>(enterDmroom, HttpStatus.OK);
  }


  // 디엠방 조회 API
  @GetMapping("/room/{memberId}")
  public ResponseEntity<ListDataDto> getDmRoom(@PathVariable int memberId) throws Exception {
    ListDataDto dto = new ListDataDto(dmService.getDmRoomList(memberId));
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  // 디엠 조회 API
  @GetMapping("/{roomId}")
  public ResponseEntity<ListDataDto> getDmList(@PathVariable long roomId) throws Exception{
    ListDataDto dto = new ListDataDto(dmService.getDmList(roomId));
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }
}
