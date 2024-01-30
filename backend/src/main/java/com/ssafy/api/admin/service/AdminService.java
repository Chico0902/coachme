package com.ssafy.api.admin.service;

import com.ssafy.api.admin.dto.response.AdminElevationsResponseDto;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Member;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminService {

  private final MemberRepository memberRepository;

  /**
   * 아이디를 입력받아서 해당 아이디가 사용중인지 검증
   *
   * @return true : 아이디 사용중 / false : 사용가능
   */
  @Transactional(readOnly = true)
  public List<AdminElevationsResponseDto> getRequestedElevationList() throws EntityNotFoundException {

    // db에서 찾은 멤버
    List<Member> membersInDB = memberRepository.findByIsElevatedTrue(); // bach fetch = 50
    if (membersInDB == null || membersInDB.isEmpty()) throw new EntityNotFoundException();
    List<AdminElevationsResponseDto> list = new ArrayList<>();
    for (Member memberInDB : membersInDB) {
      //longId, name, portfolio
      list.add(new AdminElevationsResponseDto(memberInDB));
    }

    return list;
  }
}
