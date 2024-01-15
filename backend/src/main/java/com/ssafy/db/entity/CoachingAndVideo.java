package com.ssafy.db.entity;

import jakarta.persistence.*;

@Entity
public class CoachingAndVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="coaching_video_id")
    private Integer coachingVideoId;

    @ManyToOne
    @JoinColumn(name="coaching_id")
    private Coaching coaching;

    @ManyToOne
    @JoinColumn(name="file_id")
    private File file;


}
