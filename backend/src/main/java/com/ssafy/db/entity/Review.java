package com.ssafy.db.entity;

import com.ssafy.db.entity.type.ReviewLikeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ReviewLikeType reviewType;

  @Column(nullable = false, length = 500)
  private String comment;

  @Column(nullable = false)
  private Integer score;

  public void updateReview(String comment, Integer score) {
    this.comment = comment;
    this.score = score;
  }

}
