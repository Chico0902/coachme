package com.ssafy.db.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Entity
@Table(name = "Files")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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
}
