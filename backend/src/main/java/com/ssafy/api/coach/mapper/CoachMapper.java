package com.ssafy.api.coach.mapper;

import com.ssafy.api.coach.dto.response.CalenderResponseDto;
import com.ssafy.api.coach.dto.response.PortfolioResponseDto;
import com.ssafy.db.entity.Coaching;
import com.ssafy.db.entity.Portfolio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CoachMapper {
  CoachMapper instance = Mappers.getMapper(CoachMapper.class);

  PortfolioResponseDto PortfolioToPortfolioResponseDto(Portfolio portfolio);

}
