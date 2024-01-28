package com.ssafy.config.security.token;

import com.ssafy.api.auth.dto.request.RefreshTokenRequestDto;
import com.ssafy.config.security.UserDetailsImpl;
import com.ssafy.config.security.UserDetailsServiceImpl;
import com.ssafy.db.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class JwtTokenProvider {

  // HS256(HMAC-SHA256) 대칭키 암호화 알고리즘을 사용하기 위한 암호키
  private final Key key;

  // 액세스 토큰 만료 시간 (30분)
  private final long accessTokenExpire;

  // 리프레시 토큰 만료 시간 (1일)
  private final long refreshTokenExpire;

  public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                          @Value("${jwt.access-token-expire}") long accessTokenExpire,
                          @Value("${jwt.refresh-token-expire}") long refreshTokenExpire,
                          @Autowired UserDetailsServiceImpl userDetailsService) {
    this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    this.accessTokenExpire = accessTokenExpire;
    this.refreshTokenExpire = refreshTokenExpire;
  }

  /**
   * 엔티티를 받아 엑세스 토큰을 생성하는 메서드.
   * @param member    : Member 엔티티
   * @return : access token
   */
  public String generateAccessToken(Member member) {

    // 회원 Long Id로 토큰생성 후 정보 입력
    Claims claims = Jwts.claims().setSubject(member.getStringId());
    claims.put("longId", member.getLongId());
    claims.put("privilege", member.getPrivilege());
    claims.put("name", member.getName());
    Date now = new Date();

    // Access 토큰 반환
    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + accessTokenExpire))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
  }

  public String generateAccessToken(RefreshTokenRequestDto dto) {
    // 회원 Long Id로 토큰생성 후 정보 입력
    Claims claims = Jwts.claims().setSubject(dto.getStringId());
    claims.put("longId", dto.getLongId());
    claims.put("privilege", dto.getPrivilege());
    claims.put("name", dto.getName());
    Date now = new Date();

    // Access 토큰 반환
    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + accessTokenExpire))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
  }

  /**
   * 엔티티를 받아 리플레쉬 토큰을 생성하는 메서드
   * @param stringId - 해당 토큰의 subject가 되는 멤버 id(String ID)
   * @return : refresh token
   */
  public String generateRefreshToken(String stringId) {

    // 회원 ID와 UUID를 담은 토큰생성 후 정보 입력
    Claims claims = Jwts.claims().setSubject(stringId);
    claims.put("uuid", UUID.randomUUID());
    Date now = new Date();

    // refresh 토큰 반환
    return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + accessTokenExpire))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
  }

  /**
   * 토큰을 확인해서 권한을 부여하는 메서드
   * @param token 헤더로부터 획득한 토큰
   */
  public Authentication getAuthentication(String token) {
    // token으로부터 claims 생성
    Claims claims = getClaims(token);

    // privilege 확인해서 권한 인가
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    switch((String) claims.get("privilege")) {
      case "COAME" -> authorities.add(new SimpleGrantedAuthority("ROLE_COAME"));
      case "COACH" -> {
        authorities.add(new SimpleGrantedAuthority("ROLE_COAME"));
        authorities.add(new SimpleGrantedAuthority("ROLE_COACH"));
      }
      case "ADMIN" -> {
        authorities.add(new SimpleGrantedAuthority("ROLE_COAME"));
        authorities.add(new SimpleGrantedAuthority("ROLE_COACH"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
      }
    }

    return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
  }

  /**
   * Request Header의 Authorization에서 암호화된 Token을 반환하는 메서드
   * @param request : 요청
   * @return Header에서 token 획득
   * @throws
   */
  public String getTokenInHeader(HttpServletRequest request) {
    String requestHeaderAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

    // Header의 토큰이 잘못되었을 때 예외처리
    if (requestHeaderAuth == null || !requestHeaderAuth.startsWith("Bearer"))
      throw new UnsupportedJwtException("잘못된 토큰 정보입니다.");

    // 암호화된 토큰 반환
    return requestHeaderAuth.split(" ")[1].trim();
  }

  /**
   * 암호화된 토큰을 복호화하는 메서드
   * @param encryptedToken - 암호화된 토큰 정보 : getTokenInHeader 메서드로 얻을 수 있음
   * @return Clamis - 복호화된 토큰 정보
   */
  public Claims getClaims(String encryptedToken) {

    // Jwt Parser 생성
    JwtParser parser = Jwts.parserBuilder().setSigningKey(key).build();

    return parser.parseClaimsJws(encryptedToken).getBody();
  }

  /**
   * 암호화된 토큰을 검증하는 메서드
   * @param encryptedToken - 암호화된 토큰
   * @throws UnsupportedJwtException – if the claimsJws argument does not represent an Claims JWS
   * @throws MalformedJwtException – if the claimsJws string is not a valid JWS
   * @throws IllegalArgumentException – if the claimsJws string is null or empty or only whitespace
   */
  public boolean validateToken(String encryptedToken)
          throws UnsupportedJwtException, MalformedJwtException, IllegalArgumentException {
      try {
        Jwts.parser().setSigningKey(key).parseClaimsJws(encryptedToken);
        return true;
      } catch (ExpiredJwtException e) {
        return false;
      }
    }

}
