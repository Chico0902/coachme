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
   * API번호 : member-3
   * 비밀번호 변경 요청을 위한 DTO 클래스
   * @param {String} id
   * @param {String} email
   */
  constructor(id, email) {
    this.id = id
    this.email = email
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
   * @param {FormData} profileImage 프로필 사진
   */
  constructor(profileImage) {
    this.profileImage = profileImage
  }
}

export class MemberDuplicateRequestDto {
  /**
   * API번호 : member-14
   * 사용자 ID검증을 위한 DTO 클래스
   * @param {String} id 사용자 ID
   */
  constructor(id) {
    this.id = id
  }
}
