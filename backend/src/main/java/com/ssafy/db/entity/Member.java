package com.ssafy.db.entity;

import com.ssafy.api.member.request.MemberRegistDto;
import com.ssafy.api.member.request.UpdateMemberDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Table(name = "members")
@Builder
@Entity
@Getter
public class Member implements UserDetails {

  protected Member() {
  }

  public Member(Long memberId, String id, String password, String privilege, String name, String nickname, String phone, String email, String description, LocalDateTime modifyDate, LocalDateTime createDate, int elevation, Portfolio portfolio) {
    this.memberId = memberId;
    this.id = id;
    this.password = password;
    this.privilege = privilege;
    this.name = name;
    this.nickname = nickname;
    this.phone = phone;
    this.email = email;
    this.description = description;
    this.modifyDate = modifyDate;
    this.createDate = createDate;
    this.elevation = elevation;
    this.portfolio = portfolio;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long memberId;

  @Column
  private String id;

  @Column
  private String password;

  @Column(columnDefinition = "VARCHAR(100) DEFAULT 'COAME'")
  private String privilege;

  @Column
  private String name;

  @Column
  private String nickname;

  @Column
  private String phone;

  @Column
  private String email;

  @Column
  private String description;

  @LastModifiedDate
  @Column(name = "modify_date", nullable = true)
  private LocalDateTime modifyDate;

  @CreatedDate
  @Column(name = "create_date")
  private LocalDateTime createDate;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int elevation;

  @ManyToOne
  @JoinColumn(name = "portfolio_id")
  private Portfolio portfolio;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getUsername() {
    return id;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void changeMemberInfo(UpdateMemberDto updateMemberDto) {
    this.name = updateMemberDto.getName();
    this.email = updateMemberDto.getEmail();
    this.nickname = updateMemberDto.getNick();
  }

}
