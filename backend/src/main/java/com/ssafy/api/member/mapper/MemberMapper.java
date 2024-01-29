package com.ssafy.api.member.mapper;

import com.ssafy.api.admin.dto.response.AdminElevationsResponseDto;
import com.ssafy.api.member.dto.request.MemberRegistRequestDto;
import com.ssafy.db.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

  MemberMapper instance = Mappers.getMapper(MemberMapper.class);

  /**
   * Mapper 사용방법
   * source = "변경 전 클래스", target = "변경 후 클래스"
   * ex1) DTO -> Member | source : DTO, target : Member
   * ex2) Member -> DTO | source : Member, target : DTO
   * cf) 같은 이름의 필드는 명시안해도 알아서 번역
   */


  // [member-2] 회원가입 요청 시 해당 정보를 DB에 저장한다.
  @Mapping(source = "pw", target = "password")
  @Mapping(source = "nick", target = "nickName")
  Member memberRegistRequestDtoToMember(MemberRegistRequestDto dto);

  @Mapping(source = "pw", target = "password")
  @Mapping(source = "nick", target = "nickName")
  Member adminElevationsResponseDto(AdminElevationsResponseDto dto);

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
