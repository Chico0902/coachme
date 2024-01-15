package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Files")
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer fileId;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Members member;

    @ManyToOne
    @JoinColumn(name="portfolio_id")
    private Portfolios portfolio;

    private String name;
    private String url;
    private String type;
    @LastModifiedDate
    private LocalDateTime modifyDate;
    @CreatedDate
    private LocalDateTime createDate;
}
