package com.ssafy.db.entity;

import com.ssafy.db.entity.coaching.Coaching;
import com.ssafy.db.entity.member.Member;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class Like extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "like_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member coame;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member coach;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "coaching_id")
  private Coaching coaching;

  @Column
  private ReviewLikeType likeType;
}
