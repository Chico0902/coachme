package com.ssafy.api.member.mapper;

import com.ssafy.api.admin.dto.MemberListResponseDto;
import com.ssafy.api.admin.dto.PortfolioResponseDto;
import com.ssafy.api.member.dto.MemberDuplicateRequestDto;
import com.ssafy.api.member.dto.MemberInfoResponseDto;
import com.ssafy.api.member.dto.RegistMemberDto;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.Portfolio;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-24T15:31:24+0900",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
public class MemberMapperImpl implements MemberMapper {

    @Override
    public RegistMemberDto memberToRegistMemberDto(Member member) {
        if ( member == null ) {
            return null;
        }

        RegistMemberDto registMemberDto = new RegistMemberDto();

        registMemberDto.setId( member.getMemberId() );
        registMemberDto.setPw( member.getPassword() );
        registMemberDto.setNick( member.getNickname() );
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

        memberInfoResponseDto.setId( member.getMemberId() );
        memberInfoResponseDto.setPw( member.getPassword() );
        memberInfoResponseDto.setNick( member.getNickname() );
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

        memberListResponseDto.setId( member.getMemberId() );
        memberListResponseDto.setNick( member.getNickname() );
        memberListResponseDto.setPriv( member.getPrivilege() );
        if ( member.getCreateDate() != null ) {
            memberListResponseDto.setCdate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( member.getCreateDate() ) );
        }
        memberListResponseDto.setElev( member.getElevation() );
        memberListResponseDto.setName( member.getName() );
        memberListResponseDto.setPhone( member.getPhone() );
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

        portfolioResponseDto.setDescription( memberPortfolioDescription( member ) );
        portfolioResponseDto.setId( member.getId() );
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

        memberDuplicateRequestDto.setId( member.getMemberId() );

        return memberDuplicateRequestDto;
    }

    private String memberPortfolioDescription(Member member) {
        if ( member == null ) {
            return null;
        }
        Portfolio portfolio = member.getPortfolio();
        if ( portfolio == null ) {
            return null;
        }
        String description = portfolio.getDescription();
        if ( description == null ) {
            return null;
        }
        return description;
    }
}
