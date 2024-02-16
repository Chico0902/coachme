package com.ssafy.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

  @Column
  @ColumnDefault(value = "0")
  private Boolean status;

  public void addCoameCoahing(CoameCoaching coameCoaching) {
    this.coameCoachings.add(coameCoaching);
  }

  public void createLiveCoaching(Coaching coaching, LocalDateTime date) {
    this.coaching = coaching;
    this.coachingDate = date;
    coaching.addLiveCoaching(this);
  }
  public void changeLiveCoachingStatus(){
    this.status = true;
  }
}
