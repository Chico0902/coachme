package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class DMRoom {
  @Id
  @GeneratedValue
  @Column(name = "dmroom_id")
  private Long dmroomId; // ?int였는데 혹시 이유가 있었는지 확인

  @ManyToOne
  @JoinColumn(name = "coame_member_id")
  private Member coame;

  @ManyToOne
  @JoinColumn(name = "coach_member_id")
  private Member coach;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate; //BaseEntity 넣는게 나을 것 같음.
}
