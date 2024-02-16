package com.ssafy.api.review.controller;

import com.ssafy.api.review.dto.request.CoachReviewWriteRequestDto;
import com.ssafy.api.review.dto.request.CoachingReviewWriteRequestDto;
import com.ssafy.api.review.dto.request.ReviewUpdateRequestDto;
import com.ssafy.api.review.dto.response.CoachReviewsResponseDto;
import com.ssafy.api.review.dto.response.CoachingReviewsResponseDto;
import com.ssafy.api.review.service.ReviewService;
import com.ssafy.dto.ListDataDto;
import com.ssafy.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
  private final ReviewService reviewService;

  @PostMapping("/coach")
  public ResponseEntity<?> writeCoachReview(@RequestBody CoachReviewWriteRequestDto dto) throws Exception {
    reviewService.writeCoachReview(dto);
    return new ResponseEntity<>(new MessageDto("Coach review write successfully completed"), HttpStatus.OK);
  }

  @PostMapping("/coaching")
  public ResponseEntity<?> writeCoachingReview(@RequestBody CoachingReviewWriteRequestDto dto) throws Exception {
    reviewService.writeCoachingReview(dto);
    return new ResponseEntity<>(new MessageDto("Coaching review write successfully completed"), HttpStatus.OK);
  }

  @PatchMapping("/{reviewId}")
  public ResponseEntity<?> modifyReview(
      @PathVariable("reviewId") long reviewId,
      @RequestBody ReviewUpdateRequestDto dto) throws Exception {
    reviewService.modifyReview(reviewId, dto);
    return new ResponseEntity<>(new MessageDto("Review update successfully completed"), HttpStatus.OK);
  }

  @DeleteMapping("/{reviewId}")
  public ResponseEntity<?> deleteReview(@PathVariable("reviewId") long reviewId) throws Exception {
    reviewService.deleteReview(reviewId);
    return new ResponseEntity<>(new MessageDto("Review delete successfully completed"), HttpStatus.OK);
  }

  @GetMapping("/coach/{coachId}")
  public ResponseEntity<?> getCoachReviews(@PathVariable("coachId") long reviewId) throws Exception {
    List<CoachReviewsResponseDto> list = reviewService.getCoachReviews(reviewId);
    return new ResponseEntity<>(new ListDataDto(list), HttpStatus.OK);
  }

  @GetMapping("/coaching/{coachingId}")
  public ResponseEntity<?> getCoachingReviews(@PathVariable("coachingId") long reviewId) throws Exception {
    List<CoachingReviewsResponseDto> list = reviewService.getCoachingReviews(reviewId);

    return new ResponseEntity<>(new ListDataDto(list), HttpStatus.OK);
  }
}
