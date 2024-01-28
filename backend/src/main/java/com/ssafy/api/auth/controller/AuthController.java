package com.ssafy.api.auth.controller;

import com.ssafy.api.auth.dto.request.LoginRequestDto;
import com.ssafy.api.auth.dto.response.TokenResponseDto;
import com.ssafy.api.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final AuthService authService;

  /**
   * [auth-1] 로그인 요청 시 해당 id와 pw가 유효한지 여부를 응답한다.
   * privilege : ALL
   * @return [200] 토큰정보 발송
   * @throws Exception : handleUsernameNotFoundException, handleBadCredentialsException
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Validated LoginRequestDto loginRequestDto) throws Exception{

      // service에서 토큰 생성
      TokenResponseDto tokenResponseDto = authService.getTokenResponseDto(loginRequestDto);

      // [200] 토큰정보 발송
      return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);
  }

}
