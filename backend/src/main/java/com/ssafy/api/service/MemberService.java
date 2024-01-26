package com.ssafy.api.service;

import com.ssafy.api.request.RegistMemberDto;
import com.ssafy.db.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void regist(RegistMemberDto registMemberDto) {

    }
}
