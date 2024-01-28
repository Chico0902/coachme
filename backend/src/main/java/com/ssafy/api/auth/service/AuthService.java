package com.ssafy.api.auth.service;

import com.ssafy.api.auth.dto.request.LoginRequestDto;
import com.ssafy.api.auth.dto.response.TokenResponseDto;
import com.ssafy.config.security.token.JwtTokenProvider;
import com.ssafy.db.entity.Member;
import com.ssafy.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate stringRedisTemplate;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 로그인
     * stringId == username
     * @param loginRequestDto : 로그인 요청
     * @return : Token 정보
     */
    public TokenResponseDto getTokenResponseDto(LoginRequestDto loginRequestDto) throws Exception {

        // 유효한 로그인인지 검증
        Member validateMember = getValidateMember(
                loginRequestDto.getStringId(), loginRequestDto.getPw());

        // 유효한 로그인이면 토큰 전송
        TokenResponseDto tokenResponseDto = new TokenResponseDto(
                jwtTokenProvider.generateAccessToken(validateMember),
                jwtTokenProvider.generateRefreshToken(validateMember.getStringId())
        );

        // Redis에 저장
//        stringRedisTemplate.opsForValue().set(validateMember.getStringId(), tokenResponseDto.getRefreshToken());

        return tokenResponseDto;
    }

    /**
     * DB에 있는 비밀번호와 동일한 비밀번호인지 확인하고, 해당 멤버를 반환
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
        if (!bCryptPasswordEncoder.matches(password, memberInDB.get(0).getPassword())) throw new BadCredentialsException("패스워드 불일치");

        // 일치 시 해당 멤버 반환
        return memberInDB.get(0);
    }

}
