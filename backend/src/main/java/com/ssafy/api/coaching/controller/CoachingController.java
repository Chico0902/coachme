package com.ssafy.api.coaching.controller;

import com.ssafy.api.coaching.dto.CreateCoachingRequestDto;
import com.ssafy.api.coaching.dto.GetOneCoachingResponseDto;
import com.ssafy.api.coaching.service.CoachingService;
import com.ssafy.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coaches")
@RequiredArgsConstructor
@Slf4j
public class CoachingController {
  private final CoachingService coachingService;

  /**
   * [coaching-2] 코치가 코칭을 개설한다.
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
   * [coaching-?] 코치가 등록한 코칭을 조회한다.
   * privilege : 2
   * @return [200] 정상 조회완료
   */
//  @GetMapping("/{coachId}/coachings/{id}")
//  public ResponseEntity<GetOneCoachingResponseDto> createCoaching(
//      @PathVariable(name = "coachId") Long longId,
//      @PathVariable(name = "coachId") Long id) throws Exception {
//    // 코칭 조회
//    GetOneCoachingResponseDto dto = coachingService.getOneCoaching(longId, id);
//    // 정상 등록완료(200)
//    return new ResponseEntity<>(dto, HttpStatus.OK);
//  }

}
