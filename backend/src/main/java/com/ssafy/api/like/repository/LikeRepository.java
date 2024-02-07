package com.ssafy.api.like.repository;

import com.ssafy.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Member, Long> {

}
