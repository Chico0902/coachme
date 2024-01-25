package com.ssafy.api.member.mapper;

import com.ssafy.api.admin.dto.MemberListResponseDto;
import com.ssafy.api.admin.dto.PortfolioResponseDto;
import com.ssafy.api.member.dto.MemberDuplicateRequestDto;
import com.ssafy.api.member.dto.MemberInfoResponseDto;
import com.ssafy.api.member.dto.RegistMemberDto;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.Portfolio;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-25T14:57:17+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3 (ojdkbuild)"
)
public class MemberMapperImpl implements MemberMapper {

    @Override
    public RegistMemberDto memberToRegistMemberDto(Member member) {
        if ( member == null ) {
            return null;
        }

        RegistMemberDto registMemberDto = new RegistMemberDto();

        registMemberDto.setId( member.getStringId() );
        registMemberDto.setPw( member.getPassword() );
        registMemberDto.setNick( member.getNickName() );
        registMemberDto.setName( member.getName() );
        registMemberDto.setEmail( member.getEmail() );

        return registMemberDto;
    }

    @Override
    public MemberInfoResponseDto memberToMemberInfoResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberInfoResponseDto memberInfoResponseDto = new MemberInfoResponseDto();

        memberInfoResponseDto.setId( member.getStringId() );
        memberInfoResponseDto.setPw( member.getPassword() );
        memberInfoResponseDto.setNick( member.getNickName() );
        memberInfoResponseDto.setName( member.getName() );
        memberInfoResponseDto.setEmail( member.getEmail() );

        return memberInfoResponseDto;
    }

    @Override
    public MemberListResponseDto memberToMemberListResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberListResponseDto memberListResponseDto = new MemberListResponseDto();

        memberListResponseDto.setId( member.getStringId() );
        memberListResponseDto.setNick( member.getNickName() );
        if ( member.getPrivilege() != null ) {
            memberListResponseDto.setPriv( member.getPrivilege().name() );
        }
        memberListResponseDto.setElev( member.isElevated() );
        memberListResponseDto.setName( member.getName() );
        memberListResponseDto.setEmail( member.getEmail() );

        return memberListResponseDto;
    }

    @Override
    public List<MemberListResponseDto> memberToMemberListResponseDto(List<Member> memberList) {
        if ( memberList == null ) {
            return null;
        }

        List<MemberListResponseDto> list = new ArrayList<MemberListResponseDto>( memberList.size() );
        for ( Member member : memberList ) {
            list.add( memberToMemberListResponseDto( member ) );
        }

        return list;
    }

    @Override
    public PortfolioResponseDto memberToPortfolioResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        PortfolioResponseDto portfolioResponseDto = new PortfolioResponseDto();

        portfolioResponseDto.setDescription( memberPortfolioHtmlDocs( member ) );
        portfolioResponseDto.setName( member.getName() );

        return portfolioResponseDto;
    }

    @Override
    public List<PortfolioResponseDto> memberToPortfolioResponseDto(List<Member> member) {
        if ( member == null ) {
            return null;
        }

        List<PortfolioResponseDto> list = new ArrayList<PortfolioResponseDto>( member.size() );
        for ( Member member1 : member ) {
            list.add( memberToPortfolioResponseDto( member1 ) );
        }

        return list;
    }

    @Override
    public MemberDuplicateRequestDto memberToMemberDuplicateDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDuplicateRequestDto memberDuplicateRequestDto = new MemberDuplicateRequestDto();

        memberDuplicateRequestDto.setId( member.getStringId() );

        return memberDuplicateRequestDto;
    }

    private String memberPortfolioHtmlDocs(Member member) {
        if ( member == null ) {
            return null;
        }
        Portfolio portfolio = member.getPortfolio();
        if ( portfolio == null ) {
            return null;
        }
        String htmlDocs = portfolio.getHtmlDocs();
        if ( htmlDocs == null ) {
            return null;
        }
        return htmlDocs;
    }
}
