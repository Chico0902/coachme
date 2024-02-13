package com.ssafy.util.file.repository;

import com.ssafy.db.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

  @Query(value = "SELECT f FROM File f " +
      "JOIN FETCH f.coaching c " +
      "WHERE c.coach.id = ?1")
  List<File> findByCoachIdWithCoaching(Long coachId);

  @Query("SELECT f FROM File f WHERE f.coaching.id = ?1")
  List<File> findByCoachingId(Long coachingId);
}
