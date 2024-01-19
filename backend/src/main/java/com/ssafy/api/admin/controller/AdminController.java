package com.ssafy.api.admin.controller;

import com.ssafy.api.admin.dto.MemberListResponseDto;
import com.ssafy.api.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

  private final AdminService adminService;

  @GetMapping("/members")
  public ResponseEntity<Map<String, Object>> showMemberList() {
    Map<String, Object> responseMessage = new HashMap<>();
    HttpStatus status;
    try {
      List<MemberListResponseDto> list = adminService.getMemberList();
      responseMessage.put("data", list);
      status = HttpStatus.OK;
    } catch (Exception e) {
      responseMessage.put("message", "Error !");
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
  }
}
