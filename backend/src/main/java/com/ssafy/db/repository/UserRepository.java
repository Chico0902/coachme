package com.ssafy.db.repository;

import com.ssafy.db.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Long> {

}
