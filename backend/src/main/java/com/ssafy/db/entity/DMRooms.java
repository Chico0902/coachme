package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class DMRooms {
    @Id
    @GeneratedValue
    @Column(name = "dmroom_id")
    private int dmroomId;

    @ManyToOne
    @JoinColumn(name = "coame_member_id")
    private Members coame;

    @ManyToOne
    @JoinColumn(name = "coach_member_id")
    private Members coach;

    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime createDate;
}
