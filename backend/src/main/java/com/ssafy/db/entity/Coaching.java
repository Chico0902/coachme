package com.ssafy.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Coaching extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "coaching_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "coach_id")
  private Member coach;

  @ManyToOne
  @JoinColumn(name = "coame_id")
  private Member coame;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "main_category_id")
  private Category mainCategory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sub_category_id")
  private Category subCategory;

  @Column(nullable = false, length = 50)
  private String name;

  @OneToMany(mappedBy = "coaching")
  private List<CoameCoaching> coameCoachings = new ArrayList<>();

  @OneToMany(mappedBy = "coaching")
  private List<VideoCoaching> videoCoachings = new ArrayList<>();

  @Column
  private Integer price;

  // RAW HTML로 들어온 해당 코칭에 대한 설명 페이지
  @Column(nullable = false, length = 5000)
  private String htmlDocs;

  @Column(nullable = false, length = 100)
  private String summary;

  @OneToMany(mappedBy = "coaching")
  private List<Likes> receivedLikes = new ArrayList<>();

  @OneToMany(mappedBy = "coaching")
  private List<Review> receivedReviews = new ArrayList<>();

  // method

}
