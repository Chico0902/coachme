package com.ssafy.api.member.repository;

import com.ssafy.db.entity.Member;
import com.ssafy.db.entity.status.ElevateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  List<Member> findByStringId(String stringId);

  @Query(value = "SELECT m FROM Member m join fetch m.portfolio " +
      "WHERE m.elevateStatus = ?1")
  List<Member> findByElevateStatusWithPortfolio(ElevateStatus elevateStatus);

  @Query(value = "SELECT m FROM Member m " +
      "JOIN FETCH m.coameTaughtCourses ctc " +
      "JOIN FETCH m.profileImage pi " +
      "JOIN FETCH ctc.liveCoaching lc " +
      "WHERE lc.id = ?1")
  List<Member> findByLiveCoachingIdWithDetails(Long liveCoachingId);


  @Query("SELECT c FROM Review r right JOIN r.coach c GROUP BY c ORDER BY AVG(r.score) DESC limit 5")
  List<Member> findByPopularCoach();
}