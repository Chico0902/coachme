package com.ssafy.api.member.mapper;

import com.ssafy.api.admin.dto.response.AdminMembersResponseDto;
import com.ssafy.api.coaching.dto.response.CoameListResponseDto;
import com.ssafy.api.livecoaching.dto.response.LiveCoachingMemberResponseDto;
import com.ssafy.api.member.dto.request.MemberRegistRequestDto;
import com.ssafy.db.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

  @Mapping(source = "privilege.privilegeCode", target = "priv")
  @Mapping(source = "nickName", target = "nick")
  @Mapping(source = "createDate", target = "cdate")
  @Mapping(source = "modifyDate", target = "mdate")
  AdminMembersResponseDto memberToAdminMemberResponseDto(Member member);

  List<AdminMembersResponseDto> memberToAdminMemberResponseDto(List<Member> memberList);

  @Mapping(source = "profileImage.url", target = "url")
  CoameListResponseDto memberToCoameListResponseDto(Member member);

  List<CoameListResponseDto> memberToCoameListResponseDto(List<Member> member);

  @Mapping(source = "longId", target = "memberId")
  @Mapping(source = "profileImage.url", target = "memberProfileUrl")
  @Mapping(source = "name", target = "memberName")
  @Mapping(source = "profileText", target = "memberProfileText")
  LiveCoachingMemberResponseDto memberToLiveCoachingMemberDto(Member member);

}
