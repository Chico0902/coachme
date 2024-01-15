package com.ssafy.db.entity;

import jakarta.persistence.*;

@Entity
public class CoachingAndVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer coachingVideoId;

    @ManyToOne
    @JoinColumn(name="coaching_id")
    private Coachings coaching;

    @ManyToOne
    @JoinColumn(name="file_id")
    private Files files;


}
