package com.ssafy.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class LiveCoaching extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "live_coaching_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "coaching_id")
  private Coaching coaching;

  @OneToMany(mappedBy = "liveCoaching", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<CoameCoaching> coameCoachings = new ArrayList<>();

  @Column(name = "coaching_date", nullable = false)
  private LocalDateTime coachingDate;

  public void addCoameCoahing(CoameCoaching coameCoaching) {
    this.coameCoachings.add(coameCoaching);
  }
}
