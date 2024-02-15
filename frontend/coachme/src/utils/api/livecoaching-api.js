import { authAxios } from '@/utils/http-commons'

/**
 * API번호 : live-1
 * METHOD : POST
 * URI : /live/sessions/{roomId}/connections
 * 권한 : 1
 * 설명 : 라이브 코칭 방 입장(openVidu)
 * @param {number} roomId
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
	token : String
}
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function postLiveCoachingEntrnce(roomId, longId) {
  return new Promise((resolve) =>
    authAxios
      .post(`live/sessions/${roomId}/connections`, { memberId: longId })
      .then((success) => {
        resolve(success.data)
      })
      .catch((fail) => {
        console.error(fail)
      })
  )
}

/**
 * API번호 : live-2
 * METHOD : POST
 * URI : /live/sessions
 * 권한 : 1
 * 설명 : 라이브 코칭 openvidu 연결(openVidu)
 * @param {object} dto
 * {
    customSessionId: String
  }
  * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
	sessionId : String
}
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function postConnectLiveCoaching(dto) {
  return new Promise((resolve) =>
    authAxios
      .post(`live/sessions`, dto)
      .then((success) => {
        resolve(success.data)
      })
      .catch((fail) => {
        console.error(fail)
      })
  )
}

/**
 * API번호 : live-3
 * METHOD : GET
 * URI : /live/recording/start
 * 권한 : 2
 * 설명 : 레코딩 시작
  * @param {Promise} success
 * 설명 : 정상 시작
 * 코드 : 200
 * body : {
            recoringId : recordingId
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getRecordStart(sessionId, success, fail) {
  authAxios.get(`/live/recording/start/${sessionId}`).then(success).catch(fail)
}

/**
 * API번호 : live-4
 * METHOD : GET
 * URI : /live/recording/stop
 * 권한 : 2
 * 설명 : 레코딩 종료
 * @param {Promise} success
 * 설명 : 정상 시작
 * 코드 : 200
 * body : {
            messages : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getRecordStop(recordingId, success, fail) {
  authAxios.get(`/live/recording/stop/${recordingId}`).then(success).catch(fail)
}

/**
 * API번호 : live-5
 * METHOD : POST
 * URI : /live/recording/stop
 * 권한 : 2
 * 설명 : 레코딩 종료
 * @param {Promise} success
 * 설명 : 정상 시작
 * 코드 : 200
 * body : {
            messages : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getRecordFinish(sessionId, success, fail) {
  authAxios.get(`/live/recording/finish/${sessionId}`).then(success).catch(fail)
}

/**
 * API번호 : live-6
 * METHOD : GET
 * URI : /live/room/open/{liveCoachingId}
 * 권한 : 2
 * 설명 : 라이브 코칭 시작
 * @param {Promise} success
 * 설명 : 정상 시작
 * 코드 : 200
 * body : {
            messages : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getStartLiveCoaching(liveCoachingId, success, fail) {
  authAxios.get(`/live/room/open/${liveCoachingId}`).then(success).catch(fail)
}
