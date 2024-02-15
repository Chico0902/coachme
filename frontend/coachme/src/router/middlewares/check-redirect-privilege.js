/**
 * 현재 JWT의 권한 정보를 확인해서 redirect된 요청이면 이동시켜주는 함수
 * @param {route} to 대상 Route 객체로 이동합니다.
 * @param {route} from 이동 전 라우트입니다.
 * @param {function} next 훅을 해결하기 위한 호출입니다.(참고 : https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html)
 */
export function checkRedirectPrivilege(to, from, next) {
  // mypage에서 redirect된 요청 승인
  if (to.redirectedFrom != undefined) {
    const redirectedBaseUrl = to.redirectedFrom.path.split('/')[1]
    if (redirectedBaseUrl === 'mypage') return next()
  }

  // 내부에서 이동하는 요청 승인
  const beforeBaseUrl = from.path.split('/')[1]
  if (beforeBaseUrl === 'mypage-coach') return next()
  // 이외 접근 거부
  return next('/')
}
