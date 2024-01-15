package com.ssafy.db.repository;

import com.ssafy.db.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Members, Long> {

}
