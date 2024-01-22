import { getAccessToken, checkPrivilegeOverDemand } from '../../utils/functions/auth'
/**
 * 현재 JWT의 권한 정보를 확인해서 권한이 1보다 작으면 입장을 막는 함수
 * @param {route} to 대상 Route 객체로 이동합니다.
 * @param {route} from 이동 전 라우트입니다.
 * @param {function} next 훅을 해결하기 위한 호출입니다.(참고 : https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html)
 */
export function guardPrivilegeOne(to, from, next) {
  try {
    const token = getAccessToken()
    const DEMAND = 1
    checkPrivilegeOverDemand(token, DEMAND)
    return next()
  } catch (e) {
    alert(e.message + ' 로그인 페이지로 이동합니다.')
    return next({ name: 'test-login' })
  }
}
