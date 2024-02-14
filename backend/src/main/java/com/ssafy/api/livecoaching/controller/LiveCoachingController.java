package com.ssafy.api.livecoaching.controller;

import com.google.gson.JsonObject;
import com.ssafy.api.livecoaching.dto.request.StartRecordingRequestDto;
import com.ssafy.api.livecoaching.dto.response.LiveCoachingMemberResponseDto;
import com.ssafy.api.livecoaching.service.LiveCoachingService;
import com.ssafy.dto.ListDataDto;
import com.ssafy.dto.MessageDto;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin(origins = "*")
@RequestMapping("/live")
@RestController
@RequiredArgsConstructor
@Slf4j
public class LiveCoachingController {

  @Value("${openvidu.url}")
  private String OPENVIDU_URL;

  @Value("${openvidu.secret}")
  private String SECRET;

  private OpenVidu openvidu;

  private final LiveCoachingService liveCoachingService;

  // 세션 이름과 OpenVidu 세션 객체를 연결하는 컬렉션
  private Map<String, Session> mapSessions = new ConcurrentHashMap<>();

  // 세션 이름과 토큰을 연결하는 컬렉션 (내부 맵은 토큰과 연결된 역할을 연결합니다)
  private Map<String, List<String>> mapSessionNamesTokens = new ConcurrentHashMap<>();

  // 세션 이름과 녹화 객체를 연결하는 컬렉션
  private Map<String, Boolean> sessionRecordings = new ConcurrentHashMap<>();


  @PostConstruct
  public void init() {
    this.openvidu = new OpenVidu(OPENVIDU_URL, SECRET);
  }

  /**
   * [live-1]
   * privilege : 1
   *
   * @return [200]
   */
  @PostMapping("/sessions")
  public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
      throws OpenViduJavaClientException, OpenViduHttpException {
    SessionProperties properties = SessionProperties.fromJson(params).build();
    Session session = openvidu.createSession(properties);
    mapSessions.put(session.getSessionId(), session);
    mapSessionNamesTokens.put(session.getSessionId(), new ArrayList<>());
    return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
  }

  @PostMapping("/sessions/{sessionId}/connections")
  public ResponseEntity<LiveCoachingMemberResponseDto> createConnection(
      @PathVariable("sessionId") String sessionId,
      @RequestBody(required = false) Map<String, Object> params)
      throws OpenViduJavaClientException, OpenViduHttpException {

    Session session = openvidu.getActiveSession(sessionId);

    if (session == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
    Connection connection = session.createConnection(properties);

    long memberId = Long.parseLong(params.get("memberId").toString());
    LiveCoachingMemberResponseDto responseDto = liveCoachingService.getMemberInfo(memberId);
    responseDto.setToken(connection.getToken());

    if (mapSessions.get(sessionId) != null) {
      // Session already exists
      log.debug("Existing session {}", sessionId);

      // Generate a new token with the recently created connectionProperties
      String token = connection.getToken();

      // Update our collection storing the new token
      mapSessionNamesTokens.get(sessionId).add(token);
    }
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }


  // 녹화를 시작합니다.
  @GetMapping("/recording/start/{sessionId}")
  public ResponseEntity<?> startRecording(@PathVariable("sessionId") String sessionId) {
    RecordingProperties properties =
        new RecordingProperties.Builder()
            .outputMode(Recording.OutputMode.INDIVIDUAL)
            .hasAudio(true)
            .hasVideo(true)
            .build();

    try {
      Recording recording = openvidu.startRecording(sessionId, properties);
      sessionRecordings.put(sessionId, true);
      Map<String, String> map = new HashMap<>();
      map.put("recordingId", recording.getId());
      return new ResponseEntity<>(map, HttpStatus.OK);
    } catch (OpenViduJavaClientException | OpenViduHttpException e) {
      e.printStackTrace();
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  // 녹화를 중지합니다.
  @GetMapping("/recording/stop/{recordingId}")
  public ResponseEntity<?> stopRecording(@PathVariable("recordingId") String recordingId) {
    try {
      log.debug("recordingId {}", recordingId);
      Recording recording = openvidu.stopRecording(recordingId);
      sessionRecordings.remove(recording.getSessionId());

      return new ResponseEntity<>("Recording stopped successfully.", HttpStatus.OK);
    } catch (OpenViduJavaClientException | OpenViduHttpException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  // 녹화 목록을 가져옵니다.
  @GetMapping("/recording/finish/{sessionId}")
  public ResponseEntity<?> listRecordings(@PathVariable("sessionId") long sessionId) {
    try {
      List<Recording> recordings = openvidu.listRecordings();
      List<String> urlList = new ArrayList<>();
      for (Recording recording : recordings) {
        if (recording.getSessionId().equals(Long.toString(sessionId))) {
          urlList.add(recording.getUrl());
        }
      }
      liveCoachingService.deleteLiveRoom(sessionId);

      String stringSessionId = String.valueOf(sessionId);
      Session s = mapSessions.get(stringSessionId);
      s.close();

      mapSessions.remove(stringSessionId);
      mapSessionNamesTokens.remove(stringSessionId);
      return new ResponseEntity<>(new ListDataDto(urlList), HttpStatus.OK);
    } catch (OpenViduJavaClientException | OpenViduHttpException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/room/open/{liveCoachingId}")
  public ResponseEntity<?> openLiveRoom(@PathVariable("liveCoachingId") long liveCoachingId) {
    liveCoachingService.openLiveRoom(liveCoachingId);
    return new ResponseEntity<>(new MessageDto("Live Coaching State Change Successful"), HttpStatus.OK);
  }
}