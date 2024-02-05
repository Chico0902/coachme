package com.ssafy.api.admin.controller;

import com.ssafy.api.admin.dto.request.AdminElevateRequestDto;
import com.ssafy.api.admin.dto.response.AdminElevationsResponseDto;
import com.ssafy.api.admin.dto.response.AdminMembersResponseDto;
import com.ssafy.api.admin.service.AdminService;
import com.ssafy.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
   * [admin-1] 모든 회원정보를 받아온다.[List]
   * privilege : 3
   *
   * @return [200] 정상 조회완료
   */
  @GetMapping("/members")
  public ResponseEntity<Map<String, List<AdminMembersResponseDto>>> showAllMembersList() throws Exception {
    Map<String, List<AdminMembersResponseDto>> response = new HashMap<>();
    // 코치 상승요청 리스트 담기(service)
    response.put("data", adminService.getAllMembersList());
    // 정상 등록완료(200)
    return ResponseEntity.ok(response);
  }

  /**
   * [admin-2] 권한 상승요청 목록을 조회한다.
   * privilege : 3
   *
   * @return [200] 정상 조회완료
   */
  @GetMapping("/privileges/elevations")
  public ResponseEntity<?> showElevationList() throws Exception {
    Map<String, List<AdminElevationsResponseDto>> response = new HashMap<>();
    // 코치 상승요청 리스트 담기(service)
    response.put("list", adminService.getRequestedElevationList());
    // 정상 등록완료(200)
    return ResponseEntity.ok(response);
  }

  /**
   * [admin-3] 해당 아이디의 권한을 1에서 2로 상승
   * privilege : 3
   *
   * @return [200] 정상 요청완료
   */
  @PatchMapping("/privileges/elevations")
  public ResponseEntity<MessageDto> requestElevatePermission(@Validated @RequestBody AdminElevateRequestDto dto) throws Exception {
    // 권한 요청 등록(service)
    adminService.requestElevatePermission(dto);

    return new ResponseEntity<>(new MessageDto("Permission elevation request successfully completed"), HttpStatus.OK);
  }

}

