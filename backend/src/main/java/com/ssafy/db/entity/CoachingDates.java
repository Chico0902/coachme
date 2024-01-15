package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class CoachingDates {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JoinColumn(name="coaching_date_id")
    private Integer coachingDateId;

    @ManyToOne
    @JoinColumn(name="coaching_id")
    private Coachings coachingId;

    @CreatedDate
    @Column(name = "coaching_date")
    private LocalDateTime coachingDate;

}
