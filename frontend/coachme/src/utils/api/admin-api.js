import { authBackendAxios } from '@/utils/http-commons'
import { getRefreshToken } from '../functions/auth'

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
export function getAllMemberInfo(success, fail) {
  refreshAxios
    .get(`/admin/members`, {
      headers: {
        Authorization: 'bearer ' + getRefreshToken()
      }
    })
    .then(success)
    .catch(fail)
}

/**
 * API번호 : admin-2
 * METHOD : GET
 * URI : /admin/privileges/elevations
 * 권한 : 3
 * 설명 : 권한 상승요청 목록을 조회한다.
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
export function getAllElevations(success, fail) {
  refreshAxios
    .get(`/admin/privileges/elevations`, {
      headers: {
        Authorization: 'bearer ' + getRefreshToken()
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
  refreshAxios
    .patch(`/admin/privileges/elevations`, dto, {
      headers: {
        Authorization: 'bearer ' + getRefreshToken()
      }
    })
    .then(success)
    .catch(fail)
}
