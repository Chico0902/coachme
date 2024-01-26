package com.ssafy.api.auth.controller;

import com.ssafy.api.auth.dto.request.LoginRequestDto;
import com.ssafy.api.auth.dto.response.TokenResponseDto;
import com.ssafy.api.auth.service.AuthService;
import com.ssafy.api.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Validated LoginRequestDto loginRequestDto) {
    try {

      // 토큰 발송
      TokenResponseDto tokenResponseDto = authService.getTokenResponseDto(loginRequestDto);
      return new ResponseEntity<>(tokenResponseDto, HttpStatus.OK);

    } catch (Exception e) {

      // 잘못된 접근
      if (e.getClass() == UsernameNotFoundException.class) return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.FORBIDDEN);

      log.debug("error message : {}", e.getMessage());
      return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
