package com.ssafy.api.admin.service;

import com.ssafy.api.admin.dto.request.AdminElevateRequestDto;
import com.ssafy.api.admin.dto.response.AdminElevationsResponseDto;
import com.ssafy.api.admin.dto.response.AdminMembersResponseDto;
import com.ssafy.api.member.mapper.MemberMapper;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.status.ElevateStatus;
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

  @Transactional(readOnly = true)
  public List<AdminMembersResponseDto> getAllMembersList() throws EntityNotFoundException {
    List<Member> members = memberRepository.findAll();
    log.info("member count : {}", members.get(0).getName());
    List<AdminMembersResponseDto> list = MemberMapper.instance.memberToAdminMemberResponseDto(members);
    log.info("list count : {}", list);

    return list;
  }

  /**
   * 권한 상승 요청한 id를 입력받아 권한 상승
   */
  public void requestElevatePermission(AdminElevateRequestDto dto) {

    // DTO를 찾아서 요청 ID를 넣어주고, 결과로 반환
    for (Long id : dto.getIds()) memberRepository.getReferenceById(id).elevatePermissionRequest();

  }

  /**
   * 아이디를 입력받아서 해당 아이디가 사용중인지 검증
   *
   * @return true : 아이디 사용중 / false : 사용가능
   */
  @Transactional(readOnly = true)
  public List<AdminElevationsResponseDto> getRequestedElevationList() throws EntityNotFoundException {

    // db에서 찾은 멤버
    List<Member> membersInDB = memberRepository.findByElevateStatusWithPortfolio(ElevateStatus.REQUEST);

    // 검색한 멤버가 없으면 오류
    if (membersInDB == null || membersInDB.isEmpty()) throw new EntityNotFoundException();

    // 검색한 멤버 DTO에 삽입
    List<AdminElevationsResponseDto> list = new ArrayList<>();
    for (Member memberInDB : membersInDB) list.add(new AdminElevationsResponseDto(memberInDB));

    return list;
  }
}