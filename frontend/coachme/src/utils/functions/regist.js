/**
 * id, pw, name, nick, email 입력받아서 유효성 검증하는 함수
 * @param {*} id
 * @param {*} pw
 * @param {*} name
 * @param {*} nick
 * @param {*} email
 * @returns
 */
export function validateRegist(id, pw, name, nick, email) {
  // Exception : not empty
  if (id === '' || pw === '' || name === '' || nick === '' || email === '')
    throw 'Member information must not empty.'

  // Exception : not English or Number
  const re = /^[a-zA-Z0-9]*$/
  if (!re.test(id)) return false
  return true
}
