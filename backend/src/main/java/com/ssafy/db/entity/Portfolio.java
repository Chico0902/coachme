package com.ssafy.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Portfolio {
  @Id
  @GeneratedValue
  @Column(name = "portfolio_id")
  private int portfolioId;

  @Column
  private String description;

  @CreatedDate
  @Column(name = "create_date")
  private LocalDateTime createDate;
}
