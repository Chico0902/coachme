import { authAxios } from '@/utils/http-commons'

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
export function getMyPortfolio(token, longId, success, fail) {
  const axios = authAxios(token)
  axios.get(`/coaches/portfolio/${longId}`).then(success).catch(fail)
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
export function patchMyPortfolio(token, longId, dto, success, fail) {
  const axios = authAxios(token)
  axios.patch(`/coaches/portfolio/${longId}`, dto).then(success).catch(fail)
}
