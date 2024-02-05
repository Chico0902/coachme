package com.ssafy.config.security.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.dto.ExceptionDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authException) throws IOException, ServletException {
    log.error("Not Authenticated Request : {}", authException.getMessage());
    log.error("Error type : {}", authException.getClass());
    log.error("Request Method : {}", request.getMethod());
    log.error("Request Uri : {}", request.getRequestURI());

    String responseBody = objectMapper.writeValueAsString(new ExceptionDto("Not Authenticated Request"));

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(responseBody);
  }
}
