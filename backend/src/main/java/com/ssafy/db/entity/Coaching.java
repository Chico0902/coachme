package com.ssafy.db.entity;

import com.ssafy.db.entity.type.CoachingType;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Coaching extends BaseEntity {
  @Id @Column(name = "coaching_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  // 코미의 리스트인지 코치의 리스트인지 확인하는 타입
  @Column
  @Enumerated(EnumType.STRING)
  private CoachingType coachingType;

  @OneToOne(fetch = FetchType.LAZY)
  private Category mainCategory;

  @OneToOne(fetch = FetchType.LAZY)
  private Category subCategory;

  @Column
  private String name;

  @OneToMany(mappedBy = "coaching")
  @Column
  private List<CoameCoaching> coameCoachings;

  @OneToMany(mappedBy = "coaching")
  @Column
  private List<VideoCoaching> videoCoachings;

  @Column
  private Integer price;

  // RAW HTML로 들어온 해당 코칭에 대한 설명 페이지
  @Column
  private String htmlDocs;

  @Column
  private String summary;

  @OneToMany
  @JoinColumn()
  private List<Like> receivedLikes;

  @OneToMany
  @JoinColumn()
  private List<Review> receivedReviews;

  // method

}
