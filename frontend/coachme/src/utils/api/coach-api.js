import { publicAxios, authAxios, authFileAxios } from '@/utils/http-commons'

/**
 * API번호 : coach-1
 * METHOD : POST
 * URI : /coaches/categories/{division1}/{division2}
 * 권한 : 0
 * 설명 : 해당 분류 코치들의 정보를 받아온다.
 * @param {Object} dto 
 * @param {String} division1 메인 카테고리
 * dto : {
            "words": String,
            "loginMemberId": Long(로그인 안했으면 -1)
          }
 * @param {String} division2 서브 카테고리
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function postCoachesByCategory(division1, division2, dto, success, fail) {
  publicAxios.post(`/coaches/categories/${division1}/${division2}`, dto).then(success).catch(fail)
}

/**
 * API번호 : coach-2
 * METHOD : GET
 * URI : /coaches/coachings/{longId(member)}
 * 권한 : 2
 * 설명 : 코치는 본인의 포트폴리오를 조회한다.
 * @param {String} token accessToken(pinia 사용시 accessToken.value)
 * @param {Number} longId 사용자(코치) pk
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export async function getMyPortfolio(longId, success, fail) {
  authAxios.get(`/coaches/portfolio/${longId}`).then(success).catch(fail)
}

/**
 * API번호 : coach-3
 * METHOD : PATCH
 * URI : /coaches/coachings/{longId(member)}
 * 권한 : 2
 * 설명 : 코치는 본인의 포트폴리오를 수정한다.
 * @param {String} token accessToken(pinia 사용시 accessToken.value)
 * @param {Number} longId 사용자(코치) pk
 * @param {object} dto 포트폴리오 수정 dto
 * @param {Promise} success
 * 설명 : 정상 수정완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function patchMyPortfolio(longId, dto, success, fail) {
  authAxios.patch(`/coaches/portfolio/${longId}`, dto).then(success).catch(fail)
}

/**
 * API번호 : coach-5
 * METHOD : POST
 * URI : /coaches/{longId(member)}/coachings
 * 권한 : 2
 * 설명 : 코치가 코칭을 개설한다.
 * @param {String} token accessToken(pinia 사용시 accessToken.value)
 * @param {Number} longId 사용자(코치) pk
 * @param {object} dto 코칭 개설 dto
 * @param {Promise} success
 * 설명 : 정상 개설완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function postNewCoaching(longId, dto, success, fail) {
  authAxios.post(`/coaches/${longId}/coachings`, dto).then(success).catch(fail)
}

/**
 * API번호 : coach-6
 * METHOD : GET
 * URI : /coaches/{longId(member)}/coachings
 * 권한 : 2
 * 설명 : 코치가 자신의 코칭 정보를 조회한다.
 * @param {Number} longId 사용자(코치) pk
 * @param {Promise} success
 * 설명 : 정상 개설완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getMyCoaching(longId, success, fail) {
  authAxios.get(`/coaches/${longId}/coachings`).then(success).catch(fail)
}

/**
 * API번호 : coach-10
 * METHOD : GET
 * URI : /coaches/{longId(coach_longId)}/calendar
 * 권한 : 2
 * 설명 : 코치가 마이페이지>라이브관리 메뉴에서 자신이 만든 라이브 코칭 일정을 캘린더로 확인할 수 있다.
 * @param {Number} longId 사용자(코치) pk
 * @param {Promise} success
 * 설명 : 정상 개설완료
 * 코드 : 200
 * "list": [
        {
            "id": Long,
            "className": String,
            "date": String,
            "time": String
        }
    ]
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getLiveCoachingCalendar(longId, success, fail) {
  authAxios.get(`/coaches/${longId}/calendar`).then(success).catch(fail)
}

/**
 * API번호 : coach-11
 * METHOD : GET
 * URI : /coaches/{coachId}
 * 권한 : 1
 * 설명 : 코미가 코치 상세 페이지를 조회한다.
 * @param {Number} coachId 사용자(코치) pk
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
    "coachName": String,
    "coachProfileImageUrl": String,
    "htmlDocs": String,
    "list": Object,
    "reviewCount": Long,
    "reviewAvg": float,
    "likeCount": Long
}
  * @param {Promise} fail
  * 설명 : 잘못된 요청[400], 서버 오류[500]
  * 코드 : 400, 500
  * body : {
            message : String
          }
  */
export function getCoachDetailPage(coachId, success, fail) {
  authAxios.get(`/coaches/${coachId}`).then(success).catch(fail)
}

/**
 * API번호 : coach-12
 * METHOD : POST
 * URI : /coaches/{coach_id}/live
 * 권한 : 2
 * 설명 : 코치가 라이브 코칭을 생성한다.
 * @param {Number} coachId 사용자(코치) pk
 * @param {Object} dto
 * 코칭id, 라이브코칭 시간(날짜+시간)
 * @param {Promise} success
 * 설명 : 정상 개설완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function postCreateLiveCoaching(coachId, dto, success, fail) {
  authAxios.post(`/coaches/${coachId}/live`, dto).then(success).catch(fail)
}

/**
 * API번호 : coach-13
 * METHOD : GET
 * URI : /coaches/{coach_id}/videos
 * 권한 : 2
 * 설명 : 코치가 자신의 영상 목록을 조회할 수 있다.
 * @param {Number} coachId 사용자(코치) pk
 * @param {Promise} success
 * 설명 : 정상 개설완료
 * 코드 : 200
 * list: [
				{
						"videoId": Long,
						"videoName": String,
						"url": String,
						"coachingId": Long,
						"coachingName": String
				}
		]
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getVideoList(coachId, success, fail) {
  authAxios.get(`/coaches/${coachId}/videos`).then(success).catch(fail)
}

/**
 * API번호 : coach-14
 * METHOD : GET
 * URI : /coaches/popular
 * 권한 : 0
 * 설명 : 모든 사용자가 메인페이지에서 인기코치를 확인할 수 있다.
 * 
 * @param {Promise} success
 * 설명 : 정상 개설완료
 * 코드 : 200
 * 코치 id, 코치 프로필 url, 코치 이름, 리뷰 평균 점수, 좋아요 표시(디폴트 : 활성화 안됨)
 * list: [
				{
						
				}
		]
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getPopularCoachList(success, fail) {
  publicAxios.get(`/coaches/popular`).then(success).catch(fail)
}

/**
 * API번호 : coach-15
 * METHOD : POST
 * URI : /coaches/{coachId}/videos
 * 권한 : 0
 * 설명 : 영상을 업로드할 수 있다.
* @param {Number} coachId
 * @param {Promise} success
 * 설명 : 정상 업로드 완료
 * 코드 : 200
 * body : {
						message : String
				  }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function postNewVideo(coachId, dto, success, fail) {
  authFileAxios.post(`/coaches/${coachId}/videos`, dto).then(success).catch(fail)
}
