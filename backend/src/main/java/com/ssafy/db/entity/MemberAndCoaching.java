package com.ssafy.db.entity;

import jakarta.persistence.*;

@Entity
public class MemberAndCoaching {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mycoachingList;

    @ManyToOne
    @JoinColumn(name="coaching_date_id")
    private CoachingDates coachingDateId;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Members memberId;

    private String createDate;


}
