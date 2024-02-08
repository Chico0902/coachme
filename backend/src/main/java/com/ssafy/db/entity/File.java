package com.ssafy.db.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Entity
@Table(name = "Files")
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

  // methods

  /**
   * 파일 이름으로부터 타입을 찾는 메서드
   *
   * @Param
   * @Return : 파일타입
   */
  private String getFileExtension() {
    try {
      return this.name.substring(this.name.lastIndexOf(".") + 1);
    } catch (StringIndexOutOfBoundsException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일" + this.name + ") 입니다.");
    }
  }

  public File(Member member, String name, String url) {
    this.uploader = member;
    this.name = name;
    this.url = url;
  }
}
