package com.ssafy.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @LastModifiedDate
    @Column(name = "modify_date", nullable = true)
    private LocalDateTime modifyDate;

    @CreatedDate
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;
}
