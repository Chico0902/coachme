import { authAxios } from '@/utils/http-commons'

/**
 * API번호 : dm-1
 * METHOD : GET
 * URI : /dm/room/{longId(member PK)}
 * 권한 : 1
 * 설명 : 회원의 전체 DM방 목록을 불러온다
 * @param {Number} longId 회원 id(pk)
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getMyDmRooms(longId, success, fail) {
  authAxios.get(`/dm/room/${longId}`).then(success).catch(fail)
}

/**
 * API번호 : dm-2
 * METHOD : GET
 * URI : /dm/{roomId}
 * 권한 : 1
 * 설명 : DM방의 이전 DM 내역을 불러온다
 * @param {Number} roomId 방 id(pk)
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
    "list": [
        {
            "memberId": 2,
            "memberName": "관리자",
            "memberProfileUrl": "/src/assets/icons/coame.png",
            "message": "채팅1",
            "createDate": "2024-02-06T15:41"
        }
      ]
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getDmsByRoomId(roomId, success, fail) {
  authAxios.get(`/dm/${roomId}`).then(success).catch(fail)
}

/**
 * API번호 : dm-3
 * METHOD : GET
 * URI : /dm/room/{longId(member PK)}
 * 권한 : 1
 * 설명 : 회원이 DM방에 들어간다(디엠방 생성) / 회원과 코치의 DM방을 불러온다
 * @param {Number} myLongId 본인 id(pk)
 * @param {Number} coachLongId 상대방 id(pk)
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
            message : String
          }
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getDmsByMemberId(myLongId, coachLongId, success, fail) {
  authAxios.get(`/dm/room/enter/${myLongId}/${coachLongId}`).then(success).catch(fail)
}
