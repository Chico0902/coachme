package com.ssafy.api.coaching.controller;

import com.ssafy.api.coaching.dto.request.CreateCoachingRequestDto;
import com.ssafy.api.coaching.dto.request.SearchWordsRequestDto;
import com.ssafy.api.coaching.dto.response.CoachingDetailResponseDto;
import com.ssafy.api.coaching.dto.response.CoameListResponseDto;
import com.ssafy.api.coaching.service.CoachingService;
import com.ssafy.dto.ListDataDto;
import com.ssafy.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coachings")
@RequiredArgsConstructor
@Slf4j
public class CoachingController {
  private final CoachingService coachingService;

  /**
   * [coaching-2] 코치가 코칭을 개설한다.
   * privilege : 2
   *
   * @return [200] 정상 개설완료
   */
  @PostMapping("/coachings/{longId}")
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
   * [coaching-6] 코미가 라이브 코칭을 수강 신청
   * privilege : 1
   *
   * @return [200] 정상 등록완료
   */
  @GetMapping("/live/{liveCoachingId}/{coameId}")
  public ResponseEntity<?> signUpClass(@PathVariable Long liveCoachingId, @PathVariable Long coameId) {

    coachingService.signUpClass(liveCoachingId, coameId);

    return new ResponseEntity<>(new MessageDto("sign up successfully !"), HttpStatus.OK);
  }

  /**
   * [coaching-7] 회원정보 권한 상승 요청 시, 유효한 요청인지 확인 후 권한 상승 목록에 추가한다.
   * privilege : 2
   *
   * @return [200] 코미 id, 이름, 프로필사진 URL
   */
  @GetMapping("/live/{id}/coames")
  public ResponseEntity<?> getCoameList(@PathVariable Long id) {
    List<CoameListResponseDto> responseList = coachingService.getCoameList(id);

    return new ResponseEntity<>(new ListDataDto(responseList), HttpStatus.OK);
  }

  /**
   * [coaching-3] 해당 분류 코칭의 정보를 받아온다.
   * privilege : 0
   *
   * @param division1 : 카테고리 테이블 내의 MAIN 분류 / all
   * @param division2 : 카테고리 테이블 내의 SUB 분류 / all
   * @param searchWordsRequestDto : 검색어 / all
   * @return - [200] list
   */
  @PostMapping("/categories/{division1}/{division2}")
  public ResponseEntity<ListDataDto> CoachingRequestDto(
      @PathVariable("division1") String division1,
      @PathVariable("division2") String division2,
      @RequestBody SearchWordsRequestDto searchWordsRequestDto) {
    ListDataDto listDataDto = new ListDataDto(coachingService.getCoachingList(division1, division2, searchWordsRequestDto.getWords()));
    return new ResponseEntity<>(listDataDto, HttpStatus.OK);
  }

  /**
   * [coaching-4] 코칭의 상세 페이지를 조회한다.
   * privilege : 0
   *
   * @param coachingId : 코칭 PK
   * @return - [200] list
   */
  @GetMapping("/{coachingId}")
  public ResponseEntity<CoachingDetailResponseDto> getCoachingDetail(@PathVariable("coachingId") long coachingId) {
    CoachingDetailResponseDto responseDto = coachingService.getCoachingDetail(coachingId);

    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }
}