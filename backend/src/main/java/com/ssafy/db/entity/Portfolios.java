package com.ssafy.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Portfolios {
    @Id
    @GeneratedValue
    private int portfolio_id;

    @Column
    private String description;

    @CreatedDate
    @Column
    private LocalDateTime create_date;
}
