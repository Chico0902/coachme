package com.ssafy.api.auth.controller;

import com.ssafy.api.auth.dto.TokenInfoDto;
import com.ssafy.api.auth.dto.request.LoginRequestDto;
import com.ssafy.api.auth.dto.response.LoginResponseDto;
import com.ssafy.api.auth.service.AuthService;
import com.ssafy.api.member.dto.response.ProfileResponseDto;
import com.ssafy.api.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

  private final AuthService authService;
  private final MemberService memberService;

  /**
   * [auth-1] 로그인 요청 시 해당 id와 pw가 유효한지 여부를 응답한다.
   * privilege : ALL
   *
   * @return [200] 토큰정보 발송
   * @throws Exception : handleUsernameNotFoundException, handleBadCredentialsException
   */
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Validated LoginRequestDto loginRequestDto, HttpServletResponse response) throws Exception {

    // service에서 토큰 및 쿠키 생성
    TokenInfoDto tokenInfoDto = authService.getTokenInfoDto(loginRequestDto);
    ProfileResponseDto profileResponseDto = memberService.requestProfile(tokenInfoDto.getLongId());
    Cookie refresh = authService.setCookie("refresh-token", tokenInfoDto.getRefreshToken());

    // Header정보 설정
    response.addCookie(refresh);
    response.setHeader(HttpHeaders.AUTHORIZATION, tokenInfoDto.getAccessToken());

    // [200] 로그인 발송
    return new ResponseEntity<>(new LoginResponseDto(
        profileResponseDto.getProfileText(),
        profileResponseDto.getProfileImageUrl()),
        HttpStatus.OK);
  }

}
