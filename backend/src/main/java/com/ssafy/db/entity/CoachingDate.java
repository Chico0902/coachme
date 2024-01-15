package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class CoachingDate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="coaching_date_id")
    private Integer coachingDateId;

    @ManyToOne
    @JoinColumn(name="coaching_id")
    private Coaching coaching;

    @CreatedDate
    @Column(name = "coaching_date")
    private LocalDateTime coachingDate;

}
