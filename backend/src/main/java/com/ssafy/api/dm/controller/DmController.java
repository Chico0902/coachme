package com.ssafy.api.dm.controller;

import com.ssafy.api.dm.dto.response.DmRoomEnterResponseDto;
import com.ssafy.api.dm.service.DmService;
import com.ssafy.dto.ListDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dm")
@RequiredArgsConstructor
@Slf4j
public class DmController {
  private final DmService dmService;

  // 디엠방 조회 API
  @GetMapping("/room/{memberId}")
  public ResponseEntity<ListDataDto> getDmRoom(@PathVariable int memberId) throws Exception {
    ListDataDto dto = new ListDataDto(dmService.getDmRoomList(memberId));
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  // 디엠 조회 API
  @GetMapping("/{roomId}")
  public ResponseEntity<ListDataDto> getDmList(@PathVariable long roomId) throws Exception {
    ListDataDto dto = new ListDataDto(dmService.getDmList(roomId));
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  // 디엠방 생성 API
  @GetMapping("/room/enter/{member1Id}/{member2Id}")
  public ResponseEntity<DmRoomEnterResponseDto> enterRoom(@PathVariable("member1Id") long member1Id,
                                                          @PathVariable("member2Id") long member2Id) throws Exception {
    DmRoomEnterResponseDto enterDmroom = dmService.enterDmRoom(member1Id, member2Id);
    return new ResponseEntity<>(enterDmroom, HttpStatus.OK);
  }
}
