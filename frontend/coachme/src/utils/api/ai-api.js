import axios from 'axios'

const { VITE_AI_URL } = import.meta.env

/**
 * API번호 : ai-1
 * METHOD : POST
 * URI : /ai/edit
 * 권한 : 2
 * 설명 : AI를 이용하여 동영상을 편집한 뒤 S3에 저장한 url을 받아온다.
 * @param {Object} dto
 * url key(url '/' 맨뒤값)
 * @param {Promise} success
 * 설명 : 모든 회원정보 리스트로 반환
 * 코드 : 200
 * body : {
            url : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function postEditToAI(dto, success, fail) {
  axios
    .post(`${VITE_AI_URL}/edit`, dto, {
      'Access-Control-Allow-Credentials': 'true'
    })
    .then(success)
    .catch(fail)
}
