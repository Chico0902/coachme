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
public class DMRoom extends BaseEntity {
  @Id
  @GeneratedValue
  @Column(name = "dmroom_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member2_id")
  private Member member1;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member1_id")
  private Member member2;


  // 해당 DM Room에 있는 DM List
  @OneToMany(mappedBy = "id")
  private List<DM> dmList = new ArrayList<>();

}