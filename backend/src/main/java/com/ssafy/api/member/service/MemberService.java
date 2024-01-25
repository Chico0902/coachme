package com.ssafy.api.member.service;

import com.ssafy.api.dto.request.MemberDuplicateRequestDto;
import com.ssafy.api.dto.request.MemberRegistRequestDto;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

  private final MemberRepository memberRepository;

  // [member-2] 회원정보 요청을 받아서 Member 엔티티로로 저장
  public void regist(MemberRegistRequestDto dto) {
    Member member = Member.builder()
        .stringId(dto.getStringId())
        .password(dto.getPw())
        .name(dto.getName())
        .nickName(dto.getNick())
        .email(dto.getEmail())
        .build();

    memberRepository.save(member);
  }

  /**
   * [member-14] 아이디를 입력받아서 해당 아이디가 사용중인지 검증
   * @Returns true : 아이디 사용중 / false : 사용가능
   */
  public boolean isDuplicated(MemberDuplicateRequestDto dto) {
    List<Member> members = memberRepository.findByStringId(dto.getStringId());
    if (members.size() == 1) {
      return true;
    } else {
      return false;
    }
  }
}
