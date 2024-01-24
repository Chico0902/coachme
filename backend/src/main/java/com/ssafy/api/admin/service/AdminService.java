package com.ssafy.api.admin.service;

import com.ssafy.api.admin.dto.MemberListResponseDto;
import com.ssafy.api.admin.dto.PortfolioResponseDto;
import com.ssafy.api.member.mapper.MemberMapper;
import com.ssafy.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

  private final MemberRepository memberRepository;

  public List<MemberListResponseDto> getMemberList() {
    return MemberMapper.instance.memberToMemberListResponseDto(memberRepository.findAll());
  }

  public List<PortfolioResponseDto> getElevList() {
    return MemberMapper.instance.memberToPortfolioResponseDto(memberRepository.findElevationMember());
  }

}
