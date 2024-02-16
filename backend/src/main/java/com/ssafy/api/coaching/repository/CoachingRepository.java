package com.ssafy.api.coaching.repository;

import com.ssafy.db.entity.Coaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachingRepository extends JpaRepository<Coaching, Long> {
  @Query(value = "SELECT c FROM Coaching c " +
      "WHERE (:subCategoryName = 'all' OR c.subCategory.name = :subCategoryName) " +
      "AND (:mainCategoryName = 'all' OR c.mainCategory.name = :mainCategoryName) " +
      "AND (:words = 'all' OR c.name like %:words%) " +
      "AND (:memberId = -1 OR c.coach.longId != :memberId) ")
  List<Coaching> findByCoachingCategory(String mainCategoryName, String subCategoryName, String words, long memberId);


  @Query(value = "SELECT c FROM Coaching c " +
      "WHERE (:subCategoryName = 'all' OR c.subCategory.name = :subCategoryName) " +
      "AND (:mainCategoryName = 'all' OR c.mainCategory.name = :mainCategoryName) " +
      "AND (:words = 'all' OR c.coach.name like %:words%) " +
      "AND (:memberId = -1 OR c.coach.longId != :memberId) ")
  List<Coaching> findByCoachCategory(String mainCategoryName, String subCategoryName, String words, long memberId);

  @Query(value = "SELECT c FROM Coaching c " +
      "WHERE c.coach.longId = :longId")
  List<Coaching> findByCoachId(Long longId);

  @Query("SELECT c FROM Review r right JOIN r.coaching c GROUP BY c ORDER BY AVG(r.score) DESC limit 5")
  List<Coaching> findByPopularCoaching();

}
