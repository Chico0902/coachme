package com.ssafy.db.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class DMRooms {
    @Id
    @GeneratedValue
    private int dmroom_id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Members coame;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Members coach;

    @CreatedDate
    @Column
    private LocalDateTime create_date;
}
