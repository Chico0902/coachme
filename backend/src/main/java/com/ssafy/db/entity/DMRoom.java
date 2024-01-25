package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class DMRoom {
  @Id
  @GeneratedValue
  @Column(name = "dmroom_id")
  private int dmroomId;

  @ManyToOne
  @JoinColumn(name = "coame_member_id")
  private Member coame;

  @ManyToOne
  @JoinColumn(name = "coach_member_id")
  private Member coach;

  @CreatedDate
  @Column(name = "create_date")
  private LocalDateTime createDate;
}
