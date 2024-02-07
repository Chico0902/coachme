package com.ssafy.db.entity;

import com.ssafy.db.entity.type.ReviewLikeType;
import jakarta.persistence.*;

@Entity
public class Likes extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "like_id")
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

  @Enumerated(value = EnumType.STRING)
  private ReviewLikeType likeType;

  public void addLikeCoach(Member coame, Member coach) {
    this.coame = coame;
    this.coach = coach;
    this.likeType = ReviewLikeType.COACH;
  }
}
