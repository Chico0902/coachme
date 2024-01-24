package com.ssafy.config;

import com.ssafy.api.auth.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenProvider jwtTokenProvider;
  private final StringRedisTemplate stringRedisTemplate;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        // CSRF 기능 비활성화
        .csrf(CsrfConfigurer::disable)
        // JWT 인증을 사용하기 위해 세션을 비활성화 (STATELESS)
        .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // Security에서 제공되는 Form Login 기능을 비홠성화
        .formLogin(AbstractHttpConfigurer::disable)
        // HTTP 기본 인증 기능 비활성화
        .httpBasic(AbstractHttpConfigurer::disable)
        // 모든 API 요청에 대한 권한 검사를 비활성화 (모두 허용)
        .authorizeHttpRequests((authorizeRequests) -> {
          authorizeRequests.anyRequest().permitAll();
        })
        // UsernamePasswordAuthenticationRilter 전에 Jwt인증 필터를 적용함
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, stringRedisTemplate), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}