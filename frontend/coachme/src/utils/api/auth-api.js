import { backendAxios } from '@/utils/http-commons'

const axios = backendAxios()

/**
 * API번호 : auth-1
 * METHOD : POST
 * URI : /auth/login
 * 권한 : 0
 * 설명 : 로그인 요청 시 해당 id와 pw가 유효한지 여부를 응답한다.
 * @param {LoginRequestDto} dto 로그인 요청 멤버 정보
 * @param {Promise} success
 * 설명 : 해당 로그인 요청 유효함
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 로그인 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function postLoginRequest(dto, success, fail) {
  axios.post(`/auth/login`, dto).then(success).catch(fail)
}
