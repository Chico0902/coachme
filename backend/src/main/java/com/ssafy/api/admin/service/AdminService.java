package com.ssafy.api.admin.service;


import com.ssafy.api.admin.dto.request.AdminElevateRequestDto;
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
public class AdminService {
  private final MemberRepository memberRepository;

  public void requestElevatePermission(AdminElevateRequestDto dto) {
    for (int i = 0; i < dto.getId().size(); i++) {
      Member member = memberRepository.getReferenceById(dto.getId().get(i));
      member.elevatePermissionRequest();
    }
  }
}
