import { backendAxios } from '@/utils/http-commons'

const axios = backendAxios()

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
