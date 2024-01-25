package com.ssafy.db.entity.category;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private Long id;

    @Column
    private String name;

    @Column
    private CategoryType categoryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column
    private Category parent;

    @OneToMany
    @Column
    private List<Category> children;
}
