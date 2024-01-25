package com.ssafy.db.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class LiveCoaching extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "live_coaching_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "coaching_id")
  private Coaching coaching;

  @Column
  private LocalDateTime coachingDate;
}
