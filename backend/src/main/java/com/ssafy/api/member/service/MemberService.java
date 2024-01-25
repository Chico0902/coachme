package com.ssafy.api.member.service;

import com.ssafy.api.auth.service.CustomUserDetailsService;
import com.ssafy.api.member.dto.MemberInfoResponseDto;
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
  //  private final FindPwService findPwService;
  private final CustomUserDetailsService customUserDetailsService;

  //[member-14] 받은 회원 ID가 존재하는지 확인
  @Transactional(readOnly = true)
  public boolean checkMemberId(String memberId) {
    Member existingMember = customUserDetailsService.loadUserByUsername(memberId);
    return existingMember == null;
  }

  // [member-2] 회원가입(회원 정보 등록). 생성일자 기록되는거 확인완료.
  public void regist(RegistMemberDto RegistMemberDto) { //? 두번째 RegistMemberDto를 registMemberDto로 변경해야 하는지

    Member member = Member.builder()
        .stringId(RegistMemberDto.getId())
        .password(RegistMemberDto.getPw())
        .name(RegistMemberDto.getName())
        .nickName(RegistMemberDto.getNick())
        .email(RegistMemberDto.getEmail())
        .build();

    memberRepository.save(member);
  }

  // [member-5] 기본 회원정보 조회
  @Transactional(readOnly = true)
  public MemberInfoResponseDto findById(Long id) {
//    log.info("memberId : {}", memberId);
    Member member = memberRepository.findById(id); // 아이디로 회원 찾기
    // 회원의 정보를 담아서 보내준다.
//    RegistMemberDto RegistMemberDto = MemberMapper.instance.memberToRegistMemberDto(member);
    return MemberMapper.instance.memberToMemberInfoResponseDto(member);

    // 아래 코드가 필요없는 이유 : 회원정보의 아이디를 토대로 조회하기 때문에 회원정보가 무조건 존재함
//    if(member != null) {
////      log.info("memberId : {}", memberId);
//      return RegistMemberDto;
//    } else {
//      return null;
//    }
  }

  // [member-13] 회원 삭제
  public void deleteMember(Long id) {
    Member existingMember = memberRepository.findById(id);
    memberRepository.delete(existingMember);

    // 아래 코드가 필요없는 이유 : 회원정보의 아이디를 토대로 조회하기 때문에 회원정보가 무조건 존재함
//    if (existingMember != null) {
//      memberRepository.delete(existingMember);
//    } else {
//      throw new RuntimeException("Member not found");
//    }
  }

  // [member-6] 회원정보 수정
  public void updateMember(Long id, UpdateMemberDto updateMemberDto) {
    Member existingMember = memberRepository.findById(id);
    String password1 = existingMember.getPassword();
    String password2 = updateMemberDto.getPw();

    // 회원이 기입한 비밀번호가 저장되어있는 비밀번호와 일치하는지 확인
    if (password1.equals(password2)) {
      // 일치하면 회원정보 수정
      existingMember.changeMemberStatus(updateMemberDto.getNick(), updateMemberDto.getEmail());
    } else {
      throw new RuntimeException("Invalid username or password");
    }
  }


}
