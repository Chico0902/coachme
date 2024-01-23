package com.ssafy.db.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "Files")
@Builder
@Getter
public class File {
  protected File() {
  }

  protected File(Integer fileId, Member member, Portfolio portfolio, String name, String url, String type, String format, LocalDateTime modifyDate, LocalDateTime createDate) {
    this.fileId = fileId;
    this.member = member;
    this.portfolio = portfolio;
    this.name = name;
    this.url = url;
    this.type = type;
    this.format = format;
    this.modifyDate = modifyDate;
    this.createDate = createDate;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "file_id")
  private Integer fileId;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "portfolio_id")
  private Portfolio portfolio;

  @Column
  private String name;
  @Column
  private String url;
  @Column
  private String type;
  @Column
  private String format;

  @LastModifiedDate
  @Column(name = "modify_date")
  private LocalDateTime modifyDate;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate;
}
