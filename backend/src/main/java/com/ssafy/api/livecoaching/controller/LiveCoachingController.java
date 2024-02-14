package com.ssafy.api.livecoaching.controller;

import com.ssafy.api.livecoaching.dto.request.StartRecordingRequestDto;
import com.ssafy.api.livecoaching.dto.response.LiveCoachingMemberResponseDto;
import com.ssafy.api.livecoaching.service.LiveCoachingService;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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
    log.debug("properties {}", properties);
    log.debug("openVidu: {}", openvidu);
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
  public ResponseEntity<LiveCoachingMemberResponseDto> createConnection(
      @PathVariable("sessionId") String sessionId,
      @RequestBody(required = false) Map<String, Object> params)
      throws OpenViduJavaClientException, OpenViduHttpException {
    Session session = openvidu.getActiveSession(sessionId);
    log.debug("session : {}", session);
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
  @PostMapping("/recording/start")
  public ResponseEntity<?> startRecording(@RequestBody StartRecordingRequestDto dto) {
    String sessionId = dto.getSessionId();

    RecordingProperties properties =
        new RecordingProperties.Builder()
            .outputMode(Recording.OutputMode.valueOf(dto.getOutputMode()))
            .hasAudio(dto.isHasAudio())
            .hasVideo(dto.isHasVideo())
            .build();

    log.debug("세션 {} 의 녹화 시작 | 속성=[outputMode={}, hasAudio={}, hasVideo={}]"
        , sessionId, Recording.OutputMode.valueOf(dto.getOutputMode()), dto.isHasAudio(), dto.isHasVideo());

    try {
      Recording recording = openvidu.startRecording(sessionId, properties);
      sessionRecordings.put(sessionId, true);
      log.debug("recording {}", recording);
      return new ResponseEntity<>(recording, HttpStatus.OK);
    } catch (OpenViduJavaClientException | OpenViduHttpException e) {
      e.printStackTrace();
//      log.error(e.printStackTrace());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  // 녹화를 중지합니다.
  @PostMapping("/recording/stop")
  public ResponseEntity<?> stopRecording(@RequestBody Map<String, Object> params) {
    String recordingId = (String) params.get("recording");
    log.debug("녹화 중지 | recordingId={}", recordingId);

    try {
      Recording recording = openvidu.stopRecording(recordingId);
      sessionRecordings.remove(recording.getSessionId());
      return new ResponseEntity<>(recording, HttpStatus.OK);
    } catch (OpenViduJavaClientException | OpenViduHttpException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  // 녹화를 삭제합니다.
  @DeleteMapping("/recording/delete")
  public ResponseEntity<?> deleteRecording(@RequestBody Map<String, Object> params) {
    String recordingId = (String) params.get("recording");

    log.debug("녹화 삭제 | recordingId={}", recordingId);

    try {
      openvidu.deleteRecording(recordingId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (OpenViduJavaClientException | OpenViduHttpException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/recording/get/{recordingId}")
  public ResponseEntity<?> getRecording(@PathVariable(value = "recordingId") String recordingId) {
    log.debug("녹화 가져오기 | recordingId= {}", recordingId);
    try {
      Recording recording = openvidu.getRecording("SessionA~5");
      return new ResponseEntity<>(recording, HttpStatus.OK);
    } catch (OpenViduJavaClientException | OpenViduHttpException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

  // 녹화 목록을 가져옵니다.
  @GetMapping("/recording/list")
  public ResponseEntity<?> listRecordings() {
    log.debug("녹화 목록 가져오기");
    try {
      List<Recording> recordings = openvidu.listRecordings();
      return new ResponseEntity<>(recordings, HttpStatus.OK);
    } catch (OpenViduJavaClientException | OpenViduHttpException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }

}
