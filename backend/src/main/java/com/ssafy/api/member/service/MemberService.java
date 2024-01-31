package com.ssafy.api.member.service;

import com.ssafy.api.member.dto.request.ElevationRequestDto;
import com.ssafy.api.member.dto.request.MemberInfoChangeRequestDto;
import com.ssafy.api.member.dto.request.MemberRegistRequestDto;
import com.ssafy.api.member.mapper.MemberMapper;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

  private final MemberRepository memberRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * 회원정보 요청을 받아서 비밀번호를 암호화하고 Member 엔티티로 저장
   */
  public void regist(MemberRegistRequestDto dto) {

    // 비밀번호 암호화
    dto.setPw(bCryptPasswordEncoder.encode(dto.getPw()));

    // DTO -> 멤버 변환
    Member member = MemberMapper.instance.memberRegistRequestDtoToMember(dto);

    // member 상태와 권한 초기화
    member.initMemberPrivilegeAndStatus();

    log.info(member.getEmail());

    // member 저장
    memberRepository.save(member);
  }


  /**
   * 회원정보 수정 요청을 받아서 비밀번호를 검증하고 회원정보 수정
   */
  public void modify(Long longId, MemberInfoChangeRequestDto dto) {

    // DB에서 멤버 찾아오기
    Member memberInDB = memberRepository.getReferenceById(longId);

    // 비밀번호가 일치하지 않으면 오류 출력
    if (!bCryptPasswordEncoder.matches(dto.getPw(), memberInDB.getPassword()))
      throw new BadCredentialsException("패스워드 불일치");

    // member 상태 변경
    memberInDB.changeMemberStatus(dto.getNick(), dto.getEmail());
  }

  /**
   * id와 htmlDocs를 입력받아서 해당 id의 멤버의 포트폴리오 업데이트 및 권한상승 요청리스트에 추가
   */
  public void requestElevation(ElevationRequestDto dto) {

    // 멤버 찾기
    Member memberInDB = memberRepository.getReferenceById(dto.getLongId());

      // 포트폴리오 업데이트, 권한상승 요청리스트에 추가
    memberInDB.elevateRequest(dto.getHtmlDocs());

    log.info("계정 권한상승 요청 성공");
  }

  /**
   * 아이디를 입력받아서 해당 아이디가 사용중인지 검증
   * @return true : 아이디 사용중 / false : 사용가능
   */
  @Transactional(readOnly = true)
  public boolean isDuplicated(String stringId) {

    // db에서 찾은 멤버
    List<Member> membersInDB = memberRepository.findByStringId(stringId);
    if (membersInDB == null || membersInDB.isEmpty()) return false;
    return true;
  }

}
