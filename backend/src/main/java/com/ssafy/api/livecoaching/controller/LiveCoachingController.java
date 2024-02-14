package com.ssafy.api.livecoaching.controller;

import com.ssafy.api.livecoaching.dto.request.StartRecordingRequestDto;
import com.ssafy.api.livecoaching.dto.response.LiveCoachingMemberResponseDto;
import com.ssafy.api.livecoaching.service.LiveCoachingService;
import com.ssafy.dto.MessageDto;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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
  private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();
  // 세션 이름과 녹화 객체를 연결하는 컬렉션
  private Map<String, Boolean> sessionRecordings = new ConcurrentHashMap<>();


  @PostConstruct
  public void init() {
    this.openvidu = new OpenVidu(OPENVIDU_URL, SECRET);
  }

  /**
   * @param params The Session properties
   * @return The Session ID
   */
  @PostMapping("/sessions")
  public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
      throws OpenViduJavaClientException, OpenViduHttpException {
    SessionProperties properties = SessionProperties.fromJson(params).build();
    Session session = openvidu.createSession(properties);
    return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
  }

  /**
   * @param sessionId The Session in which to create the Connection
   * @param params    The Connection properties
   * @return The Token associated to the Connection
   */
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

    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  // 녹화를 시작합니다.
  @GetMapping("/recording/start/{sessionId}")
  public ResponseEntity<?> startRecording(@PathVariable("sessionId") String sessionId) {
    RecordingProperties properties =
        new RecordingProperties.Builder()
            .outputMode(Recording.OutputMode.COMPOSED)
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

//      return new ResponseEntity<>(new MessageDto("Recording successful.") , HttpStatus.OK);
      return new ResponseEntity<>(recording, HttpStatus.OK);
    } catch (OpenViduJavaClientException | OpenViduHttpException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  // 녹화 목록을 가져옵니다.
  @GetMapping("/recording/list/{sessionId}")
  public ResponseEntity<?> listRecordings(@PathVariable("sessionId") String sessionId) {
    try {
      List<Recording> recordings = openvidu.listRecordings();
      log.debug("Recordings {}", recordings);
      return new ResponseEntity<>(recordings, HttpStatus.OK);
    } catch (OpenViduJavaClientException | OpenViduHttpException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/room/{liveCoachingId}")
  public ResponseEntity<?> deleteLiveRoom(@PathVariable("liveCoachingId") long liveCoachingId) {
    liveCoachingService.deleteLiveRoom(liveCoachingId);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

}
