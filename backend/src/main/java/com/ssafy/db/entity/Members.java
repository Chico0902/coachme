package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
public class Members {

    @Id
    private String member_id;

    @Column
    private String password;

    @Column(columnDefinition = "INT DEFAULT 1")
    private int privilege;

    @Column
    private String name;

    @Column
    private String nickname;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String description;

    @LastModifiedDate
    @Column(nullable = true)
    private LocalDateTime modify_date;

    @CreatedDate
    @Column
    private LocalDateTime create_date;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int elevation;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolios portfolios;


}
