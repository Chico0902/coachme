package com.ssafy.db.entity;

import jakarta.persistence.*;

@Entity
public class Likes {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "like_id")
  private Integer likeId;

  @ManyToOne
  @JoinColumn(name = "coach_member_id")
  private Member coach;

  @ManyToOne
  @JoinColumn(name = "coame_member_id")
  private Member coame;

  @ManyToOne
  @JoinColumn(name = "coaching_id")
  private Coaching coaching;

  @Column(name = "is_mycoaching")
  private Integer isMycoaching;

}
