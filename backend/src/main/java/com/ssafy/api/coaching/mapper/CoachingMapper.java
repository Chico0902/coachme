package com.ssafy.api.coaching.mapper;

import com.ssafy.api.admin.dto.response.AdminMembersResponseDto;
import com.ssafy.api.coaching.dto.CreateCoachingRequestDto;
import com.ssafy.api.coaching.dto.GetOneCoachingResponseDto;
import com.ssafy.api.member.dto.request.MemberRegistRequestDto;
import com.ssafy.api.member.mapper.MemberMapper;
import com.ssafy.db.entity.Category;
import com.ssafy.db.entity.Coaching;
import com.ssafy.db.entity.Member;

import com.ssafy.api.coaching.dto.response.CoameListResponseDto;
import com.ssafy.db.entity.CoameCoaching;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CoachingMapper {

  CoachingMapper instance = Mappers.getMapper(CoachingMapper.class);

  /**
   * Mapper 사용방법
   * source = "변경 전 클래스", target = "변경 후 클래스"
   * ex1) DTO -> Coaching | source : DTO, target : Coaching
   * ex2) Coaching -> DTO | source : Coaching, target : DTO
   * cf) 같은 이름의 필드는 명시안해도 알아서 번역
   */

  @Mapping(source = "coame.stringId", target = "stringId")
  @Mapping(source = "coame.name", target = "name")
  @Mapping(source = "coame.profileImage.url", target = "url")
  CoameListResponseDto coachingToCoameListResponseDto(CoameCoaching coameCoaching);

  List<CoameListResponseDto> coachingToCoameListResponseDto(List<CoameCoaching> coameCoachings);

  @Mapping(source = "mainCategory.name", target = "main")
  @Mapping(source = "subCategory.name", target = "sub")
  GetOneCoachingResponseDto coachingToGetOneCoachingResponseDto(Coaching coaching);

}
