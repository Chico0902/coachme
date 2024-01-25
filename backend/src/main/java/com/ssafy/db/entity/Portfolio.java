package com.ssafy.db.entity;

import com.ssafy.db.entity.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
public class Portfolio extends BaseEntity {
  @Id @GeneratedValue
  @Column(name = "portfolio_id")
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  @Column
  private Member member;

  @Column
  private String htmlDocs;
}
