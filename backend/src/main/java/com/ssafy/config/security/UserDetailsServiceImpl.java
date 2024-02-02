package com.ssafy.config.security;

import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetailsImpl loadUserByUsername(String stringId) throws UsernameNotFoundException {

    // DB에서 멤버 찾아옴
    List<Member> memberInDB = memberRepository.findByStringId(stringId);

    // 회원 검증
    if (memberInDB == null || memberInDB.isEmpty()) throw new UsernameNotFoundException("등록되지 않은 회원");

    // 결과 반환
    return new UserDetailsImpl(memberInDB.get(0));
  }
}
