package com.ssafy.config.security.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.auth.dto.request.RefreshTokenRequestDto;
import com.ssafy.api.auth.dto.response.TokenResponseDto;
import com.ssafy.dto.ExceptionDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private final StringRedisTemplate stringRedisTemplate;
  private final ObjectMapper objectMapper;

  /**
   * 요청으로부터 토큰을 확인해서 권한을 부여하는 메서드
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain)
          throws ServletException, IOException {

    // 토큰이 필요하지 않은 API URL의 경우 => 로직 처리 없이 다음 필터로 이동
    if (Arrays.asList(
            "/api/auth/login",
            "/api/members",
            "/api/members/duplicate/id",
            "/api/members/privileges/elevations",
            "/api/admin/privileges/elevations"
    ).contains(request.getRequestURI())) {
      chain.doFilter(request, response);
      return;
    }

    // 토큰 refresh API일 경우 => 토큰 검증 후 새로운 access token Header에 발급
    if (request.getRequestURI().equals("/api/auth/refresh")) {

      // String Id 세팅해서 Auth에 전송한 결과로 Redis 조회 및 토큰 생성
      String refreshTokenInHeader = jwtTokenProvider.getTokenInHeader(request);

      // refresh token 유효성 검증
      if (jwtTokenProvider.validateToken(refreshTokenInHeader)) {

        // Body에 있는 정보 파싱
        ServletInputStream inputStream = request.getInputStream();
        String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        RefreshTokenRequestDto refreshTokenRequestDto = objectMapper.readValue(body, RefreshTokenRequestDto.class);

        // 새로운 Access Token 발급
        String newAccessToken = jwtTokenProvider.generateAccessToken(refreshTokenRequestDto);
        response.setHeader(HttpHeaders.AUTHORIZATION, newAccessToken);
        chain.doFilter(request, response);

        // 인증 처리
        Authentication auth = jwtTokenProvider.getAuthentication(newAccessToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        log.info("인증 객체 권한목록: {}", auth.getAuthorities());
        return;
      }
    }

    // 토큰 만료 시 리프레시 토큰 재요청 전송
    String tokenInHeader = jwtTokenProvider.getTokenInHeader(request);
    if (!(jwtTokenProvider.validateToken(tokenInHeader))) {
      objectMapper.writeValueAsString(new ExceptionDto("Access Token Has Been Expired!"));
      chain.doFilter(request, response);
    }

    // 유효한 토큰이면 인증 객체 생성해서 SecurityContext에 저장 후 다음 필터로 이동
    Authentication auth = jwtTokenProvider.getAuthentication(tokenInHeader);
    SecurityContextHolder.getContext().setAuthentication(auth);
    log.info("인증 객체 권한목록: {}", auth.getAuthorities());

    chain.doFilter(request, response);
  }
}
