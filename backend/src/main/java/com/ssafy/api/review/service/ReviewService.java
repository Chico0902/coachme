package com.ssafy.api.review.service;

import com.ssafy.api.coaching.repository.CoachingRepository;
import com.ssafy.api.member.repository.MemberRepository;
import com.ssafy.api.review.dto.request.CoachReviewWriteRequestDto;
import com.ssafy.api.review.dto.request.CoachingReviewWriteRequestDto;
import com.ssafy.api.review.dto.request.ReviewUpdateRequestDto;
import com.ssafy.api.review.dto.response.CoachReviewsResponseDto;
import com.ssafy.api.review.dto.response.CoachingReviewsResponseDto;
import com.ssafy.api.review.mapper.ReviewMapper;
import com.ssafy.api.review.repository.ReviewRepository;
import com.ssafy.db.entity.Coaching;
import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.Review;
import com.ssafy.db.entity.type.ReviewLikeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReviewService {
  private final MemberRepository memberRepository;
  private final ReviewRepository reviewRepository;
  private final CoachingRepository coachingRepository;

  public void writeCoachReview(CoachReviewWriteRequestDto dto) throws Exception {
    Member coachId = memberRepository.getReferenceById(dto.getCoachId());
    Member coameId = memberRepository.getReferenceById(dto.getCoameId());

    Review review = Review.builder()
        .coach(coachId)
        .coame(coameId)
        .reviewType(ReviewLikeType.COACH)
        .comment(dto.getComment())
        .score(dto.getScore())
        .build();

    reviewRepository.save(review);
    memberRepository.getReferenceById(dto.getCoachId()).addCoachReview(review);
  }

  public void writeCoachingReview(CoachingReviewWriteRequestDto dto) {
    Coaching coaching = coachingRepository.getReferenceById(dto.getCoachingId());
    Member coameId = memberRepository.getReferenceById(dto.getCoameId());

    Review review = Review.builder()
        .coaching(coaching)
        .coame(coameId)
        .reviewType(ReviewLikeType.COACHING)
        .comment(dto.getComment())
        .score(dto.getScore())
        .build();

    reviewRepository.save(review);
    coachingRepository.getReferenceById(dto.getCoachingId()).addCoachingReview(review);
  }

  public void modifyReview(long reviewId, ReviewUpdateRequestDto dto) {
    Review review = reviewRepository.getReferenceById(reviewId);
    review.updateReview(dto.getComment(), dto.getScore());
  }

  public void deleteReview(long reviewId) {
    reviewRepository.deleteById(reviewId);
  }

  public List<CoachReviewsResponseDto> getCoachReviews(long coachId) {
    List<Review> reviews = memberRepository.getReferenceById(coachId).getReceivedReviews();

    return ReviewMapper.instance.reviewToCoachReviewsResponseDtoList(reviews);
  }

  public List<CoachingReviewsResponseDto> getCoachingReviews(long coachingId) {
    List<Review> reviews = coachingRepository.getReferenceById(coachingId).getReceivedReviews();

    return ReviewMapper.instance.reviewToCoachingReviewsResponseDtoList(reviews);
  }
}
