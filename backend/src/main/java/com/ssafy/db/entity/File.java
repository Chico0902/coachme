package com.ssafy.db.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.StringTokenizer;

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

  @Column(nullable = false, length = 50) //?null이 가능한지
  private String name;

  @Column(nullable = false, unique = true, length = 500) //?url이라서 길이가 길 것 같다.
  private String url;

  // methods
  /**
   * 파일 이름으로부터 타입을 찾는 메서드
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
}
