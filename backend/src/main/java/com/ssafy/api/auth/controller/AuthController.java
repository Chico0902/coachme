package com.ssafy.api.auth.controller;

import com.ssafy.api.auth.dto.request.LoginRequestDto;
import com.ssafy.api.auth.dto.response.TokenResponseDto;
import com.ssafy.api.auth.service.CustomUserDetailsService;
import com.ssafy.api.auth.service.JwtTokenProvider;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin( {"http://localhost:5173", "http://localhost:80", "http://localhost"} )
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
      log.info("input id : {}", member.getStringId());
      customUserDetailsService.isValidMember(member.getStringId(), loginRequestDto.getPw());
      TokenResponseDto tokenResponseDto = new TokenResponseDto();
      String stringId = member.getStringId();

      // access token 생성
      String accessToken = jwtTokenProvider.generateToken(member.getLongId(), stringId, member.getPrivilege().name(), member.getName(), true);
      tokenResponseDto.setAccessToken(accessToken);

      // refresh token 생성
      String refreshToken = jwtTokenProvider.generateToken(member.getLongId(), stringId, member.getPrivilege().name(), member.getName(), false);
      tokenResponseDto.setRefreshToken(refreshToken);


      stringRedisTemplate.opsForValue().set(stringId, refreshToken);
      responseMessage.put("data", tokenResponseDto);
      status = HttpStatus.OK;
    } catch (Exception e) {
      responseMessage.put("message", e.getMessage());
      status = HttpStatus.FORBIDDEN;
    }
    return new ResponseEntity<Map<String, Object>>(responseMessage, status);
  }

}
