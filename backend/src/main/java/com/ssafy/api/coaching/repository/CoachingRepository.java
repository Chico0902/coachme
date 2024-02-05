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
      "m.name as memberName, " +
      "Max(c.name) as lessonInfo, " +
      "f.url as profileImg, " +
      "Count(r.review_id) as reviewCount, " +
      "AVG(r.score) as avgScore " +
      "FROM coaching c " +
      "JOIN member m ON m.member_id = c.coach_id " +
      "LEFT JOIN files f ON m.profile_image_id = f.file_id " +
      "LEFT JOIN review r ON r.coach_member_id = c.coach_id " +
      "WHERE (:subCategoryId IS NULL OR c.sub_category_id = :subCategoryId) " +
      "AND (:mainCategoryId IS NULL OR c.main_category_id = :mainCategoryId) " +
      "group by m.name, m.string_id, f.url", nativeQuery = true)
  List<CoachesResponseDtos> findByCoachCategory(Long mainCategoryId, Long subCategoryId);

  @Query(value = "SELECT " +
      "    c.name as coachingName," +
      "    m.name as memberId," +
      "    f.url as profileImg," +
      "    Count(r.review_id) as reviewCount," +
      "    AVG(r.score) as avgScore " +
      "FROM coaching c " +
      "JOIN member m ON m.member_id = c.coach_id " +
      "LEFT JOIN files f ON m.profile_image_id = f.file_id " +
      "LEFT JOIN review r ON r.coach_member_id = c.coach_id " +
      "WHERE (:subCategoryId IS NULL OR c.sub_category_id = :subCategoryId) " +
      "AND (:mainCategoryId IS NULL OR c.main_category_id = :mainCategoryId) " +
      "GROUP BY c.name, m.member_id, m.string_id, f.url, c.main_category_id", nativeQuery = true)
  List<CoachingResponseDtos> findByCoachingCategory(Long mainCategoryId, Long subCategoryId);

}
