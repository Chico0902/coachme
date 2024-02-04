package com.ssafy.api.coaching.repository;

import com.ssafy.db.entity.Category;
import com.ssafy.db.entity.Coaching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Category findByName(String name);
}
