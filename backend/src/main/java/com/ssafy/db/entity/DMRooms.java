package com.ssafy.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class DMRooms {
    @Id
    @GeneratedValue
    private int dmroom_id;
}
