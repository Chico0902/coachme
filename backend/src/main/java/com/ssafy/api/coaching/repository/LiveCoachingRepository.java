package com.ssafy.api.coaching.repository;

import com.ssafy.db.entity.Coaching;
import com.ssafy.db.entity.LiveCoaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LiveCoachingRepository extends JpaRepository<LiveCoaching, Long> {
  @Query("SELECT lc FROM LiveCoaching lc " +
      "JOIN FETCH lc.coameCoachings cc " +
      "JOIN FETCH cc.coame cm " +
      "JOIN FETCH cm.profileImage pi " +
      "WHERE lc.id = ?1")
  LiveCoaching findByIdWithDetails(Long liveCoachingId);
}
