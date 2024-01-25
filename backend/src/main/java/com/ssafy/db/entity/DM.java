package com.ssafy.db.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "DM")
@Builder
@Getter
public class DM {
  protected DM() {
  }

  protected DM(int dmId, DMRoom dmRoom, Member member, String message, LocalDateTime createDate) {
    this.dmId = dmId;
    this.dmRoom = dmRoom;
    this.member = member;
    this.message = message;
    this.createDate = createDate;
  }

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

  @Column(name = "create_date")
  private LocalDateTime createDate;

}
