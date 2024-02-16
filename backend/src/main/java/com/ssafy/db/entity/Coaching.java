package com.ssafy.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coaching extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "coaching_id")
  private Long id;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "coach_id")
  private Member coach;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "main_category_id", nullable = false)
  private Category mainCategory;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "sub_category_id", nullable = false)
  private Category subCategory;

  @Column(nullable = false, length = 50)
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "coaching")
  private List<LiveCoaching> liveCoachings = new ArrayList<>();

  @OneToMany(mappedBy = "coaching")
  private List<File> videoCoachings = new ArrayList<>();

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "represent_id")
  private File represent;

  @Column
  private Integer price;

  // RAW HTML로 들어온 해당 코칭에 대한 설명 페이지
  @Column(nullable = false, length = 5000)
  private String htmlDocs;

  @Column(nullable = false, length = 100)
  private String summary;

  @OneToMany(mappedBy = "coaching")
  private List<Likes> receivedLikes = new ArrayList<>();

  @OneToMany(mappedBy = "coaching")
  private List<Review> receivedReviews = new ArrayList<>();

  // method
  // 멤버 등록하기
  public void registCoaching(Member member) {
    //찾은 멤버와 coaching에서의 coach와 연결시켜주기.
    member.getCoachTeachCourses().add(this);
    this.coach = member;
  }

  public void addOneCategory(Category main) {
    this.mainCategory = main;
  }

  public void addBothCategories(Category main, Category sub) {
    this.mainCategory = main;
    this.subCategory = sub;
  }

  public void updatedCoaching(Category main, Category sub, String name, String summary, String htmlDocs) {
    this.mainCategory = main;
    this.subCategory = sub;
    this.name = name;
    this.summary = summary;
    this.htmlDocs = htmlDocs;
  }

  public void categorize(Category main, Category sub) {
    this.mainCategory = main;
    this.subCategory = sub;
    this.subCategory.addCategoryList(this);
  }

  public void addCoachingReview(Review review) {
    this.receivedReviews.add(review);
  }

  public void registRepresent(File file) {
    this.represent = file;
  }

  public void addLiveCoaching(LiveCoaching liveCoaching) {
    this.liveCoachings.add(liveCoaching);
  }

  public void addVideoCoaching(File file){
    this.videoCoachings.add(file);
  }
}