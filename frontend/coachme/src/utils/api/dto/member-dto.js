export class LoginRequestDto {
  /**
   * API번호 : member-1
   * 로그인 요청을 위한 DTO 클래스
   * @param {*} id
   * @param {*} pw
   */
  constructor(id, pw) {
    this.id = id
    this.pw = pw
  }
}

export class MemberRegistRequestDto {
  /**
   * API번호 : member-2
   * 회원가입 요청을 위한 DTO 클래스
   * @param {*} id
   * @param {*} pw
   * @param {*} name
   * @param {*} nick
   * @param {*} email
   */
  constructor(id, pw, name, nick, email) {
    this.id = id
    this.pw = pw
    this.name = name
    this.nick = nick
    this.email = email
  }
}

export class changePasswordRequestDto {
  /**
   * API번호 : member-2
   * 비밀번호 변경 요청을 위한 DTO 클래스
   * @param {*} id
   * @param {*} email
   */
  constructor(id, email) {
    this.id = id
    this.email = email
  }
}
