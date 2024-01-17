/**
 * id, pw를 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} id 계정 이름
 * @param {String} pw 비밀번호
 * @returns
 */
export function validateLogin(id, pw) {
  // Exception : not empty
  if (id === '' || pw === '') return false

  // Exception : not English or Number
  const re = /^[a-zA-Z0-9]*$/
  if (!re.test(id)) return false
  return true
}
