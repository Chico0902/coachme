package com.ssafy.api.coach.controller;

import com.ssafy.api.coach.dto.CoachesCoachingsResponseDto;
import com.ssafy.api.coaching.dto.CoachingInfoChangeRequestDto;
import com.ssafy.api.coaching.dto.CreateCoachingRequestDto;
import com.ssafy.api.coaching.dto.GetOneCoachingResponseDto;
import com.ssafy.api.coaching.service.CoachingService;
import com.ssafy.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coaches")
@RequiredArgsConstructor
@Slf4j
public class CoachController {
  private final CoachingService coachingService;

  /**
   * [coach-5] 코치가 코칭을 개설한다.
   * privilege : 2
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
   * [coach-6] 코치가 등록한 모든 코칭을 조회한다.
   * privilege : 2
   * @return [200] 정상 조회완료
   */
  @GetMapping("/{coachId}/coachings")
  public ResponseEntity<Map<String, List<CoachesCoachingsResponseDto>>> getCoachingList(
      @PathVariable(name = "coachId") Long longId) throws Exception {
    Map<String, List<CoachesCoachingsResponseDto>> response = new HashMap<>();
    // 해당 코치의 코칭 리스트 담기
    response.put("data", coachingService.getCoachesCoachings(longId));
    // 정상 등록완료(200)
    return ResponseEntity.ok(response);
  }
  /**
   * [coach-7] 코치가 등록한 코칭 1개를 조회한다.
   * privilege : 2
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


}
