import { authBackendAxios } from '@/utils/http-commons'

const authAxios = authBackendAxios()

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
  authAxios.get(`/admin/members`).then(success).catch(fail)
}
