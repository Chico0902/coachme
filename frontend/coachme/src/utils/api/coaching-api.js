import { authAxios } from '@/utils/http-commons'

/**
 * API번호 : coaching-2
 * METHOD : POST
 * URI : /coaches/coachings/{longId(member)}
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
export function postNewCoaching(token, longId, dto, success, fail) {
  const axios = authAxios(token)
  axios.post(`/coaches/coachings/${longId}`, dto).then(success).catch(fail)
}
