package com.ssafy.api.member.service;

import com.ssafy.api.member.Util.EncryptionUtils;
import com.ssafy.api.member.dto.request.MemberDuplicateRequestDto;
import com.ssafy.api.member.dto.request.MemberRegistRequestDto;
import com.ssafy.api.member.mapper.MemberMapper;
import com.ssafy.db.repository.MemberRepository;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    String encryptedPw = encryptSHA256(dto.getPw());
    dto.setPw(encryptedPw);

    // Member -> DTO 변환
    Member member = MemberMapper.instance.memberRegistRequestDtoToMember(dto);

    memberRepository.save(member);
  }

  /**
   * [member-14] 아이디를 입력받아서 해당 아이디가 사용중인지 검증
   * @Returns true : 아이디 사용중 / false : 사용가능
   */
  @Transactional(readOnly = true)
  public boolean isDuplicated(MemberDuplicateRequestDto dto) {
    List<Member> members = memberRepository.findByStringId(dto.getStringId());
    if (members == null || members.isEmpty()) {
      return false;
    } else {
      return true;
    }
  }
}
