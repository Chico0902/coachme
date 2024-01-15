package com.ssafy.db.entity;

import jakarta.persistence.*;

@Entity
@Table(name="Files")
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer fileId;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Members memberId;

//    @ManyToOne
//    @JoinColumn(name="portfolio_id")
//    private String portfolioId;

    private String name;
    private String url;
    private String type;
    private String modifyDate;
    private String createDate;


}
