package com.ssafy.api.member.controller;

import com.ssafy.api.coach.dto.response.CalendarResponseDto;
import com.ssafy.api.coaching.dto.response.CoameTakingCoachingDto;
import com.ssafy.api.member.dto.request.*;
import com.ssafy.api.member.dto.response.MemberInfoResponseDto;
import com.ssafy.api.member.dto.response.ProfileImageResponseDto;
import com.ssafy.api.member.dto.response.ProfileResponseDto;
import com.ssafy.api.member.service.MemberService;
import com.ssafy.dto.ListDataDto;
import com.ssafy.dto.MessageDto;
import com.ssafy.util.file.service.FileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
  private final MemberService memberService;
  private final FileService fileService;

  /**
   * [member-2] 회원가입 요청 시 해당 정보를 DB에 저장한다.
   * privilege : ALL
   *
   * @return [201] 정상 등록완료
   */
  @PostMapping
  public ResponseEntity<MessageDto> registMember(@RequestBody MemberRegistRequestDto dto) throws Exception {

    // 회원 등록(service)
    memberService.regist(dto);

    // 정상 등록완료(200)
    return new ResponseEntity<>(new MessageDto("Member registered successfully"), HttpStatus.CREATED);
  }

  /**
   * [member-4] 회원정보 권한 상승 요청 시, 유효한 요청인지 확인 후 권한 상승 목록에 추가한다.
   * privilege : 1
   *
   * @return [200] 정상 등록완료
   */
  @PostMapping("privileges/elevations")
  public ResponseEntity<MessageDto> updateMemberToElevations(@RequestBody @Validated ElevationRequestDto dto) throws Exception {

    // 회원 등록(service)
    memberService.requestElevation(dto);

    // 정상 등록완료(200)
    return new ResponseEntity<>(new MessageDto("Member request elevation successfully finished"), HttpStatus.OK);
  }

  /**
   * [member-5] 기본 회원정보 조회
   * privilege : 1
   *
   * @return [200] 기본 회원정보 데이터 전송
   */
  @GetMapping("/{longId}")
  public ResponseEntity<MemberInfoResponseDto> giveMemberInfo(@PathVariable(name = "longId") Long longId) throws Exception {

    // 기본 회원정보 조회(service)
    MemberInfoResponseDto memberInfo = memberService.getMemberInfo(longId);

    // 기본 회원정보 데이터 전송(200)
    return new ResponseEntity<>(memberInfo, HttpStatus.OK);
  }

  /**
   * [member-6] 회원정보 수정 시 입력한 비밀번호를 검증한 후, 회원정보 변경
   * privilege : COAME
   *
   * @return [200] 정상 수정완료
   */
  @PatchMapping("/{longId}")
  public ResponseEntity<MessageDto> modifyMember(
      @PathVariable(value = "longId") Long longId,
      @RequestBody MemberInfoChangeRequestDto dto) throws Exception {

    // 회원정보 수정(service)
    memberService.modify(longId, dto);

    // 정상 수정완료(200)
    return new ResponseEntity<>(new MessageDto("Member modified successfully"), HttpStatus.OK);
  }

  /**
   * [member-7] 코미나 코치의 프로필 글과 사진에 대한 조회
   * privilege : COAME
   *
   * @return [200] 프로필 글 조회결과 전송
   */
  @GetMapping("/{longId}/profiles")
  public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable(value = "longId") Long longId) throws Exception {

    // 프로필 글, 사진 조회
    ProfileResponseDto dto = memberService.requestProfile(longId);

    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  /**
   * [member-8] 코치나 코미가 프로필 글을 등록/수정한다.
   * privilege : COAME
   *
   * @return [200] 정상 등록 완료
   */
  @PostMapping("/{longId}/profiles/texts")
  public ResponseEntity<MessageDto> uploadProfileText(
      @PathVariable(value = "longId") Long longId,
      @Valid @RequestBody ProfileTextRequestDto dto) throws Exception {
    // 프로필 글 수정
    memberService.uploadProfileText(longId, dto);

    return new ResponseEntity<>(new MessageDto("Profile text upload successfully"), HttpStatus.OK);
  }

  /**
   * [member-9] 코치나 코미가 프로필 사진을 등록/수정한다.
   * privilege : COAME
   *
   * @return [200] 정상 등록 완료
   */
  @PostMapping("/{longId}/profiles/images")
  public ResponseEntity<?> uploadProfileImage(
      @PathVariable(value = "longId") Long longId, @Validated @RequestParam MultipartFile newFile) throws Exception {
    // 프로필 사진 등록
    ProfileImageResponseDto profileImageResponseDto = memberService.uploadProfileImage(longId, newFile);
    return new ResponseEntity<>(profileImageResponseDto, HttpStatus.OK);
  }

  /**
   * [member-12] 코치나 코미가 프로필 사진을 삭제한다.
   * privilege : COAME
   *
   * @return [200] 정상 등록 완료
   */
  @DeleteMapping("/{longId}/profiles/images")
  public ResponseEntity<?> deleteProfileImage(
      @PathVariable(value = "longId") Long longId) throws Exception {
    // 프로필 사진 등록
    memberService.deleteProfileImage(longId);
    return new ResponseEntity<>(new MessageDto("프로필 이미지 삭제완료"), HttpStatus.OK);
  }

  /**
   * [member-14] 회원 ID를 중복검사한다.
   * privilege : ALL
   *
   * @return [200] 사용 가능한 아이디
   */
  @PostMapping("/duplicate/id")
  public ResponseEntity<MessageDto> duplicateMember(@Validated @RequestBody MemberDuplicateRequestDto dto) throws Exception {

    // 중복여부 확인(service)
    boolean duplicated = memberService.isDuplicated(dto.getStringId());

    // 중복 되었을 때 client 오류(409)
    if (duplicated) return new ResponseEntity<>(
        new MessageDto("Member is duplicated"), HttpStatus.CONFLICT);

    // 중복 안되었을 때 사용 가능한 아이디(200)
    return new ResponseEntity<>(
        new MessageDto("Member is not duplicated, Usable id."), HttpStatus.OK);

  }

  /**
   * [member-15] 코미가 마이페이지>코칭일정 메뉴에서 자신이 신청한 코칭 일정을 캘린더로 확인할 수 있다.
   * privilege : 1
   *
   * @return [200] 신청한 코칭 정보 리스트
   */
  @GetMapping("/{longId}/calendar")
  public ResponseEntity<?> getCalendar(@PathVariable Long longId) {

    List<CalendarResponseDto> list = memberService.getCalendar(longId);

    return new ResponseEntity<>(new ListDataDto(list), HttpStatus.OK);
  }

  /**
   * [member-16] 코미가 자신이 수강신청한 코칭을 확인할 수 있다.
   * privilege : 1
   *
   * @return [200] 신청한 코칭 정보 리스트
   */
  @GetMapping("/{coameId}/coachings")
  public ResponseEntity<?> getCoameTakingCoaching(@PathVariable(name = "coameId") Long longId) {

    List<CoameTakingCoachingDto> list = memberService.getCoameCoachingList(longId);

    return new ResponseEntity<>(new ListDataDto(list), HttpStatus.OK);
  }
}