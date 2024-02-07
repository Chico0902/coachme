import { publicAxios, authAxios } from '@/utils/http-commons'

/**
 * API번호 : coach-1
 * METHOD : POST
 * URI : /coaches/categories/{division1}/{division2}
 * 권한 : 0
 * 설명 : 해당 분류 코치들의 정보를 받아온다.
 * @param {String} division1 메인 카테고리
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
  publicAxios.get(`/coaches/categories/${division1}/${division2}`).then(success).catch(fail)
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
 * @param {String} dto 포트폴리오 수정 dto
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
 * API번호 : coaching-5
 * METHOD : POST
 * URI : /coaches/{longId(member)}/coachings
 * 권한 : 2
 * 설명 : 코치가 코칭을 개설한다.
 * @param {String} token accessToken(pinia 사용시 accessToken.value)
 * @param {Number} longId 사용자(코치) pk
 * @param {CreateCoachingRequestDto} dto 코칭 개설 dto
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
 * API번호 : coaching-6
 * METHOD : GET
 * URI : /coaches/{longId(member)}/coachings
 * 권한 : 2
 * 설명 : 코치가 자신의 코칭 정보를 조회한다.
 * @param {String} token accessToken(pinia 사용시 accessToken.value)
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
