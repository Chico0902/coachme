package com.ssafy.api.like.controller;

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
   * @return [200] 정상 등록완료
   */
  @PostMapping("/{coachId}/coaches/{memberId}")
  public ResponseEntity<?> likeCoach(@PathVariable(name = "coachId") Long coachId, @PathVariable(name = "memberId") Long coameId) {

    likeService.likeCoach(coachId, coameId);

    return new ResponseEntity<>(new MessageDto("sign up successfully !"), HttpStatus.OK);
  }

  /**
   * [like-1] 코미는 코치를 찜콩한다.
   * privilege : 1
   *
   * @return [200] 정상 등록완료
   */
//  @PostMapping("/{coachId}/coaches/{memberId}")
//  public ResponseEntity<?> signUpClass(@PathVariable Long liveCoachingId, @PathVariable Long coameId) {
//
//    coachingService.signUpClass(liveCoachingId, coameId);
//
//    return new ResponseEntity<>(new MessageDto("sign up successfully !"), HttpStatus.OK);
//  }
}
