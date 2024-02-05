package com.ssafy.api.coach.service;

import com.ssafy.api.coach.dto.request.CoachesRequestDto;
import com.ssafy.api.coach.dto.request.PortfolioRequestDto;
import com.ssafy.api.coach.dto.response.CoachesResponseDtos;
import com.ssafy.api.coach.dto.response.PortfolioResponseDto;
import com.ssafy.api.coach.mapper.CoachMapper;
import com.ssafy.api.coaching.repository.CategoryRepository;
import com.ssafy.api.coaching.repository.CoachingRepository;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.type.CategoryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CoachService {
  private final MemberRepository memberRepository;
  private final CoachingRepository coachingRepository;
  private final CategoryRepository categoryRepository;

  public PortfolioResponseDto getPortfolio(long id) {
    return CoachMapper.instance.PortfolioToPortfolioResponseDto(memberRepository.getReferenceById(id).getPortfolio());
  }

  public void updatePortfolio(long id, PortfolioRequestDto dto) {
    memberRepository.getReferenceById(id).updatePortfolio(dto.getHtmlDocs());
  }

  //  String division1;
  //  String division2;
  public List<?> getCoachList(CoachesRequestDto dto) {
    List<CoachesResponseDtos> list;
    Long mainCategoryId = categoryRepository.findByCategoryTypeAndName(CategoryType.MAIN, dto.getDivision1());

    if(dto.getDivision2().isBlank()){
      list = coachingRepository.findByCategory(mainCategoryId, null);
    }else{
      Long subCategoryId = categoryRepository.findByCategoryTypeAndName(CategoryType.SUB, dto.getDivision2());

      list = coachingRepository.findByCategory(mainCategoryId, subCategoryId);

    }
    return list;
  }
}
