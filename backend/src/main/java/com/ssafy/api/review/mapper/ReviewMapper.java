package com.ssafy.api.review.mapper;

import com.ssafy.api.review.dto.response.CoachReviewsResponseDto;
import com.ssafy.api.review.dto.response.CoachingReviewsResponseDto;
import com.ssafy.db.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReviewMapper {
  ReviewMapper instance = Mappers.getMapper(ReviewMapper.class);

  @Mapping(source = "id", target = "reviewId")
  @Mapping(source = "coame.longId", target = "coameId")
  @Mapping(source = "coach.longId", target = "coachId")
  @Mapping(source = "coame.nickName", target = "nickName")
  CoachReviewsResponseDto reviewToCoachReviewsResponseDto(Review review);

  List<CoachReviewsResponseDto> reviewToCoachReviewsResponseDtoList(List<Review> reviewList);

  @Mapping(source = "id", target = "reviewId")
  @Mapping(source = "coame.longId", target = "coameId")
  @Mapping(source = "coaching.id", target = "coachingId")
  @Mapping(source = "coame.nickName", target = "nickName")
  CoachingReviewsResponseDto reviewToCoachingReviewsResponseDto(Review review);

  List<CoachingReviewsResponseDto> reviewToCoachingReviewsResponseDtoList(List<Review> reviewList);

}
