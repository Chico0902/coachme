import router from '@/router'
import axios from 'axios'
import Swal from 'sweetalert2'

const { VITE_BACKEND_URL } = import.meta.env

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
  axios
    .post(`${VITE_BACKEND_URL}/auth/login`, dto, {
      headers: {
        'Content-Type': 'application/json;charset=utf-8'
      },
      withCredentials: true
    })
    .then(success)
    .catch(fail)
}

/**
 * API번호 : auth-2
 * METHOD : GET
 * URI : /auth/refresh
 * 권한 : 0
 * 설명 : Access Token 만료 시 Refresh Token을 반환한다. 
 * @param {Promise} fail 오류 처리를 위한 함수
 * 설명 : 잘못된 토큰
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function getRefresh() {
  return new Promise((resolve) =>
    axios
      .get(`${VITE_BACKEND_URL}/auth/refresh`, {
        headers: {
          'Content-Type': 'application/json;charset=utf-8'
        },
        withCredentials: true
      })
      .then((success) => {
        const accessToken = { accessToken: success.headers.authorization }
        sessionStorage.setItem('auth', JSON.stringify(accessToken))
        resolve()
      })
      .catch((fail) => {
        console.error(fail)
        if (fail.response.data.message === 'Refresh Token Expired') {
          Swal.fire({
            icon: 'info',
            title: '로그인 정보가 없습니다.',
            text: '로그인 페이지로 이동합니다.'
          }).then(() => {
            router.push('/login')
          })
        }
      })
  )
}
