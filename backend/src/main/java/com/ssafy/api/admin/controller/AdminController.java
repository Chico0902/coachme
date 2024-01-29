package com.ssafy.api.admin.controller;

import com.ssafy.api.admin.dto.request.AdminElevateRequestDto;
import com.ssafy.api.admin.service.AdminService;
import com.ssafy.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

  private final AdminService adminService;

  // [admin-1] 전체 회원 정보 조회
//  @GetMapping("/members")
//  public ResponseEntity<Map<String, Object>> showMemberList() {
//    Map<String, Object> responseMessage = new HashMap<>();
//    HttpStatus status;
//    try {
//      responseMessage.put("data", adminService.getMemberList());
//      status = HttpStatus.OK;
//    } catch (Exception e) {
//      responseMessage.put("message", "Error !!");
//      status = HttpStatus.INTERNAL_SERVER_ERROR;
//    }
//    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
//  }

  // [admin-2] 권한 상승 요청한 회원 정보 조회
//  @GetMapping("/privileges/elevations")
//  public ResponseEntity<Map<String, Object>> showElevationList() {
//    Map<String, Object> responseMessage = new HashMap<>();
//    HttpStatus status;
//    try {
//      responseMessage.put("data", adminService.getElevList());
//      status = HttpStatus.OK;
//    } catch (Exception e) {
//      responseMessage.put("message", "Error !!");
//      status = HttpStatus.INTERNAL_SERVER_ERROR;
//    }
//    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
//  }

  /**
   * [admin-3] 해당 아이디의 권한을 1에서 2로 상승
   * privilege : 3
   * @return [200] 정상 요청완료
   */
  @PatchMapping("/privileges/elevations")
  public ResponseEntity<MessageDto> showElevationList(@Validated @RequestBody AdminElevateRequestDto dto) throws Exception{
    // 권한 요청 등록(service)
    adminService.requestElevatePermission(dto);

    return new ResponseEntity<>(new MessageDto("Permission elevation request successfully completed"), HttpStatus.OK);
  }
}
