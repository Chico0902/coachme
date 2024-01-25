package com.ssafy.api.member.mapper;

import com.ssafy.api.dto.request.MemberRegistRequestDto;
import com.ssafy.db.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

  MemberMapper instance = Mappers.getMapper(MemberMapper.class);

  // source = "Entity Field", target = "Dto Field"
  @Mapping(source = "stringId", target = "stringId")
  @Mapping(source = "password", target = "pw")
  @Mapping(source = "name", target = "name")
  @Mapping(source = "nickName", target = "nick")
  @Mapping(source = "email", target = "email")
  MemberRegistRequestDto memberToMemberRegistRequestDto(Member member);

//  @Mapping(source = "stringId", target = "id")
//  @Mapping(source = "password", target = "pw")
//  @Mapping(source = "nickName", target = "nick")
//  MemberInfoResponseDto memberToMemberInfoResponseDto(Member member);
//
//  @Mapping(source = "stringId", target = "id")
//  @Mapping(source = "nickName", target = "nick")
//  @Mapping(source = "privilege", target = "priv")
//  @Mapping(source = "elevated", target = "elev")
//  MemberListResponseDto memberToMemberListResponseDto(Member member);
//
//  List<MemberListResponseDto> memberToMemberListResponseDto(List<Member> memberList);
//
//  @Mapping(source = "portfolio.htmlDocs", target = "description")
//  PortfolioResponseDto memberToPortfolioResponseDto(Member member);
//
//  List<PortfolioResponseDto> memberToPortfolioResponseDto(List<Member> member);
//
//  @Mapping(source = "stringId", target = "id")
//  MemberDuplicateRequestDto memberToMemberDuplicateDto(Member member);
}
