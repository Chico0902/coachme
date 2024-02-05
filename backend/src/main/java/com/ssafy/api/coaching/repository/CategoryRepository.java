package com.ssafy.api.coaching.repository;

import com.ssafy.db.entity.Category;
import com.ssafy.db.entity.type.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query(value = "SELECT c.id from Category c where c.categoryType = :type and c.name = :name")
  Long findByCategoryTypeAndName(CategoryType type, String name);

  Category findByName(String name);
}
