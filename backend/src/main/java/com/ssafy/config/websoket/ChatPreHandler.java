package com.ssafy.config.websoket;

import com.ssafy.config.security.token.JwtTokenProvider;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Slf4j
public class ChatPreHandler implements ChannelInterceptor {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {

    StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    log.debug("headerAccessor : {}", headerAccessor);

    String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));
    log.debug("authorizationHeader : {}", authorizationHeader);

    StompCommand command = headerAccessor.getCommand();


    if (command.equals(StompCommand.CONNECT)) {
      if (authorizationHeader == null) {
        throw new MalformedJwtException("jwt");
      }
      String accessToken = authorizationHeader.replaceAll("[\\[\\]]", "");

      boolean isTokenValid = jwtTokenProvider.validateToken(accessToken);
      log.debug("isTokenValid : {}", isTokenValid);

      if (isTokenValid) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        this.setAuthentication(authentication, message, headerAccessor);
      }
    }

    if (command.equals(StompCommand.ERROR)) {
      throw new MessageDeliveryException("error");
    }

    return message;
  }

  private void setAuthentication(Authentication authentication, Message<?> message, StompHeaderAccessor headerAccessor) {
    SecurityContextHolder.getContext().setAuthentication(authentication);
    headerAccessor.setUser(authentication);
  }
}