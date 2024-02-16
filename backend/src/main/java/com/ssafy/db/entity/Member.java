package com.ssafy.db.entity;

import com.ssafy.db.entity.status.ElevateStatus;
import com.ssafy.db.entity.status.MemberStatus;
import com.ssafy.db.entity.type.Privilege;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Slf4j
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "member_id")
  private Long longId;

  @Column(nullable = false, unique = true, length = 20)
  private String stringId;

  @Column(nullable = false, unique = true, length = 100)
  private String password;  // 암호화 저장

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 10)
  private String nickName;

  @Column(nullable = false, length = 50)
  private String email;

  @Column(length = 100)
  private String profileText;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "profile_image_id")
  private File profileImage;

  @Enumerated(EnumType.STRING)
  private ElevateStatus elevateStatus;

  @Enumerated(EnumType.STRING)
  @ColumnDefault(value = "'CREATED'")
  @Column(nullable = false)
  private MemberStatus status = MemberStatus.CREATED; // 생성 상태

  @Enumerated(EnumType.STRING)
  @ColumnDefault(value = "'COAME'")
  @Column(nullable = false)
  private Privilege privilege = Privilege.COAME;  // 생성 시 코미

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "portfolio_id")
  private Portfolio portfolio;

  // 코치가 개설한 강의목록
  @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL)
  private List<Coaching> coachTeachCourses = new ArrayList<>();

  // 코미가 수강하는 목록
  @OneToMany(mappedBy = "coame", cascade = CascadeType.ALL)
  private List<CoameCoaching> coameTaughtCourses = new ArrayList<>();

  // 코미가 누른 좋아요
  @OneToMany(mappedBy = "coame", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Likes> sendLikes = new ArrayList<>();

  // 코미가 남긴 리뷰
  @OneToMany(mappedBy = "coame")
  private List<Review> sendReviews = new ArrayList<>();

  // 코치가 받은 리뷰
  @OneToMany(mappedBy = "coach")
  private List<Review> receivedReviews = new ArrayList<>();

  // 코치가 참여하고 있는  DM Room
  @OneToMany(mappedBy = "member1")
  private List<DMRoom> member1DmRooms = new ArrayList<>();

  // 코미가 참여하고 있는  DM Room
  @OneToMany(mappedBy = "member2")
  private List<DMRoom> member2DmRooms = new ArrayList<>();

  // method
  // 회원정보 생성 시 권한을 설정하고 상태를 생성으로 바꾼다.
  public void initMemberStatus() {
    this.status = MemberStatus.CREATED;
    this.privilege = Privilege.COAME;
    this.profileText = "프로필을 등록하세요.";
    updateProfileImageToDefault();
  }

  // 회원정보 수정 시 이름과 이메일을 변경하고 상태를 변경으로 바꾼다.
  public void changeMemberStatus(String nickName, String email) {
    this.status = MemberStatus.MODIFIED;
    this.nickName = nickName;
    this.email = email;
  }

  // 코치 등록 시 권한상승 리스트에 추가한다.
  public void elevateRequest(String newHtmlDocs) {
    this.elevateStatus = ElevateStatus.REQUEST;
    this.portfolio = new Portfolio(newHtmlDocs);
  }

  // 권한 상승 요청시, 권한을 1에서 2로 수정한다.
  public void elevatePermissionRequest() {
    this.privilege = Privilege.COACH;
    this.elevateStatus = ElevateStatus.SUCCEED;
    this.status = MemberStatus.MODIFIED;
  }

  // 프로필 글 등록 요청시, 프로필 글 등록
  public void updateProfileText(String profileText) {
    this.profileText = profileText;
  }

  // 프로필 글 등록 요청시, 프로필 글 등록
  public void addProfileImage(String fileName, String url) {
    this.profileImage = new File(this, fileName, url);
  }

  // 포트폴리오 수정 요청시 포트폴리오 수정
  public void updatePortfolio(String htmlDocs) {
    this.portfolio.uploadHtmlDocs(htmlDocs);
  }

  // 프로필 사진 기본으로 설정
  public void updateProfileImageToDefault() {
    this.profileImage = new File(this, "default", "/assets/icons/coame.png");
  }

  // 연관관계 편의 메서드
  public void addCoameTaughtCourse(CoameCoaching coameCoaching) {
    this.coameTaughtCourses.add(coameCoaching);
  }

  public void addCoachReview(Review review) {
    this.receivedReviews.add(review);
  }

  // 코치 찜콩
  public void likeCoach(Likes like) {
    this.sendLikes.add(like);
  }

  //코칭 찜콩
  public void likeCoaching(Likes like) {
    this.sendLikes.add(like);
  }

  public boolean isCoachInLikes(Member coach) {
    for (Likes like : this.sendLikes) {
      if (like.getCoach() == coach) {
        return true;
      }
    }
    return false;
  }

  public boolean isCoachingInLikes(Coaching coaching) {
    for (Likes like : this.sendLikes) {
      if (like.getCoaching() == coaching) {
        return true;
      }
    }
    return false;
  }

}
