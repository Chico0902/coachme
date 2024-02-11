package com.ssafy.api.coaching.repository;

import com.ssafy.api.coach.dto.response.CoachesResponseDtos;
import com.ssafy.api.coaching.dto.response.CoachingResponseDtos;
import com.ssafy.db.entity.Coaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachingRepository extends JpaRepository<Coaching, Long> {
  @Query(value = "SELECT " +
      "    m.member_id as coachId, " +
      "    m.name as memberName, " +
      "    Max(c.name) as lessonInfo, " +
      "    f.url as profileImg, " +
      "    Count(r.review_id) as reviewCount, " +
      "    AVG(r.score) as avgScore " +
      "FROM coaching c " +
      "JOIN member m ON m.member_id = c.coach_id " +
      "LEFT JOIN files f ON m.profile_image_id = f.file_id " +
      "LEFT JOIN review r ON r.coach_member_id = c.coach_id " +
      "WHERE (:subCategoryId IS NULL OR c.sub_category_id = :subCategoryId) " +
      "AND (:mainCategoryId IS NULL OR c.main_category_id = :mainCategoryId) " +
      "AND (:words IS NULL OR m.name like %:words%) " +
      "AND (:memberId = -1 OR m.member_id != :memberId) " +
      "group by m.name, m.string_id, f.url", nativeQuery = true)
  List<CoachesResponseDtos> findByCoachCategory(Long mainCategoryId, Long subCategoryId, String words, long memberId);

  @Query(value = "SELECT " +
      "    c.coach_id as coachId, " +
      "    m.name as memberName, " +
      "    f.url as profileImg, " +
      "    c.coaching_id as coachingId, " +
      "    c.name as coachingName," +
      "    COALESCE(Count(r.review_id), 0) as reviewCount, " +
      "    COALESCE(AVG(r.score), 0) as avgScore " +
      "FROM coaching c " +
      "JOIN member m ON m.member_id = c.coach_id " +
      "LEFT JOIN files f ON m.profile_image_id = f.file_id " +
      "LEFT JOIN review r ON r.coaching_id = c.coaching_id " +
      "WHERE (:subCategoryId IS NULL OR c.sub_category_id = :subCategoryId) " +
      "AND (:mainCategoryId IS NULL OR c.main_category_id = :mainCategoryId) " +
      "AND (:words IS NULL OR c.name like %:words%) " +
      "GROUP BY c.coaching_id, m.name, f.url, c.coaching_id, c.name", nativeQuery = true)
  List<CoachingResponseDtos> findByCoachingCategory(Long mainCategoryId, Long subCategoryId, String words);

  @Query(value = "SELECT c FROM Coaching c " +
      "WHERE c.coach.longId = :longId")
  List<Coaching> findByCoachId(Long longId);

  @Query("SELECT c FROM Review r right JOIN r.coaching c GROUP BY c ORDER BY AVG(r.score) DESC limit 5")
  List<Coaching> findByPopularCoaching();

}
