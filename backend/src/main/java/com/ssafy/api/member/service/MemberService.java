package com.ssafy.api.member.service;

import com.ssafy.api.member.request.RegistMemberDto;
import com.ssafy.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberRepository memberRepository;

  @Transactional
  public void regist(RegistMemberDto registMemberDto) {

  }
}
