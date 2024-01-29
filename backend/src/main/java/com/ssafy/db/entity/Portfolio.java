package com.ssafy.db.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Portfolio {
  @Id @GeneratedValue
  @Column(name = "portfolio_id")
  private Long id;

  @Column(nullable = false, length = 5000)
  private String htmlDocs;

  // methods
  public Portfolio(String newHtmlDocs) { this.htmlDocs = newHtmlDocs; }
  public void uploadHtmlDocs(String newHtmlDocs) {
    this.htmlDocs = newHtmlDocs;
  }
}
