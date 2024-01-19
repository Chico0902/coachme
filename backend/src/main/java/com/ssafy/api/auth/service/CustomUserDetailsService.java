package com.ssafy.api.auth.service;

import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  /**
   * 입력받은 값으로 DB에서 회원 정보를 불러오는 메서드
   *
   * @param username : 회원 ID
   * @return : Member 엔터티
   * @throws UsernameNotFoundException : 회원을 찾지 못하면 발생하는 예외
   */
  @Override
  public Member loadUserByUsername(String username) throws UsernameNotFoundException {

    return memberRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 회원 입니다."));

  }

  public void isValidMember(String memberId, String password) throws Exception {
    log.info("input Password : {}", password);
    Member member = loadUserByUsername(memberId);
    log.info("load Password : {}", member.getPassword());
    if(!member.getPassword().equals(password)) {
      throw new Exception("Password Error");
    }
  }
}
