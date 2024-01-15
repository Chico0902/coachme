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
    private Coachings coaching;

    @ManyToOne
    @JoinColumn(name="file_id")
    @Column(name="create_date")
    private Files files;


}
