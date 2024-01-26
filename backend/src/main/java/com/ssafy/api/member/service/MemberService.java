package com.ssafy.api.member.service;

import com.ssafy.api.member.Util.EncryptionUtils;
import com.ssafy.api.member.dto.request.MemberDuplicateRequestDto;
import com.ssafy.api.member.dto.request.MemberRegistRequestDto;
import com.ssafy.api.member.mapper.MemberMapper;
import com.ssafy.db.repository.MemberRepository;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ssafy.api.member.Util.EncryptionUtils.encryptSHA256;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

  private final MemberRepository memberRepository;

  /**
   * [member-2] 회원정보 요청을 받아서 Member 엔티티로로 저장
   * 회원가입 요청 시 비밀번호를 암호화하여 저장
   */
  public void regist(MemberRegistRequestDto dto) {

    // 비밀번호 암호화
    dto.setPw(encryptSHA256(dto.getPw()));

    // DTO -> 멤버 변환
    Member member = MemberMapper.instance.memberRegistRequestDtoToMember(dto);

    // member 상태와 권한 초기화
    member.initMemberPrivilegeAndStatus();

    // member 저장
    memberRepository.save(member);
  }

  /**
   * [member-14] 아이디를 입력받아서 해당 아이디가 사용중인지 검증
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
