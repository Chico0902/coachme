package com.ssafy.config.security.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.db.entity.Member;
import com.ssafy.dto.ExceptionDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

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
        "/api/members/profiles/texts/1",
        "/api/members/profiles/images/1",
        "/api/members/getfilesTest",
        "/api/dm/room/enter",
        "/api/dm/room/1",
        "/api/dm/52",
        "/api/ws-dm",
        "/api/coaches/categories",
        "/api/ws-dm",
        "/api/members/profiles/1",
        "/api/coaches/coachings/1",
        "/api/coaches/1/coachings/2",
        "/api/coaches/portfolio/1",
        "/api/coachings/1/coames",
        "/api/coachings/2/coames",
        "/api/coachings/3/coames",
        "/api/coachings/4/coames",
        "/api/coachings/5/coames",
        "/api/coachings/1/5",
        "/api/ws-dm",
        "/api/coachings/1/3",
        "/api/ws-dm",
        "/api/coaches/categories",
        "/api/review/coach",
        "/api/review/coaching",
        "/api/review/2",
        "/api/review/coach/2",
        "/api/review/coaching/1",
        "/api/live/sessions/SessionA/connections",
        "/api/live/sessions"
    ).contains(request.getRequestURI())) {
      chain.doFilter(request, response);
      return;
    }

    // 애러발생 시 전송되는 Response 정보
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    ObjectMapper objectMapper = new ObjectMapper();

    // Access Token 재발급 API일 경우 => Refresh Token 검증 후 새로운 Access Token Header에 재발급
    if (request.getRequestURI().equals("/api/auth/refresh")) {

      // 요청 쿠키의 Refresh Token을 추출
      Cookie[] cookies = request.getCookies();
      String refreshTokenInHeader = null;
      if (cookies != null) {
        for (Cookie cookie : cookies) {
          if (cookie.getName().equals("refresh-token")) {
            refreshTokenInHeader = cookie.getValue();
          }
        }
      }
      // 요청에 쿠키가 존재하지 않으면
      else {
        String jsonString = objectMapper.writeValueAsString(new ExceptionDto("Refresh Token is Null"));
        response.getWriter().write(jsonString);
        return;
      }
      if (refreshTokenInHeader == null) {
        String jsonString = objectMapper.writeValueAsString(new ExceptionDto("Refresh Token is Null"));
        response.getWriter().write(jsonString);
        return;
      }

      // Redis에 저장된 Refresh Token 꺼내오기
      String stringId = jwtTokenProvider.getClaims(refreshTokenInHeader).getSubject();
      String refreshTokenInRedis = jwtTokenProvider.getRefreshTokenInRedis(stringId);

      // Refresh Token 검증
      if (refreshTokenInRedis != null && refreshTokenInRedis.equals(refreshTokenInHeader)) {

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
        String jsonString = objectMapper.writeValueAsString(new ExceptionDto("Refresh Token Expired"));
        response.getWriter().write(jsonString);
        return;
      }
    }

    // 토큰 만료 시 리프레시 토큰 재요청 전송
    String tokenInHeader = jwtTokenProvider.getTokenInHeader(request);
    if (!(jwtTokenProvider.validateToken(tokenInHeader))) {
      log.error("Access Token Expired !");
      String jsonString = objectMapper.writeValueAsString(new ExceptionDto("Access Token Expired"));
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
