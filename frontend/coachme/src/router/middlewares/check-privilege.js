import { getAccessToken, decodeToken } from '../../utils/functions/auth'
/**
 * 현재 JWT의 권한 정보를 확인해서 권한별로 다른 페이지로 보내주는 함수
 * @param {route} to 대상 Route 객체로 이동합니다.
 * @param {route} from 이동 전 라우트입니다.
 * @param {function} next 훅을 해결하기 위한 호출입니다.(참고 : https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html)
 */
export function checkPrivilege(to, from, next) {
  // infinite recursive 방지를 위해 redirected되는 호출과 mypage 간의 이동은 승인
  if (to.redirectedFrom != undefined) {
    const beforeBaseUrl = to.redirectedFrom.path.split('/')[1]
    if (beforeBaseUrl === 'mypage') {
      return next()
    }
  }
  try {
    const token = getAccessToken()
    // 권한 매칭하는 로직
    switch (decodeToken(token).privilege) {
      case 'COAME':
        return next({ name: 'Desktop-5-1' })
      case 'COACH':
        return next({ name: 'Desktop-5-2' })
      case 'ADMIN':
        return next({ name: 'Desktop-11' })
      default:
        // Exception : 권한 형식이 잘못되었을 경우
        throw new Error('잘못된 권한 형식입니다.')
    }
  } catch (e) {
    alert(e.message + ' 로그인 페이지로 이동합니다.')
    return next('/login')
  }
}
