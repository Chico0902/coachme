package com.ssafy.db.entity;

import com.ssafy.db.entity.type.CategoryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Long id;

  @Column(nullable = false, unique = true, length = 50) // unique?
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false) // null이면 검색을 못함
  private CategoryType categoryType;

  @ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  @OneToMany(mappedBy = "parent")
  private List<Category> children = new ArrayList<>();

  @OneToMany(mappedBy = "mainCategory")
  private List<Coaching> mainCategoryCoachings = new ArrayList<>();

  @OneToMany(mappedBy = "subCategory")
  private List<Coaching> subCategoryCoachings = new ArrayList<>();

  /**
   * 이 메서드는 서브카테고리에서 실행합니다!!
   */
  public void addOneCategory(Coaching coaching) {
    this.mainCategoryCoachings.add(coaching);
  }

  public void addCategoryList(Coaching coaching) {
    this.mainCategoryCoachings.add(coaching);
    this.subCategoryCoachings.add(coaching);
  }

}
