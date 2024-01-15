package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
public class Member {

    @Id
    @Column(name = "member_id")
    private String memberId;

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
    @Column(name = "modify_date", nullable = true)
    private LocalDateTime modifyDate;

    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(columnDefinition = "INT DEFAULT 0")
    private int elevation;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;
}
