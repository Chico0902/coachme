import { getAccessToken, decodeToken, logout } from '../../utils/functions/auth'
/**
 * 현재 JWT의 권한 정보를 확인해서 권한별로 다른 페이지로 보내주는 함수
 * @param {route} to 대상 Route 객체로 이동합니다.
 * @param {route} from 이동 전 라우트입니다.
 * @param {function} next 훅을 해결하기 위한 호출입니다.(참고 : https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html)
 */
export function privilegeRedirect(to, from, next) {
  // 코치가 코미사이트로 접근 허용
  const token = getAccessToken()

  try {
    // 권한별 해당 라우터로 redirect
    switch (decodeToken(token).privilege) {
      case 'COAME':
        return next({ name: 'Desktop-5-1' })
      case 'COACH':
        return next({ name: 'Desktop-5-2' })
      case 'ADMIN':
        return next({ name: 'Desktop-12' })
      default:
        // Exception : 권한 형식이 잘못되었을 경우
        throw new Error('잘못된 권한 형식입니다.')
    }
  } catch (e) {
    alert(e.message + ' 로그인 페이지로 이동합니다!')
    logout()
    return next('/login')
  }
}
