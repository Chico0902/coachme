package com.ssafy.api.like.repository;

import com.ssafy.db.entity.Coaching;
import com.ssafy.db.entity.Likes;
import com.ssafy.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
  Likes findByCoameAndCoach(Member coame, Member coach);

  Likes findByCoameAndCoaching(Member coame, Coaching coaching);
}
