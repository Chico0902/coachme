package com.ssafy.api.admin.service;

import com.ssafy.api.admin.dto.MemberListResponseDto;
import com.ssafy.api.member.mapper.MemberMapper;
import com.ssafy.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

  private final MemberRepository memberRepository;
  public List<MemberListResponseDto> getMemberList() {
    return MemberMapper.instance.memberToMemberListResponseDto(memberRepository.findAll());
  }
}
