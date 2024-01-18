/**
 * 아이디를 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} id 계정 이름
 * @returns
 */
export function validateId(id) {
  // Exception : not empty
  if (id === '') return false

  // Exception : too short
  if (id.length < '4') return false

  // Exception : invalid input
  const re = new RegExp(/^[a-zA-Z0-9]*$/)
  if (!re.test(id)) return false

  return true
}

/**
 * 비밀번호를 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} pw 계정 비밀번호
 * @returns
 */
export function validatePassword(pw) {
  // Exception : password not enough length
  if (pw.length < '9') return false

  // Exception : not include upper characters
  const upper = new RegExp(/^[A-Z]/)
  if (!upper.test(pw)) return false

  // Exception : not include special characters
  // eslint-disable-next-line no-useless-escape
  const special = new RegExp(/[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/g)
  if (!special.test(pw)) return false

  return true
}

/**
 * 비밀번호와 확인값을 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} pw 계정 비밀번호
 * @param {String} pwConfirm 계정 비밀번호 확인
 * @returns
 */
export function validatePasswordConfirm(pw, pwConfirm) {
  // Exception : password not same
  if (pw != pwConfirm) return false

  return true
}

/**
 * 이름을 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} name 계정 이름
 * @returns
 */
export function validateName(name) {
  // Exception : not empty
  if (name === '') return false

  // Exception : invalid input
  const re = /^[가-힣]*$/
  if (!re.test(name)) return false

  return true
}

/**
 * 닉네임을 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} nick 계정 닉네임
 * @returns
 */
export function validateNickName(nick) {
  // Exception : not empty
  if (nick === '') return false

  // Exception : invalid input
  const re = /^[a-zA-Z0-9가-힣]*$/
  if (!re.test(nick)) return false

  return true
}
/**
 * 이메일을 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} email 계정 이메일
 * @returns
 */
export function validateEmail(email) {
  // Exception : not empty
  if (email === '') return false

  return true
}
/**
 * 계정정보를 입력받아서 해당 회원가입 요청이 유효한지 확인하는 함수
 * @param {String} id 계정 id
 * @param {String} pw 계정 pw
 * @returns
 */
export function validateRegist(id, pw, pwConfirm, name, nick, email) {
  if (
    validateId(id) &&
    validatePassword(pw) &&
    validateName(name) &&
    validateNickName(nick) &&
    validateEmail(email)
  )
    return true
  else throw new Error('잘못된 회원가입 요청입니다.')
}
/**
 * 아이디와 비밀번호를 입력받아서 해당 로그인 요청이 유효한지 확인하는 함수
 * @param {String} id 계정 id
 * @param {String} pw 계정 pw
 * @returns
 */
export function validateLogin(id, pw) {
  if (validateId(id) && validatePassword(pw)) return true
  else throw new Error('잘못된 로그인 요청입니다.')
}
