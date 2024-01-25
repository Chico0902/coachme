package com.ssafy.db.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "DMRoom")
@Builder
@Getter
public class DMRoom {
  protected DMRoom() {
  }

  protected DMRoom(int dmroomId, Member coame, Member coach, LocalDateTime createDate) {
    this.dmroomId = dmroomId;
    this.coame = coame;
    this.coach = coach;
    this.createDate = createDate;
  }

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
