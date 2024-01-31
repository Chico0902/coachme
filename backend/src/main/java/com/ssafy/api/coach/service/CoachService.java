package com.ssafy.api.coach.service;

import com.ssafy.api.coach.dto.request.PortfolioRequestDto;
import com.ssafy.api.coach.dto.response.PortfolioResponseDto;
import com.ssafy.api.coach.mapper.CoachMapper;
import com.ssafy.api.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CoachService {
  private final MemberRepository memberRepository;

  public PortfolioResponseDto getPortfolio(long id) {
    return CoachMapper.instance.PortfolioToPortfolioResponseDto(memberRepository.findById(id).get().getPortfolio());
  }

  public void updatePortfolio(long id, PortfolioRequestDto dto) {
    memberRepository.findById(id).get().updatePortfolio(dto.getHtmlDocs());
  }
}
