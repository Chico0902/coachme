package com.ssafy.config;

import com.ssafy.api.auth.service.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;
  private final StringRedisTemplate stringRedisTemplate;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

    chain.doFilter(request, response);

//    // 토큰이 필요하지 않은 API 리스트
//    List<String> anyList = Arrays.asList(
//        "/auth/login",
//        "/members"
//    );
//
//    // 토큰이 필요하지 않은 API URL의 경우 => 로직 처리 없이 다음 필터로 이동
//    if (anyList.contains(request.getRequestURI())) {
//      chain.doFilter(request, response);
//      return;
//    }
//
//    // 애러발생 시 전송되는 Response 정보
//    response.setContentType("application/json");
//    response.setCharacterEncoding("UTF-8");
//    ObjectMapper objectMapper = new ObjectMapper();
//    HashMap<String, Object> jsonMap = new HashMap<>();
//    jsonMap.put("status", HttpStatus.UNAUTHORIZED.value());
//
//    String token = jwtTokenProvider.resolveToken(request);
//
//    // 1. 토큰 존재 여부 검사
//    if (token == null || token.equalsIgnoreCase("")) {
//      log.error("token : null");
//      jsonMap.put("message", "TokenNotFound");
//    } else {
//      boolean isAccess = (boolean) jwtTokenProvider.getInfoInToken(token).get("isAccess");
//      String memberId = (String) jwtTokenProvider.getInfoInToken(token).get("memberId");
//      Long id = (Long) jwtTokenProvider.getInfoInToken(token).get("id");
//      String privilege = (String) jwtTokenProvider.getInfoInToken(token).get("privilege");
//      String name = (String) jwtTokenProvider.getInfoInToken(token).get("name");
//
//      // 2. 토큰 만료 검사
//      if (jwtTokenProvider.isValidToken(token)) {
//        // 2-1. Access Token 일 경우 다음 필터
//        if (isAccess) {
//          chain.doFilter(request, response);
//          return;
//        }
//        // 2-2. Refresh Token 일 경우 Access Token 재발급
//        else {
//          // 3. Redis의 Refresh Token과 검증
//          String retrievedValue = stringRedisTemplate.opsForValue().get(id);
//
//          // 3-1. 검증되었다면 Access Token과 Refresh Token 재발급
//          if (token.equals(retrievedValue)) {
//            TokenResponseDto tokenResponseDto = new TokenResponseDto();
//            tokenResponseDto.setAccessToken(jwtTokenProvider.generateToken(id, memberId, privilege, name, true));
//            tokenResponseDto.setRefreshToken(jwtTokenProvider.generateToken(id, memberId, privilege, name, false));
//            stringRedisTemplate.opsForValue().set(memberId, tokenResponseDto.getRefreshToken());
//
//            // HTTP 응답 설정
//            jsonMap.put("status", HttpStatus.OK.value());
//            jsonMap.put("data", tokenResponseDto);
//          } else {
//            // Redis에 저장된 Refresh Token 삭제
//            stringRedisTemplate.delete(memberId);
//            jsonMap.put("message", "RefreshTokenError");
//          }
//        }
//      }
//      // 만료되었을 때
//      else {
//        // 2-3. Access Token 일 경우 Refresh Token 요청
//        if (isAccess) {
//          jsonMap.put("message", "AccessTokenExpired");
//        }
//        // 2-4. Refresh Token 일 경우 로그아웃
//        else {
//          // Redis에 저장된 Refresh Token 삭제
//          stringRedisTemplate.delete(memberId);
//          jsonMap.put("message", "RefreshTokenError");
//        }
//      }
//    }
//
//    // 에러발생 시 Response 전송
//    String jsonString = objectMapper.writeValueAsString(jsonMap);
//    response.getWriter().write(jsonString);
  }
}
