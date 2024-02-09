package com.ssafy.api.coaching.repository;

import com.ssafy.db.entity.LiveCoaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LiveCoachingRepository extends JpaRepository<LiveCoaching, Long> {
  @Query("SELECT lc FROM LiveCoaching lc " +
      "JOIN FETCH lc.coameCoachings cc " +
      "JOIN FETCH cc.coame cm " +
      "JOIN FETCH cm.profileImage pi " +
      "WHERE lc.id = ?1")
  LiveCoaching findByIdWithDetails(Long liveCoachingId);

  @Query("SELECT lc FROM LiveCoaching lc " +
      "JOIN FETCH lc.coaching cc " +
      "JOIN FETCH cc.coach c " +
      "WHERE c.longId = ?1")
  List<LiveCoaching> findByCoachId(Long coachId);

  @Query("SELECT lc FROM LiveCoaching lc " +
      "JOIN FETCH lc.coaching cc " +
      "JOIN FETCH cc.coach c " +
      "Join FETCH lc.coameCoachings ccc " +
      "WHERE ccc.coame.longId = ?1")
  List<LiveCoaching> findByCoameId(Long coameId);
}
