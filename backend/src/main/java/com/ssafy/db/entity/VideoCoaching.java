package com.ssafy.db.entity;

import com.ssafy.db.entity.coaching.Coaching;
import jakarta.persistence.*;

@Entity
public class VideoCoaching {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "video_coaching_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "coaching_id")
  private Coaching coaching;

  @ManyToOne
  @JoinColumn(name = "file_id")
  private File file;
}
