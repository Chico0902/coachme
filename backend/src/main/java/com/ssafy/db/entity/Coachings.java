package com.ssafy.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coachings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String coachingId;

}
