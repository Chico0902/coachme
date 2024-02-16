package com.ssafy.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.config.security.token.AccessDeniedHandlerImpl;
import com.ssafy.config.security.token.AuthenticationEntryPointImpl;
import com.ssafy.config.security.token.JwtAuthenticationFilter;
import com.ssafy.config.security.token.JwtTokenProvider;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final ObjectMapper objectMapper;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationEntryPointImpl authenticationEntryPoint;
  private final AccessDeniedHandlerImpl accessDeniedHandler;
  private final EntityManager entityManager;

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    return http
        // CSRF 기능 비활성화(JWT인증)
        .csrf(CsrfConfigurer::disable)

        // CORS 설정
        .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))

        // JWT 인증을 위해 세션 비활성화 (STATELESS)
        .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Security의 Form Login 기능을 비홠성화
        .formLogin(AbstractHttpConfigurer::disable)

        // HTTP 기본 인증 기능 비활성화
        .httpBasic(AbstractHttpConfigurer::disable)

        // 인증절차 전에 Jwt 토큰으로부터 권한 부여(JwtFilter -> JwtTokenProvider -> UserDetailServiceImpl)
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, entityManager),
            UsernamePasswordAuthenticationFilter.class)

        // 요청별 권한 인가
        .authorizeHttpRequests((authorize) -> authorize

            // 전체 허용할 요청
            .requestMatchers("/**").permitAll()
            .requestMatchers(
                "/members",
                "/members/duplicate/id",
                "/auth/login")
            .permitAll()

            // ADMIN 접근 가능
            .requestMatchers("/admin/**").hasRole("ADMIN")

            // COACH 접근 가능
            .requestMatchers("/coach/**").hasRole("COACH")

            // COAME 접근 가능
            .requestMatchers(HttpMethod.PATCH, "/members/{longId}").hasRole("COAME")

            // 이외 요청 허용안함
            .anyRequest().denyAll())

        // 인증, 인가 오류처리를 위한 Handler Mapping
        .exceptionHandling((exceptionHandling) ->
            exceptionHandling
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler))

        .build();
  }

  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowCredentials(true);
    configuration.setAllowedOrigins(Arrays.asList("https://i10a403.p.ssafy.io", "http://localhost:5173", "http://localhost:8080"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setExposedHeaders(Arrays.asList("Authorization"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}