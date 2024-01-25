package com.ssafy.db.entity;

import com.ssafy.db.entity.type.CategoryType;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
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
}
