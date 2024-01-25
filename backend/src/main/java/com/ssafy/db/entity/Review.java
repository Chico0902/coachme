package com.ssafy.db.entity;

import com.ssafy.db.entity.type.ReviewLikeType;
import jakarta.persistence.*;

@Entity
public class Review extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "review_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coame_member_id")
  private Member coame;

  @ManyToOne
  @JoinColumn(name = "coach_member_id")
  private Member coach;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coaching_id")
  private Coaching coaching;

  @Column
  @Enumerated(EnumType.STRING)
  private ReviewLikeType reviewType;

  @Column
  private String comment;

  @Column
  private Integer score;
}
