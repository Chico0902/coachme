package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "review_id")
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "coame_member_id")
    private Member coame;

    @ManyToOne
    @JoinColumn(name = "coaching_id")
    private Coaching coaching;

    @ManyToOne
    @JoinColumn(name = "coach_member_id")
    private Member coach;

    @Column
    private String comment;

    @Column
    private Integer score;

    @LastModifiedDate
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;
}
