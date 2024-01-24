import { backendAxios, authBackendAxios } from '@/utils/http-commons'

const axios = backendAxios()
const axiosWithToken = authBackendAxios()

/**
 * API번호 : member-1
 * METHOD : POST
 * URI : /members/login
 * 권한 : 0
 * 설명 : 로그인 요청 시 해당 id와 pw가 유효한지 여부를 응답한다.
 * @param {LoginRequestDto} dto 로그인하는 멤버 정보를 LoginRequestDto로 생성해서 입력한다.
 * @param {Promise} success
 * 설명 : 해당 로그인 요청 유효함
 * 코드 : 200
 * body : {
            message : String
            privilege : String
            token : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 로그인 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function postLoginRequest(dto, success, fail) {
  axios.post(`/members/login`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-2
 * METHOD : POST
 * URI : /members
 * 권한 : 0
 * 설명 : 회원가입 요청 시 해당 정보를 DB에 저장한다.
 * @param {MemberRegistRequestDto} dto 회원가입하는 멤버 정보를 MemberRegistRequestDto로 생성해서 입력한다.
 * @param {Promise} success
 * 설명 : 해당 회원가입 요청 유효함
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 회원가입 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function postMember(dto, success, fail) {
  axios.post(`/members`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-3
 * METHOD : PATCH
 * URI : /members/passwords
 * 권한 : 0
 * 설명 : 비밀번호 찾기 요청 시 해당 아이디와 이메일이 유효한지 확인하고, 비밀번호 변경 후 이메일로 임시 비밀번호를 발송한다.
 * @param {changePasswordRequestDto} dto 비밀번호 찾기를 요청한 멤버 정보를 changePasswordRequestDto로 생성해서 입력한다.
 * @param {Promise} success
 * 설명 : 비밀번호 변경 및 임시 비밀번호 발송 완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 비밀번호 찾기 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function patchPassword(dto, success, fail) {
  axios.patch(`/members/passswords`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-6
 * METHOD : PATCH
 * URI : /members/{회원 pk}
 * 권한 : 0
 * 설명 : 회원정보 수정 시 입력한 비밀번호를 검증한 후, 회원정보 변경
 * @param {MemberInfoChangeRequestDto} dto 수정할 회원정보를 포함하는 dto
 * @param {Promise} success
 * 설명 : 기본 회원정보 수정 완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function patchMemberInfo(id, dto, success, fail) {
  axiosWithToken.patch(`/members/${id}`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-7
 * METHOD : GET
 * URI : /members/profiles/{멤버 pk}
 * 권한 : 1
 * 설명 : 코미나 코치의 프로필 글과 사진에 대한 조회결과를 전송한다.
 * @param {Number} id 멤버 id(pk)
 * @param {Promise} success
 * 설명 : 프로필 글 조회결과 전송
 * 코드 : 200
 * body : {
            profileText : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 비밀번호 찾기 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function getProfile(id, success, fail) {
  axiosWithToken.get(`/members/profiles/${id}`).then(success).catch(fail)
}

/**
 * API번호 : member-8
 * METHOD : POST
 * URI : /members/profiles/texts/{멤버 pk}
 * 권한 : 1
 * 설명 : 코치나 코미가 프로필 글을 등록한다.
 * @param {Number} id 멤버 id(pk)
 * @param {ProfileTextRequestDto} dto 프로필 글 등록 및 수정요청 dto
 * @param {Promise} success
 * 설명 : 프로필 글 등록 완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function postProfileText(id, dto, success, fail) {
  axiosWithToken.post(`/members/profiles/texts/${id}`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-9
 * METHOD : POST
 * URI : /members/profiles/images/{멤버 pk}
 * 권한 : 1
 * 설명 : 코치나 코미가 프로필 사진을 등록한다.
 * @param {Number} id 멤버 id(pk)
 * @param {ProfileImageRequestDto} dto 프로필 사진 등록 및 수정요청 dto
 * @param {Promise} success
 * 설명 : 프로필 사진 등록 완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function postProfileImage(id, dto, success, fail) {
  axiosWithToken.post(`/members/profiles/images/${id}`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-10
 * METHOD : PATCH
 * URI : /members/profiles/texts/{멤버 pk}
 * 권한 : 1
 * 설명 : 코치나 코미가 프로필 글을 수정한다.
 * @param {Number} id 멤버 id(pk)
 * @param {ProfileTextRequestDto} dto 프로필 글 등록 및 수정요청 dto
 * @param {Promise} success
 * 설명 : 프로필 글 수정 완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function patchProfileText(id, dto, success, fail) {
  axiosWithToken.patch(`/members/profiles/texts/${id}`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-11
 * METHOD : PATCH
 * URI : /members/profiles/images/{멤버 pk}
 * 권한 : 1
 * 설명 : 코치나 코미가 프로필 사진을 수정한다. 기존 프로필 사진은 삭제한다.
 * @param {Number} id 멤버 id(pk)
 * @param {ProfileImageRequestDto} dto 프로필 사진 등록 및 수정요청 dto
 * @param {Promise} success
 * 설명 : 프로필 사진 수정 및 기존파일 삭제 완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function patchProfileImage(id, file, success, fail) {
  axiosWithToken.patch(`/members/profiles/images/${id}`, file).then(success).catch(fail)
}

/**
 * API번호 : member-12
 * METHOD : DELETE
 * URI : /members/profiles/texts/{멤버 pk}
 * 권한 : 1
 * 설명 : 코치나 코미가 프로필 글을 삭제한다.
 * @param {Number} id 멤버 id(pk)
 * @param {Promise} success
 * 설명 : 프로필 글 삭제 완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function deleteProfileText(id, dto, success, fail) {
  axiosWithToken.delete(`/members/profiles/texts/${id}`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-14
 * METHOD : POST
 * URI : /members/duplicate/id
 * 권한 : 1
 * 설명 : 회원가입 시 사용자 ID가 중복되는지 검증한다.
 * @param {Number} id 멤버 id(pk)
 * @param {Promise} success
 * 설명 : 프로필 글 삭제 완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function validateDuplicateMember(dto, success, fail) {
  axios.post(`/members/duplicate/id`, dto).then(success).catch(fail)
}
