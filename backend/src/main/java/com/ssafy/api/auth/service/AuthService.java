package com.ssafy.api.auth.service;

import com.ssafy.api.auth.dto.request.LoginRequestDto;
import com.ssafy.api.auth.dto.response.TokenResponseDto;
import com.ssafy.api.member.Util.EncryptionUtils;
import com.ssafy.db.entity.Member;
import com.ssafy.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ssafy.api.member.Util.EncryptionUtils.encryptSHA256;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements UserDetailsService {

    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate stringRedisTemplate;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * 비밀번호 암호화해서 db와 비교하는 메서드
     * @param stringId : 회원 ID
     * @param password : 회원 비밀번호
     * @return DB에 있는 유효한 멤버 반환
     * @throws UsernameNotFoundException : 회원을 찾지 못하면 발생하는 예외
     */
    public Member getValidateMember(String stringId, String password) throws Exception {

        // DB에서 해당 멤버 찾아오기
        Member memberInDB = loadUserByUsername(stringId);

        // 비밀번호 암호화해서 아이디와 비밀번호가 일치하는 멤버 확인
        if (memberInDB.getPassword().equals(bCryptPasswordEncoder.(password))) throw new Exception("Password Error");

        // 해당 멤버 반환
        return memberInDB;
    }

    /**
     * 입력받은 값으로 DB에서 회원 정보를 불러오는 메서드
     * stringId == username
     * @param stringId : 회원 ID
     * @return : Member 엔터티
     * @throws UsernameNotFoundException : 회원을 찾지 못하면 발생하는 예외
     */
    @Override
    public Member loadUserByUsername(String stringId) throws UsernameNotFoundException {

        // DB에서 멤버 찾아옴
        List<Member> memberInDB = memberRepository.findByStringId(stringId);

        // 회원 검증
        if (memberInDB == null || memberInDB.isEmpty()) throw new UsernameNotFoundException("등록되지 않은 회원 ID입니다.");

        // 결과 반환
        return memberInDB.get(0);
    }

    public TokenResponseDto getTokenResponseDto(LoginRequestDto loginRequestDto) throws Exception{

        // 유효한 로그인인지 검증
        Member validateMember = getValidateMember(
                loginRequestDto.getStringId(), loginRequestDto.getPw());

        // 유효한 로그인이면 토큰 전송
        TokenResponseDto tokenResponseDto = new TokenResponseDto(
                jwtTokenProvider.generateTokenByEntity(validateMember, true),
                jwtTokenProvider.generateTokenByEntity(validateMember, false)
        );

        // Redis에 저장
        stringRedisTemplate.opsForValue().set(validateMember.getStringId(), tokenResponseDto.getRefreshToken());

        return tokenResponseDto;
    }
}
