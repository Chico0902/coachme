package com.ssafy.api.member.service;

import com.ssafy.api.coaching.dto.response.CoameTakingCoachingDto;
import com.ssafy.api.coaching.repository.CoachingRepository;
import com.ssafy.api.member.dto.request.ElevationRequestDto;
import com.ssafy.api.member.dto.request.MemberInfoChangeRequestDto;
import com.ssafy.api.member.dto.request.MemberRegistRequestDto;
import com.ssafy.api.member.dto.request.ProfileTextRequestDto;
import com.ssafy.api.member.dto.response.*;
import com.ssafy.api.member.mapper.MemberMapper;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.db.entity.*;
import com.ssafy.util.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

  private final MemberRepository memberRepository;
  private final FileService fileService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final CoachingRepository coachingRepository;

  /**
   * 회원정보 요청을 받아서 비밀번호를 암호화하고 Member 엔티티로 저장
   */
  public void regist(MemberRegistRequestDto dto) {

    // 비밀번호 암호화
    dto.setPw(bCryptPasswordEncoder.encode(dto.getPw()));

    // DTO -> 멤버 변환
    Member member = MemberMapper.instance.memberRegistRequestDtoToMember(dto);

    // member 상태와 권한 초기화
    member.initMemberStatus();

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
   *
   * @return true : 아이디 사용중 / false : 사용가능
   */
  @Transactional(readOnly = true)
  public boolean isDuplicated(String stringId) {

    // db에서 찾은 멤버
    List<Member> membersInDB = memberRepository.findByStringId(stringId);
    if (membersInDB == null || membersInDB.isEmpty()) return false;
    return true;
  }

  /**
   * 아이디를 입력받아 해당 멤버의 프로필 글과 사진을 조회
   *
   * @return profileText, profileImageUrl;
   */
  public ProfileResponseDto requestProfile(Long longId) {
    Member member = memberRepository.getReferenceById(longId);
    return new ProfileResponseDto(member.getProfileText(), member.getProfileImage().getUrl());
  }

  /**
   * 유저 아이디와 프로필 내용 입력받아 프로필 글 수정
   */
  public void uploadProfileText(Long longId, ProfileTextRequestDto dto) {
    Member member = memberRepository.getReferenceById(longId);
    member.updateProfileText(dto.getProfileText());

  }

  /**
   * 유저 아이디와 프로필 사진 입력받아 프로필 사진 수정
   */
  public ProfileImageResponseDto uploadProfileImage(Long longId, MultipartFile newFile) {
    // 기존의 프로필 이미지 삭제
    Member memberInDB = memberRepository.getReferenceById(longId);
    File file = memberInDB.getProfileImage();
    if (file != null) {
      fileService.deleteFile(file.getId());
    }

    // 프로필 사진 등록
    fileService.uploadFileList(longId, Arrays.asList(newFile));

    return new ProfileImageResponseDto(memberInDB.getProfileImage().getUrl());
  }

  /**
   * 유저 pk로 회원정보 조회
   */
  public MemberInfoResponseDto getMemberInfo(Long longId) {
    Member memberInDB = memberRepository.getReferenceById(longId);
    return new MemberInfoResponseDto(memberInDB);
  }

  /**
   * 회원 프로필 사진 삭제
   */
  public void deleteProfileImage(Long longId) {
    Member memberInDB = memberRepository.getReferenceById(longId);
    File file = memberInDB.getProfileImage();
    fileService.deleteFile(file.getId());

    memberInDB.updateProfileImageToDefault();
  }

  /**
   * 코미가 수강신청한 코칭 리스트 반환하는 메서드
   *
   * @param longId - 코미 Id
   * @return - 코칭 정보 리스트
   */
  public List<CalendarResponseDto> getCalendar(Long longId) {

    Member member = memberRepository.findByIdWithDetail(longId);
    List<CalendarResponseDto> list = new ArrayList<>();

    for (CoameCoaching coameCoaching : member.getCoameTaughtCourses()) {
      LiveCoaching liveCoaching = coameCoaching.getLiveCoaching();

      CalendarResponseDto calendarResponseDto = new CalendarResponseDto();
      calendarResponseDto.setId(liveCoaching.getId());
      calendarResponseDto.setClassName(liveCoaching.getCoaching().getName());
      String[] dateAndTime = getDateAndTime(liveCoaching.getCoachingDate());
      calendarResponseDto.setDate(dateAndTime[0]);
      calendarResponseDto.setTime(dateAndTime[1]);
      list.add(calendarResponseDto);
    }

    return list;
  }

  /**
   * 시간을 날짜와 시간으로 포맷팅하는 메서드
   *
   * @param localDateTime - 라이브 코칭 시간
   * @return - 날짜와 시간을 담은 String 배열
   */
  public String[] getDateAndTime(LocalDateTime localDateTime) {

    String[] result = new String[2];
    DateTimeFormatter format_date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    DateTimeFormatter format_time = DateTimeFormatter.ofPattern("HH:mm");

    result[0] = localDateTime.format(format_date);
    result[1] = localDateTime.format(format_time);

    return result;
  }

  /**
   * 코미가 수강하는 코칭 리스트 반환하는 메서드
   *
   * @param longId - 코미 Id
   * @return - 코칭 정보 리스트
   */
  @Transactional(readOnly = true)
  public List<CoameTakingCoachingDto> getCoameCoachingList(Long longId) {

    Member member = memberRepository.getReferenceById(longId);
    List<CoameTakingCoachingDto> list = new ArrayList<>();

    for (CoameCoaching coameCoaching : member.getCoameTaughtCourses()) {
      LiveCoaching liveCoaching = coameCoaching.getLiveCoaching();

      CoameTakingCoachingDto coameTakingCoachingDto = new CoameTakingCoachingDto();
      coameTakingCoachingDto.setId(liveCoaching.getId());
      coameTakingCoachingDto.setCoachingName(liveCoaching.getCoaching().getName());
      coameTakingCoachingDto.setCoachName(liveCoaching.getCoaching().getCoach().getName());
      list.add(coameTakingCoachingDto);
    }

    return list;
  }
}
