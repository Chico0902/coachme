package com.ssafy.api.coaching.controller;

import com.ssafy.api.coaching.dto.request.CoachingRequestDto;
import com.ssafy.api.coaching.dto.request.VideoRequestDto;
import com.ssafy.api.coaching.dto.response.CoachingDetailResponseDto;
import com.ssafy.api.coaching.dto.response.CoameListResponseDto;
import com.ssafy.api.coaching.dto.response.LiveCoachingsResponseDto;
import com.ssafy.api.coaching.dto.response.VideosInCoachingResponseDto;
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
   * [coaching-1] 코미가 라이브 코칭을 수강 신청
   * privilege : 1
   *
   * @return [200] 정상 등록완료
   */
  @GetMapping("/live/{liveCoachingId}/{coameId}")
  public ResponseEntity<?> signUpClass(@PathVariable Long liveCoachingId, @PathVariable Long coameId) {

    try {
      coachingService.signUpClass(liveCoachingId, coameId);
    } catch (Exception e) {
      return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(new MessageDto("sign up successfully !"), HttpStatus.OK);
  }

  /**
   * [coaching-2] 코치가 마이페이지>라이브관리에서 라이브코칭을 선택하면 해당 코칭을 수강하고 있는 수강생 목록을 조회한다.
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
   * @param division1          : 카테고리 테이블 내의 MAIN 분류 / all
   * @param division2          : 카테고리 테이블 내의 SUB 분류 / all
   * @param coachingRequestDto : 검색어 / all, 로그인 유저 longId / -1
   * @return - [200] list
   */
  @PostMapping("/categories/{division1}/{division2}")
  public ResponseEntity<ListDataDto> CoachingRequestDto(
      @PathVariable("division1") String division1,
      @PathVariable("division2") String division2,
      @RequestBody CoachingRequestDto coachingRequestDto) {

    ListDataDto listDataDto = new ListDataDto(coachingService.getCoachingList(division1, division2, coachingRequestDto));

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

  /**
   * [coaching-5] 메인페이지에서 인기코칭을 확인할 수 있다.
   * privilege : 0
   *
   * @return - [200] list
   */
  @GetMapping("/popular")
  public ResponseEntity<ListDataDto> getPopularCoaching() {

    ListDataDto responseDto = new ListDataDto(coachingService.getPopularCoaching());

    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  /**
   * [coaching-6] 코치가 특정 코칭의 대표 영상을 설정할 수 있다.
   * privilege : 2
   *
   * @param coachingId - 코칭 pk
   * @param fileId     - 파일 pk
   * @return - [200] 등록 성공 메세지
   */
  @GetMapping("/{coachingId}/videos/{fileId}")
  public ResponseEntity<?> registRepresentVideo(@PathVariable("coachingId") Long coachingId,
                                                @PathVariable("fileId") Long fileId) {

    coachingService.registRepresentVideo(coachingId, fileId);

    return new ResponseEntity<>(new MessageDto("regist video successfully"), HttpStatus.OK);
  }

  /**
   * [coaching-7] 코칭 상세페이지에서 해당 코칭의 라이브 코칭 목록을 조회한다.
   * privilege : 1
   *
   * @param coachingId - 코칭 pk
   * @return - [200] 라이브 코칭 정보 리스트
   */
  @GetMapping("/{coachingId}/live")
  public ResponseEntity<?> getLiveCoachingsByCoachingId(@PathVariable Long coachingId) {

    List<LiveCoachingsResponseDto> list = coachingService.getLiveCoachingsByCoachingId(coachingId);

    return new ResponseEntity<>(new ListDataDto(list), HttpStatus.OK);
  }

  /**
   * [coaching-8] 코칭 상세페이지에서 해당 코칭의 영상 목록을 조회한다.
   *
   * @param coachingId - 코칭 pk
   * @return - [200] 영상 url 리스트
   */
  @GetMapping("/{coachingId}/videos")
  public ResponseEntity<?> getVideosInCoaching(@PathVariable Long coachingId) {

    List<VideosInCoachingResponseDto> list = coachingService.getVideos(coachingId);

    return new ResponseEntity<>(new ListDataDto(list), HttpStatus.OK);
  }

  /**
   * [coaching-9] AI로 편집된 영상이 저장된 S3 URL을 전달받아 DB에 저장한다.
   * @param coachingId - 코칭 pk
   * @param videoRequestDto - 코치 pk, 파일 pk, url
   * @return - [200] 정상 처리 메세지
   */
  @PostMapping("/{coachingId}/videos")
  public ResponseEntity<?> registEditedVideo(@PathVariable Long coachingId, @RequestBody VideoRequestDto videoRequestDto) {

    coachingService.registEditedVideo(coachingId, videoRequestDto);

    return new ResponseEntity<>(new MessageDto("regist video successfully"), HttpStatus.OK);
  }
}
