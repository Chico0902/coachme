/**
 * 아이디를 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} id 계정 아이디
 * @returns true : 유효한 아이디 / false : 잘못된 아이디
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
 * @returns true : 유효한 비밀번호 / false : 잘못된 비밀번호
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
 * @returns true : 유효한 비밀번호 확인 / false : 잘못된 비밀번호 확인
 */
export function validatePasswordConfirm(pw, pwConfirm) {
  // Exception : password not same
  if (pw != pwConfirm) return false

  return true
}

/**
 * 이름을 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} name 계정 이름
 * @returns true : 유효한 이름 / false : 잘못된 이름
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
 * @returns true : 유효한 닉네임 / false : 잘못된 닉네임
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
 * @param {String} id 계정 아이디
 * @param {String} pw 계정 비밀번호
 * @param {String} pwConfirm 비밀번호 확인
 * @param {String} name 계정 이름
 * @param {String} nick 계정 닉네임
 * @param {String} email 계정 이메일
 * @returns true : 유효한 회원가입 요청
 * @throws '잘못된 회원가입 요청입니다.'
 */
export function validateRegist(id, pw, pwConfirm, name, nick, email) {
  if (
    validateId(id) &&
    validatePassword(pw) &&
    validatePassword(pwConfirm) &&
    validateName(name) &&
    validateNickName(nick) &&
    validateEmail(email)
  )
    return true
  else throw new Error('잘못된 회원가입 요청입니다.')
}
/**
 * 아이디와 비밀번호를 입력받아서 해당 로그인 요청이 유효한지 확인하는 함수
 * @param {String} id 계정 아이디
 * @param {String} pw 계정 비밀번호
 * @returns true : 유효한 로그인 요청
 * @throws '잘못된 로그인 요청입니다.'
 */
export function validateLogin(id, pw) {
  if (validateId(id) && validatePassword(pw)) return true
  else throw new Error('잘못된 로그인 요청입니다.')
}
/**
 * 아이디와 이메일을 입력받아서 해당 비밀번호 변경 요청이 유효한지 확인하는 함수
 * @param {String} id 계정 아이디
 * @param {String} email 계정 이메일
 * @returns true : 유효한 비밀번호 찾기 요청
 * @throws '잘못된 비밀번호 변경 요청입니다.'
 */
export function validateChangePassword(id, email) {
  if (validateId(id) && validateEmail(email)) return true
  else throw new Error('잘못된 비밀번호 변경 요청입니다.')
}
