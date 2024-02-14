package com.ssafy.api.livecoaching.service;

import com.ssafy.api.livecoaching.dto.response.LiveCoachingMemberResponseDto;
import com.ssafy.api.member.mapper.MemberMapper;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LiveCoachingService {
  private final MemberRepository memberRepository;


  public LiveCoachingMemberResponseDto getMemberInfo(Long memberId) {
    Member member = memberRepository.getReferenceById(memberId);
    return MemberMapper.instance.memberToLiveCoachingMemberDto(member);
  }
}
