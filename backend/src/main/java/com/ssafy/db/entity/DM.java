package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class DM {
  @Id
  @GeneratedValue
  @Column(name = "dm_id")
  private int dmId;

  @ManyToOne
  @JoinColumn(name = "dmroom_id")
  private DMRoom dmRoom;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Column
  private String message;

  @CreatedDate
  @Column(name = "create_date")
  private LocalDateTime createDate;
}
