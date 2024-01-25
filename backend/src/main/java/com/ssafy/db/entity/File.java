package com.ssafy.db.entity;

import com.ssafy.db.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)  // ?
@Entity @Table(name = "Files")
@Builder @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "file_id")
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member uploader;

  @Column
  private String name;

  @Column
  private String url;

  // methods
}
