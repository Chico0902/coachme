package com.ssafy.api.member.controller;

import com.ssafy.api.member.request.RegistMemberDto;
import com.ssafy.api.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<Map<String, Object>> registMember(@RequestBody @Validated RegistMemberDto registMemberDto) {
    Map<String, Object> responseMessage = new HashMap<>();
    HttpStatus status = HttpStatus.ACCEPTED;
    try {
      memberService.regist(registMemberDto);
      responseMessage.put("message", "Regist Done !");
      status = HttpStatus.OK;
    } catch (Exception e) {
      responseMessage.put("message", "Error !");
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
  }

}
