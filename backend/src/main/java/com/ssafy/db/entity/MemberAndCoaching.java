package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
public class MemberAndCoaching {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mycoachingList;

    @ManyToOne
    @JoinColumn(name="coaching_date_id")
    private CoachingDates coachingDates;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Members member;

    @CreatedDate
    private LocalDate createDate;


}