import router from '@/router'
import Swal from 'sweetalert2'

/**
 * Session Storage에서 토큰 받아오는 함수
 * @returns {String} accessToken : 토큰 정보(String)
 * @throws 토큰 없음 : '로그인 정보가 없습니다.' 잘못된 토큰 : '잘못된 토큰 형식입니다.'
 */

export function getAccessToken() {
  try {
    // 세션스토리지에서 token 가져오기
    const token = sessionStorage.getItem('auth')

    // token이 없을 때 예외 처리
    if (!token) {
      throw new Error('로그인 정보가 없습니다.')
    }

    // token을 JSON 형식으로 파싱
    const parsedToken = JSON.parse(token)

    // 잘못된 token 타입 예외 처리
    if (!parsedToken || typeof parsedToken !== 'object' || !parsedToken.accessToken) {
      throw new Error('로그인 정보가 없습니다.')
    }

    // AccessToken이 문자열이 아닌 경우 예외 처리
    const accessToken = parsedToken.accessToken
    if (typeof accessToken !== 'string') {
      throw new Error('로그인 정보가 없습니다.')
    }

    return accessToken
  } catch (error) {
    // 예외 발생 시 SweetAlert로 에러 메시지 표시
    Swal.fire('에러', error.message, 'error')

    // 예외를 다시 던지거나 기본값을 반환하는 등의 처리를 추가할 수 있습니다.
    // 여기서는 예외 메시지를 그대로 반환합니다.
    throw error
  }
}

/**
 * 토큰을 입력받아서 토큰 결과를 조회하는 함수
 * @param {String} token JWT
 * @returns {Object} decodedToken : 복호화된 토큰 정보(Json Object)
 * @throws 토큰 없음 : '로그인 정보가 없습니다.'
 * @throws 잘못된 토큰 : '잘못된 토큰 형식입니다.'
 */

export function decodeToken(token) {
  try {
    // token이 없을 때 예외 처리
    if (!token) {
      throw new Error('로그인 정보가 없습니다.')
    }

    // token이 문자열이 아닌 경우 예외 처리
    if (typeof token !== 'string') {
      throw new Error('로그인 정보가 없습니다.')
    }

    // JWT는 '.'으로 구분된 세 부분(header, payload, signature)으로 이루어져 있어야 합니다.
    if (token.split('.').length !== 3) {
      throw new Error('로그인 정보가 없습니다.')
    }

    const base64Url = token.split('.')[1]

    // URL-Safe하도록 문자열 대체하는 정규표현식
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')

    // base64로 디코딩 후 JSON 객체로 파싱
    const decodedToken = JSON.parse(
      decodeURIComponent(
        atob(base64)
          .split('')
          .map(function (c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
          })
          .join('')
      )
    )

    return decodedToken
  } catch (error) {
    // 예외 발생 시 SweetAlert로 에러 메시지 표시
    Swal.fire('에러', error.message, 'error')

    // 로그인 페이지로 이동
    router.push('/login')
  }
}

/**
 * 토큰 권한을 확인해서 권한이 요구사항 이상인지 확인하는 함수
 * @param {String} token JWT(복호화 이전 상태! - session storage에 들어있는 상태 그대로 사용)
 * @param {Number} demand 요구 권한
 * @returns true : 권한 만족
 * @throws 잘못된 권한 : '잘못된 권한 형식입니다.'
 * @throws 권한 부족 : '권한이 유효하지 않습니다.'
 */
export function checkPrivilegeOverDemand(token, demand) {
  // 권한 매칭하는 로직
  let privilege
  switch (decodeToken(token).privilege) {
    case 'COAME':
      privilege = 1
      break
    case 'COACH':
      privilege = 2
      break
    case 'ADMIN':
      privilege = 3
      break
    default:
      // Exception : 권한 형식이 잘못되었을 경우
      throw new Error('잘못된 권한 형식입니다.')
  }

  // Exception : privilege less than one
  if (privilege < demand) {
    throw new Error('권한이 유효하지 않습니다.')
  }

  return true
}

/**
 * 모든 토큰을 삭제하고 로그아웃하는 함수
 */
export function logout() {
  sessionStorage.setItem('auth', '')
}
