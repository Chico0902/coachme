package com.ssafy.api.coach.mapper;

import com.ssafy.api.coach.dto.response.CoachDetailResponseDto;
import com.ssafy.api.coach.dto.response.PortfolioResponseDto;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.Portfolio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoachMapper {
  CoachMapper instance = Mappers.getMapper(CoachMapper.class);

  PortfolioResponseDto PortfolioToPortfolioResponseDto(Portfolio portfolio);

  @Mapping(source = "profileImage.url", target = "coachProfileImageUrl")
  @Mapping(source = "name", target = "coachName")
  @Mapping(source = "portfolio.htmlDocs", target = "portFolioHtmlDocs")
  CoachDetailResponseDto memberToCoachDetailResponseDto(Member coachId);

}
