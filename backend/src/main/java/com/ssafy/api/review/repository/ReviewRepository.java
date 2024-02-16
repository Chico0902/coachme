package com.ssafy.api.review.repository;

import com.ssafy.db.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  @Query("select r from Review r where r.coach.longId = :coachId")
  List<Review> findAllByCoachId(long coachId);

  @Query("select r from Review r where r.coaching.id = :coachingId")
  List<Review> findAllByCoachingId(long coachingId);

}
