/**
 * 토큰을 입력받아서 토큰 결과를 조회하는 함수
 * @param {String} token JWT
 * @returns decodedToken : 복호화된 토큰 정보
 */
export function decodeToken(token) {
  // Exception : not empty
  if (token === '') throw new Error('토큰 정보가 없습니다.')

  // Exception : JWT는 '.'으로 구분된 세 부분(header, payload, signature)으로 이루어져 있어야 한다.
  if (token.split('.').length != 3) throw new Error('잘못된 토큰 형식입니다.')
  const base64Url = token.split('.')[1]

  // URL-Safe하도록 문자열 대체하는 정규표현식
  const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')

  // base64로 디코딩 후 JSON객체로 파싱
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
}
