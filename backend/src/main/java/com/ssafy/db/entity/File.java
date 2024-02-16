package com.ssafy.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Files")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class File extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "file_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member uploader;

  @ManyToOne
  @JoinColumn(name = "coaching_id")
  private Coaching coaching;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 500)
  private String url;

  public File(Member member, String name, String url) {
    this.uploader = member;
    this.name = name;
    this.url = url;
  }

  public void createFile(Member member, Coaching coaching, String url, String name) {
    this.uploader = member;
    this.coaching = coaching;
    this.url = url;
    this.name = name;
    coaching.getVideoCoachings().add(this);
  }
}
