package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class DM {
  @Id
  @GeneratedValue
  @Column(name = "dm_id")
  private Long dmId; // ?int였는데 혹시 이유가 있었는지 확인

  @ManyToOne
  @JoinColumn(name = "dmroom_id")
  private DMRoom dmRoom;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(nullable = false, length = 1000) //카카오 메세지 최대 1000자 반영
  private String message;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate; //BaseEntity 넣는게 나을 것 같음.
}
