import { publicAxios, authAxios } from '@/utils/http-commons'

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
export function postLiveCoachingEntrnce(roomId, success, fail) {
  authAxios.post(`/live/sessions/${roomId}/connections`).then(success).catch(fail)
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
export function postConnectLiveCoaching(dto, success, fail) {
  authAxios.post(`/live/sessions`, dto).then(success).catch(fail)
}
