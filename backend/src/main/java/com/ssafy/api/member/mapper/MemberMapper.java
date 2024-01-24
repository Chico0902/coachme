package com.ssafy.api.member.mapper;

import com.ssafy.api.admin.dto.MemberListResponseDto;
import com.ssafy.api.member.dto.MemberDuplicateRequestDto;
import com.ssafy.api.member.dto.MemberInfoResponseDto;
import com.ssafy.api.admin.dto.PortfolioResponseDto;
import com.ssafy.api.member.dto.RegistMemberDto;
import com.ssafy.db.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MemberMapper {
  MemberMapper instance = Mappers.getMapper(MemberMapper.class);

  @Mapping(source = "memberId", target = "id")
  MemberDuplicateRequestDto memberToMemberDuplicateDto(Member member);

  // source = "Entity Field", target = "Dto Field"
  @Mapping(source = "memberId", target = "id")
  @Mapping(source = "password", target = "pw")
  @Mapping(source = "nickname", target = "nick")
  RegistMemberDto memberToRegistMemberDto(Member member);

  @Mapping(source = "memberId", target = "id")
  @Mapping(source = "password", target = "pw")
  @Mapping(source = "nickname", target = "nick")
  MemberInfoResponseDto memberToMemberInfoResponseDto(Member member);

  @Mapping(source = "memberId", target = "id")
  @Mapping(source = "nickname", target = "nick")
  @Mapping(source = "privilege", target = "priv")
  @Mapping(source = "createDate", target = "cdate")
  @Mapping(source = "elevation", target = "elev")
  MemberListResponseDto memberToMemberListResponseDto(Member member);

  List<MemberListResponseDto> memberToMemberListResponseDto(List<Member> memberList);

  @Mapping(source = "portfolio.description", target = "description")
  PortfolioResponseDto memberToPortfolioResponseDto(Member member);

  List<PortfolioResponseDto> memberToPortfolioResponseDto(List<Member> member);
}
