package com.ssafy.api.coaching.repository;

import com.ssafy.db.entity.Coaching;
import com.ssafy.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachingRepository extends JpaRepository<Coaching, Long> {

}
