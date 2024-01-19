import { decodeToken } from '../../utils/functions/auth'
/**
 * 현재 JWT의 권한 정보를 확인해서 권한이 1보다 작으면 입장을 막는 함수
 * @param {route} to 대상 Route 객체로 이동합니다.
 * @param {route} from 이동 전 라우트입니다.
 * @param {function} next 훅을 해결하기 위한 호출입니다.(참고 : https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html)
 */
export function guardPrivilegeOne(to, from, next) {
  const token = JSON.parse(sessionStorage.getItem('auth'))

  if (token.accessToken === null) {
    alert('회원정보가 없습니다. 로그인 페이지로 이동합니다.')
    return next({ name: 'test-login' })
  }
  const decodedToken = decodeToken(token.accessToken)
  const privilege = decodedToken.privilege
  if (privilege < 1) {
    alert('권한이 유효하지 않습니다. 로그인 페이지로 이동합니다.')
    return next({ name: 'test-login' })
  }
  return next()
}
