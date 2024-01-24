package com.ssafy.api.member.service;

import com.ssafy.api.member.dto.RegistMemberDto;
import com.ssafy.api.member.dto.UpdateMemberDto;
import com.ssafy.api.member.mapper.MemberMapper;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

  private final MemberRepository memberRepository;

  //member 1-3 회원가입(회원 정보 등록)
  public void regist(RegistMemberDto RegistMemberDto) {

    Member member = Member.builder()
        .memberId(RegistMemberDto.getId())
        .password(RegistMemberDto.getPw())
        .name(RegistMemberDto.getName())
        .nickname(RegistMemberDto.getNick())
        .email(RegistMemberDto.getEmail())
        .build();
    memberRepository.save(member);
  }

  //member 1-6 기본 회원정보 조회
  @Transactional(readOnly = true)
  public RegistMemberDto findByMemberId(Long memberId) {
//    log.info("memberId : {}", memberId);
    Member member = memberRepository.findById(memberId);
    RegistMemberDto RegistMemberDto = MemberMapper.instance.memberToRegistMemberDto(member);
    if (member != null) {
//      log.info("memberId : {}", memberId);
      return RegistMemberDto;
    } else {
      return null;
    }
  }

  //member 1-13 회원 삭제
  public void deleteMember(Long memberId) {
    Member existingMember = memberRepository.findById(memberId);
    ;

    if (existingMember != null) {
      memberRepository.delete(existingMember);
    } else {
      throw new RuntimeException("Member not found");
    }
  }

  //member 1-6 회원정보 수정
  public void updateMember(Long memberId, UpdateMemberDto updateMemberDto) {
    Member existingMember = memberRepository.findById(memberId);
    String password1 = existingMember.getPassword();
    String password2 = updateMemberDto.getPw();

    if (existingMember != null && password1.equals(password2)) {
      existingMember.changeMemberInfo(updateMemberDto);
    } else {
      throw new RuntimeException("Invalid username or password");
    }
  }


}
