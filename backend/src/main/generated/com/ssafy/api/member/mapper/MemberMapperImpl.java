package com.ssafy.api.member.mapper;

import com.ssafy.api.admin.dto.response.AdminMembersResponseDto;
import com.ssafy.api.member.dto.request.MemberRegistRequestDto;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.type.Privilege;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-02T10:39:43+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3 (ojdkbuild)"
)
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberRegistRequestDtoToMember(MemberRegistRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.password( dto.getPw() );
        member.nickName( dto.getNick() );
        member.stringId( dto.getStringId() );
        member.name( dto.getName() );
        member.email( dto.getEmail() );

        return member.build();
    }

    @Override
    public AdminMembersResponseDto memberToAdminMemberResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        AdminMembersResponseDto adminMembersResponseDto = new AdminMembersResponseDto();

        String privilegeCode = memberPrivilegePrivilegeCode( member );
        if ( privilegeCode != null ) {
            adminMembersResponseDto.setPriv( Enum.valueOf( Privilege.class, privilegeCode ) );
        }
        adminMembersResponseDto.setNick( member.getNickName() );
        adminMembersResponseDto.setCdate( member.getCreateDate() );
        adminMembersResponseDto.setMdate( member.getModifyDate() );
        adminMembersResponseDto.setStringId( member.getStringId() );
        adminMembersResponseDto.setName( member.getName() );
        adminMembersResponseDto.setEmail( member.getEmail() );
        adminMembersResponseDto.setStatus( member.getStatus() );

        return adminMembersResponseDto;
    }

    @Override
    public List<AdminMembersResponseDto> memberToAdminMemberResponseDto(List<Member> memberList) {
        if ( memberList == null ) {
            return null;
        }

        List<AdminMembersResponseDto> list = new ArrayList<AdminMembersResponseDto>( memberList.size() );
        for ( Member member : memberList ) {
            list.add( memberToAdminMemberResponseDto( member ) );
        }

        return list;
    }

    private String memberPrivilegePrivilegeCode(Member member) {
        if ( member == null ) {
            return null;
        }
        Privilege privilege = member.getPrivilege();
        if ( privilege == null ) {
            return null;
        }
        String privilegeCode = privilege.getPrivilegeCode();
        if ( privilegeCode == null ) {
            return null;
        }
        return privilegeCode;
    }
}
