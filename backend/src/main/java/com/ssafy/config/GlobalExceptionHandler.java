package com.ssafy.config;

import com.ssafy.dto.ExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

  // [400]잘못된 요청일때(validate 시 null이거나, 빈칸 등)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ExceptionDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  // [400]Entity를 PK로 조회시 찾을 수 없을 때
  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<ExceptionDto> handleEntityNotFoundException(EntityNotFoundException e) {
    return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  // [400]로그인 시 해당 아이디 없을때
  @ExceptionHandler(UsernameNotFoundException.class)
  protected ResponseEntity<ExceptionDto> handleUsernameNotFoundException(UsernameNotFoundException e) {
    return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  // [401]로그인 시 해당 비밀번호가 일치하지 않을 때
  @ExceptionHandler(BadCredentialsException.class)
  protected ResponseEntity<ExceptionDto> handleBadCredentialsException(BadCredentialsException e) {
    return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.UNAUTHORIZED);
  }

  // [500]서버 오류
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ExceptionDto> handleInternalServerException(Exception e) {
    return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(IllegalStateException.class)
  protected ResponseEntity<ExceptionDto> handleIllegalStateException(Exception e) {
    return new ResponseEntity<>(new ExceptionDto(e.getMessage()), HttpStatus.BAD_REQUEST);
  }

}
