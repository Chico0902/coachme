package com.ssafy.api.member.mapper;

import com.ssafy.api.member.dto.request.MemberRegistRequestDto;
import com.ssafy.db.entity.Member;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-30T09:53:36+0900",
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
}
