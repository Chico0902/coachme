export class MemberRegistRequestDto {
  /**
   * API번호 : member-2
   * 회원가입 요청을 위한 DTO 클래스
   * @param {*} stringId
   * @param {*} pw
   * @param {*} name
   * @param {*} nick
   * @param {*} email
   */
  constructor(stringId, pw, name, nick, email) {
    this.stringId = stringId
    this.pw = pw
    this.name = name
    this.nick = nick
    this.email = email
  }
}

export class changePasswordRequestDto {
  /**
   * API번호 : member-3
   * 비밀번호 변경 요청을 위한 DTO 클래스
   * @param {String} stringId
   * @param {String} email
   */
  constructor(stringId, email) {
    this.stringId = stringId
    this.email = email
  }
}

export class ElevationRequestDto {
  /**
   * API번호 : member-4
   * 권한상승 요청을 위한 DTO 클래스
   * @param {String} longId
   * @param {String} htmlDocs
   */
  constructor(longId, htmlDocs) {
    this.longId = longId
    this.htmlDocs = htmlDocs
  }
}

export class MemberInfoChangeRequestDto {
  /**
   * API번호 : member-6
   * 회원정보 수정 시 입력한 비밀번호를 검증한 후, 회원정보 변경
   * @param {String} pw
   * @param {String} nick
   * @param {String} email
   */
  constructor(pw, nick, email) {
    this.pw = pw
    this.nick = nick
    this.email = email
  }
}

export class ProfileTextRequestDto {
  /**
   * API번호 : member-8, 10
   * 프로필 글 등록 및 수정 요청을 위한 DTO 클래스
   * @param {String} profileText 프로필 글
   */
  constructor(profileText) {
    this.profileText = profileText
  }
}

export class ProfileImageRequestDto {
  /**
   * API번호 : member-9, 11
   * 프로필 사진 등록 및 수정 요청을 위한 DTO 클래스
   */
  constructor(newImage) {
    this.data = new FormData()
    this.data.append('newImage', newImage)
  }
}

export class MemberDuplicateRequestDto {
  /**
   * API번호 : member-14
   * 사용자 ID검증을 위한 DTO 클래스
   * @param {String} stringId 사용자 ID
   */
  constructor(stringId) {
    this.stringId = stringId
  }
}
