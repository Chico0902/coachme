package com.ssafy.api.auth.controller;

import com.ssafy.api.auth.dto.LoginRequestDto;
import com.ssafy.api.auth.dto.TokenResponseDto;
import com.ssafy.api.auth.service.CustomUserDetailsService;
import com.ssafy.api.auth.service.JwtTokenProvider;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
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
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final CustomUserDetailsService customUserDetailsService;
  private final JwtTokenProvider jwtTokenProvider;
  private final StringRedisTemplate stringRedisTemplate;

  @PostMapping("/login")
  public ResponseEntity<Map<String, Object>> login(@RequestBody @Validated LoginRequestDto loginRequestDto) {
    Map<String, Object> responseMessage = new HashMap<>();
    HttpStatus status;
    try {
      Member member = customUserDetailsService.loadUserByUsername(loginRequestDto.getId());
      customUserDetailsService.isValidMember(member.getId(), loginRequestDto.getPw());
      TokenResponseDto tokenResponseDto = new TokenResponseDto();
      String memberId = member.getId();
      String accessToken = jwtTokenProvider.generateToken(member.getId(), member.getPrivilege(), member.getName(), true);
      tokenResponseDto.setAccessToken(accessToken);
      String refreshToken = jwtTokenProvider.generateToken(member.getId(), member.getPrivilege(), member.getName(), false);
      tokenResponseDto.setRefreshToken(refreshToken);
      stringRedisTemplate.opsForValue().set(memberId, refreshToken);
      responseMessage.put("data", tokenResponseDto);
      status = HttpStatus.OK;
    } catch (Exception e) {
      responseMessage.put("message", e.getMessage());
      status = HttpStatus.FORBIDDEN;
    }
    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
  }

}
