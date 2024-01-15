package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class DMs {
    @Id
    @GeneratedValue
    private int dm_id;

    @ManyToOne
    @JoinColumn(name = "dmroom_id")
    private DMRooms dmRoom;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Members member;

    @Column
    private String message;

    @CreatedDate
    @Column
    private LocalDateTime create_date;
}
