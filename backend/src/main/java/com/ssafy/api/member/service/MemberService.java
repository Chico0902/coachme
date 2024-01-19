package com.ssafy.api.member.service;

import com.ssafy.api.member.dto.RegistMemberDto;
import com.ssafy.api.member.mapper.MemberMapper;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

  public RegistMemberDto find(String id) throws UsernameNotFoundException{

    Member member = memberRepository.findByMemberId(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

    return MemberMapper.instance.memberToRegistMemberDto(member);
  }
}
