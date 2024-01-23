package com.ssafy.api.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenProvider {

  // HS256(HMAC-SHA256) 대칭키 암호화 알고리즘을 사용하기 위한 암호키
  private final Key key;
  /*
   액세스 토큰 만료 시간 (30분)
   @Value("${jwt.access-token-expire")
   */
  private final long accessTokenExpire;
  /*
   리프레시 토큰 만료 시간 (1일)
   @Value("${jwt.refresh-token-expire}")
   */
  private final long refreshTokenExpire;

  public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.access-token-expire}") long accessTokenExpire, @Value("${jwt.refresh-token-expire}") long refreshTokenExpire) {
    this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    this.accessTokenExpire = accessTokenExpire;
    this.refreshTokenExpire = refreshTokenExpire;
  }

  /**
   * isAccess 파라미터로 access/refresh 구분하여 토큰을 생성하는 메서드.
   *
   * @param memberId  : 회원 ID
   * @param privilege : 회원 권한 (COAME, COACH, ADMIN)
   * @param name      : 회원 이름
   * @param isAccess  : Access / Refresh 토큰 구분
   * @return : token
   */
  public String generateToken(String memberId, String privilege, String name, boolean isAccess) {

    Claims claims = Jwts.claims().setSubject(memberId);
    claims.put("privilege", privilege);
    claims.put("name", name);
    Date now = new Date();

    if (isAccess) {
      claims.put("type", "access");
      return Jwts.builder()
          .setClaims(claims)
          .setIssuedAt(now)
          .setExpiration(new Date(now.getTime() + accessTokenExpire))
          .signWith(key, SignatureAlgorithm.HS256)
          .compact();
    } else {
      claims.put("type", "refresh");
      return Jwts.builder()
          .setClaims(claims)
          .setIssuedAt(now)
          .setExpiration(new Date(now.getTime() + refreshTokenExpire))
          .signWith(key, SignatureAlgorithm.HS256)
          .compact();
    }
  }

  /**
   * Request Header의 Authorization에서 Token값을 반환하는 메서드
   *
   * @param request : 요청
   * @return : token
   */
  public String resolveToken(HttpServletRequest request) {
    return request.getHeader("Authorization");
  }

  /**
   * 토큰을 검증하는 메서드
   *
   * @param token : 토큰
   * @return : true/false
   */
  public boolean isValidToken(String token) {
    // 토큰 만료 검사
    try {
      log.info("Expire Date : {}", Jwts.parserBuilder()
          .setSigningKey(key).build()
          .parseClaimsJws(token).getBody().getExpiration());
      return true;
    } catch (ExpiredJwtException e) {
      // 토큰 만료되었다면
      return false;
    }
  }

  /**
   * 토큰에 저장된 정보를 반환해주는 메서드
   *
   * @param token : 토큰
   * @return : memberId, privilege, name, isAccess 정보가 담긴 Map
   */
  public Map<String, Object> getInfoInToken(String token) {
    Map<String, Object> map = new HashMap<>();
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token).getBody();
    map.put("memberId", claims.getSubject());
    map.put("privilege", claims.get("privilege"));
    map.put("name", claims.get("name"));
    if (claims.get("type").equals("access")) {
      map.put("isAccess", true);
    } else {
      map.put("isAccess", false);
    }
    return map;
  }

}
