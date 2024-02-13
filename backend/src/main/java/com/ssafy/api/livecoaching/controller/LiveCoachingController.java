package com.ssafy.api.livecoaching.controller;

import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;

@CrossOrigin(origins = "*")
@RequestMapping("/live")
@RestController
@RequiredArgsConstructor
@Slf4j
public class LiveCoachingController {

  @Value("${openvidu.url}")
  private String openviduUrl;

  @Value("${openvidu.secret}")
  private String openviduSecret;

  private OpenVidu openvidu;

  @PostConstruct
  public void init() {
    this.openvidu = new OpenVidu(openviduUrl, openviduSecret);
  }

  /**
   * @param params The Session properties
   * @return The Session ID
   */
  @PostMapping("/sessions")
  public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
      throws OpenViduJavaClientException, OpenViduHttpException {
    SessionProperties properties = SessionProperties.fromJson(params).build();
    log.debug("properties {}", properties);

    Session session = openvidu.createSession(properties);
    log.debug("session {}", session);

    return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
  }

  /**
   * @param sessionId The Session in which to create the Connection
   * @param params    The Connection properties
   * @return The Token associated to the Connection
   */
  @PostMapping("/sessions/{sessionId}/connections")
  public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                 @RequestBody(required = false) Map<String, Object> params)
      throws OpenViduJavaClientException, OpenViduHttpException {
    Session session = openvidu.getActiveSession(sessionId);
    log.debug("session : {}", session);
    if (session == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
    Connection connection = session.createConnection(properties);

    System.out.println(connection.getToken());

    return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
  }

}
