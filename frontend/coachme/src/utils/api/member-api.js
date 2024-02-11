import { publicAxios, authAxios, authFileAxios } from '@/utils/http-commons'

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
  publicAxios.post(`/members`, dto).then(success).catch(fail)
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
  publicAxios.patch(`/members/passswords`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-4
 * METHOD : POST
 * URI : /members/privileges/elevations
 * 권한 : 1
 * 설명 : 회원정보 권한 상승 요청 시, 유효한 요청인지 확인 후 권한 상승 목록에 추가한다.
 * @param {String} token accessToken(pinia 사용시 accessToken.value)
 * @param {ElevationRequestDto} dto 회원정보 권한 상승 요청 시 해당 계정 ID와 포트폴리오(htmlDocs)를 전송한다.
 * @param {Promise} success
 * 설명 : 권한 상승목록에 추가 완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청
 * 코드 : 400
 * body : {
            message : String
          }
 */
export function postRequestElevation(dto, success, fail) {
  authAxios.post(`/members/privileges/elevations`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-5
 * METHOD : GET
 * URI : /members/{longId}
 * 권한 : 1
 * 설명 : 기본 회원정보 조회
 * @param {String} token accessToken(pinia 사용시 accessToken.value)
 * @param {Number} longId 사용자 pk
 * @param {Promise} success
 * 설명 : 기본 회원정보 데이터 전송
 * 코드 : 200
 * body : {
            stringId : String
            name : String
            nick : String
            email : String
          }
 * @param {Promise} fail
 * 설명 : [400]잘못된 요청, [403]권한 오류
 * 코드 : 400, 403
 * body : {
            message : String
          }
 */
export function getMemberInfo(longId, success, fail) {
  authAxios.get(`/members/${longId}`).then(success).catch(fail)
}

/**
 * API번호 : member-6
 * METHOD : PATCH
 * URI : /members/{longId}
 * 권한 : 1
 * 설명 : 회원정보 수정 시 입력한 비밀번호를 검증한 후, 회원정보 변경
 * @param {String} token accessToken(pinia 사용시 accessToken.value)
 * @param {Number} longId 사용자 pk
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
export function patchMemberInfo(longId, dto, success, fail) {
  authAxios.patch(`/members/${longId}`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-7
 * METHOD : GET
 * URI : /members/profiles/{longId}
 * 권한 : 1
 * 설명 : 코미나 코치의 프로필 글과 사진에 대한 조회결과를 전송한다.
 * @param {Number} longId 멤버 id(pk)
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
export function getProfile(longId, success, fail) {
  authAxios.get(`/members/${longId}/profiles`).then(success).catch(fail)
}

/**
 * API번호 : member-8
 * METHOD : POST
 * URI : /members/profiles/texts/{longId}
 * 권한 : 1
 * 설명 : 코치나 코미가 프로필 글을 등록한다.
 * @param {String} token accessToken(pinia 사용시 accessToken.value)
 * @param {Number} longId 멤버 id(pk)
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
export function postProfileText(longId, dto, success, fail) {
  authAxios.post(`/members/${longId}/profiles/texts`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-9
 * METHOD : POST
 * URI : /members/profiles/images/{longId}
 * 권한 : 1
 * 설명 : 코치나 코미가 프로필 사진을 등록한다.(기존에 사진이 있으면 삭제)
 * @param {String} token accessToken(pinia 사용시 accessToken.value)
 * @param {Number} longId 멤버 id(pk)
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
export function postProfileImage(longId, dto, success, fail) {
  authFileAxios.post(`/members/${longId}/profiles/images`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-12
 * METHOD : DELETE
 * URI : /members/{longId}/profiles/images
 * 권한 : 1
 * 설명 : 코치나 코미가 프로필 사진을 삭제한다.
 * @param {String} token accessToken(pinia 사용시 accessToken.value)
 * @param {Number} longId 멤버 id(pk)
 * @param {Promise} success
 * 설명 : 프로필 사진 삭제 완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 요청정보 오류[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function deleteProfileImage(longId, success, fail) {
  authAxios.delete(`/members/${longId}/profiles/images`).then(success).catch(fail)
}

/**
 * API번호 : member-13
 * METHOD : POST
 * URI : /members/duplicate/id
 * 권한 : 0
 * 설명 : 회원가입 시 사용자 ID가 중복되는지 검증한다.
 * @param {MemberDuplicateRequestDto} dto 멤버 stringId가 들어있는 dto
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
  publicAxios.post(`/members/duplicate/id`, dto).then(success).catch(fail)
}

/**
 * API번호 : member-15
 * METHOD : GET
 * URI : /members/{longId}/calendar
 * 권한 : 0
 * 설명 : 코미가 마이페이지>코칭일정 메뉴에서 자신이 신청한 코칭 일정을 캘린더로 확인할 수 있다.
 * @param {Number} longId 코미 id
 * @param {Promise} success
 * 코드 : 200
 * body : {
            ?
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청
 * 코드 : 403
 * body : {
            ?
          }
 */
export function getCoameLiveCoachingCalendar(longId, success, fail) {
  authAxios.get(`/members/${longId}/calendar`).then(success).catch(fail)
}
