package com.ssafy.db.entity;

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
public class CoameCoaching extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "coame_coaching_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member coame;

  @ManyToOne
  @JoinColumn(name = "live_coaching_id")
  private LiveCoaching liveCoaching;

  public void createCoaching(LiveCoaching liveCoaching, Member member) {
    this.liveCoaching = liveCoaching;
    this.coame = member;
    liveCoaching.addCoameCoahing(this);
    member.addCoameTaughtCourse(this);
  }

}
