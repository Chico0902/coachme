package com.ssafy.db.entity;

import com.ssafy.db.entity.type.CoachingType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Coaching extends BaseEntity {
  @Id @Column(name = "coaching_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "coach_id")
  private Member coach;

  @ManyToOne
  @JoinColumn(name = "coame_id")
  private Member coame;

  // 코미의 리스트인지 코치의 리스트인지 확인하는 타입
  @Column
  @Enumerated(EnumType.STRING)
  private CoachingType coachingType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "main_category_id")
  private Category mainCategory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sub_category_id")
  private Category subCategory;

  @Column
  private String name;

  @OneToMany(mappedBy = "coaching")
  private List<CoameCoaching> coameCoachings = new ArrayList<>();

  @OneToMany(mappedBy = "coaching")
  private List<VideoCoaching> videoCoachings = new ArrayList<>();

  @Column
  private Integer price;

  // RAW HTML로 들어온 해당 코칭에 대한 설명 페이지
  @Column
  private String htmlDocs;

  @Column
  private String summary;

  @OneToMany(mappedBy = "coaching")
  private List<Like> receivedLikes = new ArrayList<>();

  @OneToMany(mappedBy = "coaching")
  private List<Review> receivedReviews = new ArrayList<>();

  // method

}
