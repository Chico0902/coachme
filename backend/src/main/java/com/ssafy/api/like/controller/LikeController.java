package com.ssafy.api.like.controller;

import com.ssafy.api.like.dto.response.CheckCoachLikeDto;
import com.ssafy.api.like.dto.response.CheckCoachingLikeDto;
import com.ssafy.api.like.service.LikeService;
import com.ssafy.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
@RequiredArgsConstructor
@Slf4j
public class LikeController {

  private final LikeService likeService;

  /**
   * [like-1] 코미는 코치를 찜콩한다.
   * privilege : 1
   *
   * @return [200] 코치 찜콩 완료
   */
  @GetMapping("/{coameId}/coaches/{coachId}")
  public ResponseEntity<MessageDto> coameLikeCoach(
      @PathVariable(name = "coameId") Long coameId,
      @PathVariable(name = "coachId") Long coachId) throws Exception {

    likeService.sendLikeCoach(coameId, coachId);

    return new ResponseEntity<>(new MessageDto("regist like successful"), HttpStatus.OK);
  }

  /**
   * [like-2] 코미는 코칭을 찜콩한다.
   * privilege : 1
   *
   * @return [200] 코칭 찜콩 완료
   */
  @GetMapping("/{coameId}/coachings/{coachingId}")
  public ResponseEntity<MessageDto> coameLikeCoaching(
      @PathVariable(name = "coameId") Long coameId,
      @PathVariable(name = "coachingId") Long coachingId) throws Exception {

    likeService.sendLikeCoaching(coameId, coachingId);

    return new ResponseEntity<>(new MessageDto("regist like successful"), HttpStatus.OK);
  }

  /**
   * [like-3] 코미는 코치 찜콩을 취소한다.
   * privilege : 1
   *
   * @return [200] 코치 찜콩 취소 완료.
   */
  @DeleteMapping("/{coameId}/coaches/{coachId}")
  public ResponseEntity<MessageDto> coameUnlikeCoach(
      @PathVariable(name = "coameId") Long coameId,
      @PathVariable(name = "coachId") Long coachId) throws Exception {

    likeService.unlikeCoach(coameId, coachId);

    // 코치 찜콩 취소 완료(200)
    return new ResponseEntity<>(new MessageDto("unliked coach successfully"), HttpStatus.OK);
  }

  /**
   * [like-4] 코미는 코칭 찜콩을 취소한다.
   * privilege : 1
   *
   * @return [200] 코치 찜콩 취소 완료.
   */
  @DeleteMapping("/{coameId}/coachings/{coachingId}")
  public ResponseEntity<MessageDto> coameunlikeCoaching(
      @PathVariable(name = "coameId") Long coameId,
      @PathVariable(name = "coachingId") Long coachingId) throws Exception {

    likeService.unlikeCoaching(coameId, coachingId);

    // 코칭 찜콩 취소 완료(200)
    return new ResponseEntity<>(new MessageDto("unliked coaching successfully"), HttpStatus.OK);
  }

  /**
   * [like-5] 코미는 찜콩한 코칭에 대해서 확인할 수 있다.
   * privilege : 1
   *
   * @return [200] 코칭 찜콩 확인 완료.
   */
  @GetMapping("/check/{coameId}/coachings/{coachingId}")
  public ResponseEntity<CheckCoachingLikeDto> coameChecklikeCoaching(
      @PathVariable(name = "coameId") Long coameId,
      @PathVariable(name = "coachingId") Long coachingId) throws Exception {

    CheckCoachingLikeDto dto = likeService.checkLikeCoaching(coameId, coachingId);

    // 코치 찜콩 취소 완료(200)
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }

  /**
   * [like-6] 코미는 찜콩한 코치에 대해서 확인할 수 있다.
   * privilege : 1
   *
   * @return [200] 코치 찜콩 확인 완료.
   */
  @GetMapping("/check/{coameId}/coaches/{coachId}")
  public ResponseEntity<CheckCoachLikeDto> coameChecklikeCoach(
      @PathVariable(name = "coameId") Long coameId,
      @PathVariable(name = "coachId") Long coachId) throws Exception {

    CheckCoachLikeDto dto = likeService.checkLikeCoach(coameId, coachId);

    // 코칭 찜콩 취소 완료(200)
    return new ResponseEntity<>(dto, HttpStatus.OK);
  }
}
