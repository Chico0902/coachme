package com.ssafy.config.security;

import com.ssafy.db.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

  private final Member member;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    switch (member.getPrivilege()) {
      case COAME -> authorities.add(new SimpleGrantedAuthority("ROLE_COAME"));
      case COACH -> {
        authorities.add(new SimpleGrantedAuthority("ROLE_COAME"));
        authorities.add(new SimpleGrantedAuthority("ROLE_COACH"));
      }
      case ADMIN -> {
        authorities.add(new SimpleGrantedAuthority("ROLE_COAME"));
        authorities.add(new SimpleGrantedAuthority("ROLE_COACH"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
      }
    }
    return authorities;
  }

  @Override
  public String getPassword() {
    return this.member.getPassword();
  }

  @Override
  public String getUsername() {
    return this.member.getStringId();
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }
}
