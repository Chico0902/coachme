package com.ssafy.api.coach.controller;

import com.ssafy.api.coach.dto.request.CoachesRequestDto;
import com.ssafy.api.coach.dto.request.CreateLiveRequestDto;
import com.ssafy.api.coach.dto.request.PortfolioRequestDto;
import com.ssafy.api.coach.dto.response.CalendarResponseDto;
import com.ssafy.api.coach.dto.response.CoachDetailResponseDto;
import com.ssafy.api.coach.dto.response.PortfolioResponseDto;
import com.ssafy.api.coach.dto.response.VideoResponseDto;
import com.ssafy.api.coach.service.CoachService;
import com.ssafy.api.coaching.dto.request.CoachingInfoChangeRequestDto;
import com.ssafy.api.coaching.dto.request.CreateCoachingRequestDto;
import com.ssafy.api.coaching.dto.response.GetOneCoachingResponseDto;
import com.ssafy.api.coaching.service.CoachingService;
import com.ssafy.api.member.dto.response.ProfileImageResponseDto;
import com.ssafy.dto.ListDataDto;
import com.ssafy.dto.MessageDto;
import com.ssafy.util.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/coaches")
@RequiredArgsConstructor
@Slf4j
public class CoachController {

  private final CoachService coachService;
  private final CoachingService coachingService;

  /**
   * [coach-1] 해당 분류 코치의 정보를 받아온다.
   * privilege : 0
   *
   * @param division1 : 카테고리 테이블 내의 MAIN 분류 / all
   * @param division2 : 카테고리 테이블 내의 SUB 분류 / all
   * @return - [200] 코치 정보(list)
   */
  @PostMapping("/categories/{division1}/{division2}")
  public ResponseEntity<ListDataDto> getCoachList(
      @PathVariable("division1") String division1,
      @PathVariable("division2") String division2,
      @RequestBody CoachesRequestDto coachesRequestDto) {
    ListDataDto listDataDto = new ListDataDto(coachService.getCoachList(division1, division2, coachesRequestDto));
    return new ResponseEntity<>(listDataDto, HttpStatus.OK);
  }

  /**
   * [coach-2] 코치는 본인의 포트폴리오를 조회한다.
   *
   * @param id - 코치 longId
   * @return - [200] 포트폴리오 데이터
   */
  @GetMapping("/portfolio/{longId}")
  public ResponseEntity<PortfolioResponseDto> getPortfolio(@PathVariable("longId") long id) throws Exception {
    PortfolioResponseDto portfolio = coachService.getPortfolio(id);
    return new ResponseEntity<>(portfolio, HttpStatus.OK);
  }

  /**
   * [coach-3] 코치는 본인의 포트폴리오를 수정한다.
   *
   * @param id                  - 코치 longId
   * @param portfolioRequestDto - 수정할 포트폴리오 데이터
   * @return - [200] 수정 완료 메세지
   */
  @PatchMapping("/portfolio/{longId}")
  public ResponseEntity<MessageDto> updatePortfolio(
      @PathVariable("longId") long id,
      @RequestBody PortfolioRequestDto portfolioRequestDto) throws Exception {
    coachService.updatePortfolio(id, portfolioRequestDto);
    return new ResponseEntity<>(new MessageDto("Portfolio update successfully completed"), HttpStatus.OK);
  }

  /**
   * [coach-5] 코치가 코칭을 개설한다.
   * privilege : 2
   *
   * @return [200] 정상 개설완료
   */
  @PostMapping("/{longId}/coachings")
  public ResponseEntity<MessageDto> createCoaching(
      @PathVariable(name = "longId") Long longId,
      @RequestBody CreateCoachingRequestDto dto) throws Exception {
    // 코칭 등록
    coachingService.createCoaching(longId, dto);
    log.info("member id : {}", longId);
    // 정상 등록완료(200)
    return new ResponseEntity<>(new MessageDto("Coaching created successfully"), HttpStatus.CREATED);
  }

