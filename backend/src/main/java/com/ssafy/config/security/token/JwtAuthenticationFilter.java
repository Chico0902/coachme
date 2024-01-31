package com.ssafy.config.security.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.auth.dto.request.RefreshTokenRequestDto;
import com.ssafy.api.auth.dto.response.TokenResponseDto;
import com.ssafy.db.entity.Member;
import com.ssafy.dto.ExceptionDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private final EntityManager entityManager;

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
            "/api/admin/privileges/elevations",
            "/api/admin/members"
    ).contains(request.getRequestURI())) {
      chain.doFilter(request, response);
      return;
    }

    // 애러발생 시 전송되는 Response 정보
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    ObjectMapper objectMapper = new ObjectMapper();
    HashMap<String, Object> jsonMap = new HashMap<>();

    // Access Token 재발급 API일 경우 => Refresh Token 검증 후 새로운 Access Token Header에 재발급
    if (request.getRequestURI().equals("/api/auth/refresh")) {

      // String Id 세팅해서 Auth에 전송한 결과로 Redis 조회 및 토큰 생성
      String refreshTokenInHeader = jwtTokenProvider.getTokenInHeader(request);
      String stringId = jwtTokenProvider.getClaims(refreshTokenInHeader).getSubject();
      String refreshTokenInRedis = jwtTokenProvider.getRefreshTokenInRedis(stringId);

      // Refresh Token 검증
      if(refreshTokenInRedis != null && refreshTokenInRedis.equals(refreshTokenInHeader)) {

        // Access Token 생성을 위한 Repository 조회
        String jpql = "SELECT m FROM Member m WHERE m.stringId = :stringId";
        TypedQuery<Member> query = entityManager.createQuery(jpql, Member.class);
        query.setParameter("stringId", stringId);
        Member newAccessTokenMember = query.getSingleResult();
        entityManager.close();

        // 새로운 Access Token 발급
        String newAccessToken = jwtTokenProvider.generateAccessToken(newAccessTokenMember);
        response.setHeader(HttpHeaders.AUTHORIZATION, newAccessToken);
        response.setStatus(HttpStatus.OK.value());
        return;
      }
      // Refresh Token이 만료되었거나, 저장된 Refresh Token과 다를 경우
      else {
        log.error("Refresh Token Expired !");
        jsonMap.put("data", new ExceptionDto("Refresh Token Expired"));
        String jsonString = objectMapper.writeValueAsString(jsonMap);
        response.getWriter().write(jsonString);
        return;
      }
    }

    // 토큰 만료 시 리프레시 토큰 재요청 전송
    String tokenInHeader = jwtTokenProvider.getTokenInHeader(request);
    if (!(jwtTokenProvider.validateToken(tokenInHeader))) {
      log.error("Access Token Expired !");
      jsonMap.put("data", new ExceptionDto("Access Token Expired"));
      String jsonString = objectMapper.writeValueAsString(jsonMap);
      response.getWriter().write(jsonString);
      return;
    }

    // 유효한 토큰이면 인증 객체 생성해서 SecurityContext에 저장 후 다음 필터로 이동
    Authentication auth = jwtTokenProvider.getAuthentication(tokenInHeader);
    SecurityContextHolder.getContext().setAuthentication(auth);
    log.info("Authorities : {}", auth.getAuthorities());

    chain.doFilter(request, response);
  }
}