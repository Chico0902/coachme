package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Coachings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="coaching_id")
    private String coachingId;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Members memberId;

    @Column(name="category_i")
    private String categoryI;

    @Column(name="category_ii")
    private String categoryII;

    @Column(name="coaching_name")
    private String coachingName;

    @Column
    private Integer price;

    @Column
    private String description;

    @Column
    private String summary;

    @CreatedDate
    @Column(name = "create_date")
    private LocalDate createDate;

}
