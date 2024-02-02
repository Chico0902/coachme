import { authBackendAxios } from '@/utils/http-commons'
import { getRefresh } from '../api/auth-api'

const refreshAxios = authBackendAxios()

/**
 * API번호 : admin-1
 * METHOD : GET
 * URI : /admin/members
 * 권한 : 3
 * 설명 : 모든 회원정보를 받아온다.
 * @param {Promise} success
 * 설명 : 모든 회원정보 리스트로 반환
 * 코드 : 200
 * body : {
            id : String
            priv : String
            name : String
            nick : String
            email : String
            cdate : String
            elev : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function getAllMemberInfo(token, success, fail) {
  refreshAxios
    .get(`/admin/members`, {
      headers: {
        Authorization: 'Bearer ' + token
      }
    })
    .then(success)
    .catch(fail)

  // 재발급 요청 메서드
  refreshAxios.interceptors.response.use(function (response) {
    if (response.status === '401') {
      // 엑세스 토큰 만료일 경우, 토큰 재발급 요청
      if (response.data.message === 'Access Token Expired') {
        getRefresh()
        // 리프레쉬 토큰 만료일 경우, 로그인 페이지로 이동
      } else if (response.data.message === 'Refresh Token Expired') router.push('/login')
    }
  })
}

/**
 * API번호 : admin-2
 * METHOD : GET
 * URI : /admin/privileges/elevations
 * 권한 : 3
 * 설명 : 권한 상승요청 목록을 조회한다.
 * @param {String} token Access token(accessToken.value)
 * @param {Promise} success
 * 설명 : 모든 회원정보 리스트로 반환
 * 코드 : 200
 * body : {
            id: 'Long(Member PK)',
            name: 'String',
            portfolio: 'String(Raw HTML)'
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function getAllElevations(token, success, fail) {
  refreshAxios
    .get(`/admin/privileges/elevations`, {
      headers: {
        Authorization: 'Bearer ' + token
      }
    })
    .then(success)
    .catch(fail)
}

/**
 * API번호 : admin-3
 * METHOD : PATCH
 * URI : /admin/privileges/elevations
 * 권한 : 3
 * 설명 : 해당 아이디의 권한을 1에서 2로 상승시킨다.
 * @param {AdminElevateRequestDto} dto 권한을 상승할 Member 목록을 pk로 전달한다.
 * @param {Promise} success
 * 설명 : 권한 변경 완료
 * 코드 : 200
 * body : { 
            message: 'String' 
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청
 * 코드 : 403
 * body : {
            message : String
          }
 */
export function patchElevations(dto, success, fail) {
  refreshAxios.patch(`/admin/privileges/elevations`, dto, {}).then(success).catch(fail)
}
