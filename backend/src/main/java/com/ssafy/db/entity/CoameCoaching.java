package com.ssafy.db.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CoameCoaching extends BaseEntity{

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "coame_coaching_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member coame;

  @ManyToOne
  @JoinColumn(name = "coaching_id")
  private Coaching coaching;

  @OneToMany
  @JoinColumn(name ="live_coaching_id")
  private List<LiveCoaching> liveCoachings;
}
