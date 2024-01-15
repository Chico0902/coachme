package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class DMs {
    @Id
    @GeneratedValue
    @Column(name = "dm_id")
    private int dmId;

    @ManyToOne
    @JoinColumn(name = "dmroom_id")
    private DMRooms dmRoom;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Members member;

    @Column
    private String message;

    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;
}
