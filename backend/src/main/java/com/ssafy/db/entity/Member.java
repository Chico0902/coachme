package com.ssafy.db.entity;

import com.ssafy.api.member.dto.UpdateMemberDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "member_id")
  private String memberId;

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
  @Column(name = "create_date", nullable = false, updatable = false)
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
    return memberId;
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