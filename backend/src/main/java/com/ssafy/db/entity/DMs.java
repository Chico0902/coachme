package com.ssafy.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class DMs {
    @Id
    @GeneratedValue
    private int dm_id;
}
