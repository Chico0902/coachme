package com.ssafy.api.admin.controller;

import com.ssafy.api.admin.dto.response.AdminElevationsResponseDto;
import com.ssafy.api.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

  private final AdminService adminService;

  /**
   * [admin-2] 권한 상승요청 목록을 조회한다.
   * privilege : 3
   * @return [200] 정상 조회완료
   */
  @GetMapping("/privileges/elevations")
  public ResponseEntity<Map<String, List<AdminElevationsResponseDto>>> showElevationList() throws Exception {
    Map<String, List<AdminElevationsResponseDto>> response = new HashMap<>();
    // 코치 상승요청 리스트 담기(service)
    response.put("data", adminService.getRequestedElevationList());
    // 정상 등록완료(200)
    return ResponseEntity.ok(response);
  }
}
