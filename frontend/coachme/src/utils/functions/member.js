/**
 * id를 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} id 계정 이름
 * @returns
 */
export function validateId(id) {
  // Exception : not empty
  if (id === '') {
    throw new Error('not empty')
  }

  // Exception : invalid input
  const re = /^[a-zA-Z0-9]*$/
  if (!re.test(id)) {
    throw new Error('invalid input')
  }
}

/**
 * pw를 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} pw 계정 비밀번호
 * @returns
 */
export function validatePassword(pw) {
  // Exception : password not enough length
  if (pw.length < '9') {
    throw new Error('password not enough length')
  }

  // Exception : not include upper characters
  const upper = new RegExp(/^[A-Z]*$/)
  if (!upper.test(pw)) {
    throw new Error('not include upper characters')
  }

  // Exception : not include special characters
  const special = new RegExp(/^[!@#$%^&*()_+{}[\]:;<>,.?~\\/-]*$/)
  if (!special.test(pw)) {
    throw new Error('not include upper characters')
  }
}

/**
 * 이름을 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} name 계정 이름
 * @returns
 */
export function validateName(name) {
  // Exception : not empty
  if (name === '') {
    throw new Error('not empty')
  }

  // Exception : invalid input
  const re = /^[ㄱ-ㅎㅏ-ㅣ가-힣]*$/
  if (!re.test(name)) {
    throw new Error('invalid input')
  }
}

/**
 * 닉네임을 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} name 계정 이름
 * @returns
 */
export function validateNickName(nick) {
  // Exception : not empty
  if (nick === '') {
    throw new Error('not empty')
  }

  // Exception : invalid input
  const re = /^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣]*$/
  if (!re.test(nick)) {
    throw new Error('invalid input')
  }
}
/**
 * 이메일을 입력받아서 해당 요청이 유효한지 확인하는 함수
 * @param {String} name 계정 이름
 * @returns
 */
export function validateEmail(email) {
  // Exception : not empty
  if (email === '') {
    throw new Error('not empty')
  }
}
/**
 * 아이디와 비밀번호를 입력받아서 해당 로그인 요청이 유효한지 확인하는 함수
 * @param {String} id 계정 id
 * @param {String} pw 계정 pw
 * @returns
 */
export function validateLogin(id, pw) {
  try {
    validateId(id)
    validatePassword(pw)
  } catch (err) {
    throw '잘못된 로그인 요청입니다.'
  }
}
/**
 * 계정정보를 입력받아서 해당 회원가입 요청이 유효한지 확인하는 함수
 * @param {String} id 계정 id
 * @param {String} pw 계정 pw
 * @returns
 */
export async function validateRegist(id, pw, name, nick, email) {
  try {
    validateId(id)
    validatePassword(pw)
    validateName(name)
    validateNickName(nick)
    validateEmail(email)
    return true
  } catch (e) {
    switch (e.message) {
      case 'not empty':
        throw new Error('기입하지 않은 항목이 있습니다.')
      case 'invalid input':
        throw new Error('유효하지 않은 입력값이 있습니다.')
      case 'password not enough length':
        throw new Error('비밀번호의 길이가 충분하지 않습니다.')
      case 'not include upper characters':
        throw new Error('비밀번호는 대문자를 포함해야 합니다.')
      case 'not include special characters':
        throw new Error('비밀번호는 특수문자를 포함해야 합니다.')
    }
  }
}
