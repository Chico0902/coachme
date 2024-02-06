package com.ssafy.api.auth.service;

import com.ssafy.api.auth.dto.TokenInfoDto;
import com.ssafy.api.auth.dto.request.LoginRequestDto;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.config.security.token.JwtTokenProvider;
import com.ssafy.db.entity.Member;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

  private final JwtTokenProvider jwtTokenProvider;
  private final MemberRepository memberRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * 로그인
   * stringId == username
   *
   * @param loginRequestDto : 로그인 요청
   * @return : Token 정보
   */
  public TokenInfoDto getTokenInfoDto(LoginRequestDto loginRequestDto) throws Exception {

    // 유효한 로그인인지 검증
    Member validateMember = getValidateMember(
        loginRequestDto.getStringId(), loginRequestDto.getPw());

    // 유효한 로그인이면 토큰 전송
    TokenInfoDto tokenInfoDto = new TokenInfoDto(
        validateMember.getLongId(),
        jwtTokenProvider.generateAccessToken(validateMember),
        jwtTokenProvider.generateRefreshToken(validateMember.getStringId())
    );

    // Redis에 저장
    jwtTokenProvider.setRefreshTokenInRedis(validateMember.getStringId(), tokenInfoDto.getRefreshToken());

    return tokenInfoDto;
  }

  /**
   * DB에 있는 비밀번호와 동일한 비밀번호인지 확인하고, 해당 멤버를 반환
   *
   * @param stringId : 회원 ID
   * @param password : 회원 비밀번호
   * @return DB에 있는 유효한 멤버 반환
   * @throws BadCredentialsException : 비밀번호 불일치
   */
  public Member getValidateMember(String stringId, String password) throws BadCredentialsException {

    // DB에서 해당 멤버 찾아오기
    List<Member> memberInDB = memberRepository.findByStringId(stringId);

    if (memberInDB == null || memberInDB.isEmpty()) throw new UsernameNotFoundException("등록되지 않은 회원");

    // 입력받은 비밀번호와 DB의 비밀번호 비교
    if (!bCryptPasswordEncoder.matches(password, memberInDB.get(0).getPassword()))
      throw new BadCredentialsException("패스워드 불일치");

    // 일치 시 해당 멤버 반환
    return memberInDB.get(0);
  }

  /**
   * 토큰을 전달할 쿠키를 생성하는 메서드
   *
   * @param tokenName - 저장할 토큰 이름
   * @param token     - 실제 토큰
   * @return - 생성된 쿠키
   */
  public Cookie setCookie(String tokenName, String token) {

    // 쿠키 생성
    Cookie cookie = new Cookie(tokenName, token);

    // Secure : HTTPS 프로토콜을 통해 암호화된 연결에서만 전송 (스니핑 방지)
    cookie.setSecure(true);

    // HttpOnly : JavaScript를 통해 접근하는 것을 방지 (XSS 공격 방지)
    cookie.setHttpOnly(true);

    // 유효 시간 설정
    cookie.setMaxAge(60 * 60 * 24); // 1일 (초)

    cookie.setPath("/");

    return cookie;
  }
}
