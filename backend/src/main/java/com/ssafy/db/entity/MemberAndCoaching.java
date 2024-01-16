package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class MemberAndCoaching {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "mycoaching_list")
  private Integer mycoachingList;

  @ManyToOne
  @JoinColumn(name = "coaching_date_id")
  private CoachingDate coachingDate;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @CreatedDate

  private LocalDateTime createDate;


}
