export class LoginRequestDto {
  /**
   * API번호 : member-1
   * 로그인 요청을 위한 DTO 클래스
   * @param {*} stringId
   * @param {*} pw
   */
  constructor(stringId, pw) {
    this.stringId = stringId
    this.pw = pw
  }
}
