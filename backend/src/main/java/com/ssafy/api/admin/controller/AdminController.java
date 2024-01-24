package com.ssafy.api.admin.controller;

import com.ssafy.api.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

  private final AdminService adminService;

  // [admin-1] 전체 회원 정보 조회
  @GetMapping("/members")
  public ResponseEntity<Map<String, Object>> showMemberList() {
    Map<String, Object> responseMessage = new HashMap<>();
    HttpStatus status;
    try {
      responseMessage.put("data", adminService.getMemberList());
      status = HttpStatus.OK;
    } catch (Exception e) {
      responseMessage.put("message", "Error !!");
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
  }

  // [admin-2] 권한 상승 요청한 회원 정보 조회
  @GetMapping("/privileges/elevations")
  public ResponseEntity<Map<String, Object>> showElevationList() {
    Map<String, Object> responseMessage = new HashMap<>();
    HttpStatus status;
    try {
      responseMessage.put("data", adminService.getElevList());
      status = HttpStatus.OK;
    } catch (Exception e) {
      responseMessage.put("message", "Error !!");
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
  }
}