  /**
   * [coach-6] 코치가 등록한 모든 코칭을 조회한다.
   * privilege : 2
   *
   * @return [200] 정상 조회완료
   */
  @GetMapping("/{coachId}/coachings")
  public ResponseEntity<?> getCoachingList(@PathVariable(name = "coachId") Long longId) throws Exception {
    ListDataDto list = new ListDataDto(coachingService.getCoachesCoachings(longId));
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * [coach-7] 코치가 등록한 코칭 1개를 조회한다.
   * privilege : 2
   *
   * @return [200] 정상 조회완료
   */
  @GetMapping("/{coachId}/coaching/{id}")
  public ResponseEntity<GetOneCoachingResponseDto> getOneCoaching(
      @PathVariable(name = "coachId") Long longId,
      @PathVariable(name = "id") Long id) throws Exception {
    // 코칭 조회
    GetOneCoachingResponseDto dto = coachingService.getOneCoaching(longId, id);
    // 정상 등록완료(200)
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  /**
   * [coach-8] 코치가 코칭 정보를 수정한다.
   * privilege : 2
   *
   * @return [200] 정상 조회완료
   */
  @PatchMapping("/{coachId}/coaching/{id}")
  public ResponseEntity<MessageDto> changeCoachingInfo(
      @PathVariable(name = "coachId") Long longId,
      @PathVariable(name = "id") Long id,
      @RequestBody CoachingInfoChangeRequestDto dto) throws Exception {
    // 코칭 수정 정보 등록
    coachingService.changeCoachingInfo(longId, id, dto);
    // 정상 등록완료(200)
    return new ResponseEntity<>(new MessageDto("Coaching Info Change request successfully completed"), HttpStatus.OK);
  }

  /**
   * [coach-9] 코치가 자신의 코칭 1개를 삭제한다.
   * privilege : 2
   *
   * @return [200] 정상 조회완료
   */
  @DeleteMapping("/{coachId}/coaching/{id}")
  public ResponseEntity<MessageDto> deleteCoaching(
      @PathVariable(name = "coachId") Long longId,
      @PathVariable(name = "id") Long id) {
    // 코칭 수정 정보 등록
    coachingService.deleteCoaching(longId, id);
    // 정상 등록완료(200)
    return new ResponseEntity<>(new MessageDto("Coaching delete request successfully completed"), HttpStatus.OK);
  }

  /**
   * [coach-10] 코치가 마이페이지>라이브관리 메뉴에서 자신이 만든 라이브 코칭 일정을 캘린더로 확인할 수 있다.
   * privilege : 2
   *
   * @return - [200] list
   */
  @GetMapping("/{longId}/calendar")
  public ResponseEntity<?> getCalender(@PathVariable Long longId) {
    List<CalendarResponseDto> list = coachService.getCalender(longId);
    return new ResponseEntity<>(new ListDataDto(list), HttpStatus.OK);
  }

  /**
   * [coach-11] 코치의 상세 페이지를 조회한다.
   * privilege : 0
   *
   * @param coachId : 코치 PK (Member longId)
   * @return - [200] list
   */
  @GetMapping("/{coachId}")
  public ResponseEntity<CoachDetailResponseDto> getCoachDetail(@PathVariable("coachId") long coachId) {
    CoachDetailResponseDto responseDto = coachService.getCoachDetail(coachId);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  /**
   * [coach-12] 코치가 라이브 코칭을 생성한다.
   * privilege : 2
   *
   * @param createLiveRequestDto - 생성할 라이브 코칭 정보
   * @return - [200] 라이브 코칭 생성 완료 메세지
   */
  @PostMapping("/{coachId}/live")
  public ResponseEntity<?> createLiveCoaching(@RequestBody CreateLiveRequestDto createLiveRequestDto) {
    coachService.createLiveCoaching(createLiveRequestDto);
    return new ResponseEntity<>(new MessageDto("create live successfuly"), HttpStatus.OK);
  }

  /**
   * [coach-13] 코치가 자신의 영상 목록을 조회할 수 있다.
   * privilege : 2
   *
   * @param coachId - 코치 pk
   * @return - [200] 영상 정보 리스트
   */
  @GetMapping("/{coachId}/videos")
  public ResponseEntity<?> getVideos(@PathVariable Long coachId) {
    List<VideoResponseDto> list = coachService.getVideos(coachId);
    return new ResponseEntity<>(new ListDataDto(list), HttpStatus.OK);
  }

  /**
   * [coach-14] 코치는 영상을 업로드 할 수 있다.
   * privilege : 2
   *
   * @return - [200] 영상 업로드 성공 메세지
   */
  @PostMapping("/{coachId}/videos")
  public ResponseEntity<?> uploadVideo(
      @PathVariable(value = "coachId") Long coachId,
      @Validated @RequestParam("videoFile") MultipartFile videoFile,
      @RequestParam("fileName") String fileName,
      @RequestParam("coachingId") long coachingId
  ) {
    coachService.uploadVideo(coachId, videoFile, fileName, coachingId);
    return new ResponseEntity<>(new MessageDto("upload video successfully"), HttpStatus.OK);
  }

  /**
   * [coach-14] 모든 사용자가 메인페이지에서 인기코치를 확인할 수 있다.
   * privilege : 0
   *
   * @return - [200] list
   */
  @GetMapping("/popular")
  public ResponseEntity<ListDataDto> getPopularCoach() {
    ListDataDto responseDto = new ListDataDto(coachService.getPopularCoach());
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

}
