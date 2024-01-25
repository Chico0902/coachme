package com.ssafy.db.entity.coaching;

import com.ssafy.db.entity.*;
import com.ssafy.db.entity.category.Category;
import com.ssafy.db.entity.member.Member;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
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

  @Column
  private Category mainCategory;

  @Column
  private Category subCategory;

  @Column
  private String name;

  @OneToMany
  @JoinColumn(name = "coame_coaching_id")
  @Column
  private List<CoameCoaching> coameCoachings;

  @OneToMany
  @JoinColumn(name = "video_coaching_id")
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
