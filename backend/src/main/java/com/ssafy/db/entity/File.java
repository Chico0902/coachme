package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "Files")
public class File {
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
  @LastModifiedDate
  @Column(name = "modify_date")
  private LocalDateTime modifyDate;
  @CreatedDate
  @Column(name = "create_date")
  private LocalDateTime createDate;
}
