import { publicAxios, authAxios } from '@/utils/http-commons'

/**
 * API번호 : like-1
 * METHOD : GET
 * URI : /like/{coameId}/coaches/{coachId}
 * 권한 : 1
 * 설명 : 코미는 코치를 찜콩한다.
 * @param {number} coameId
 * @param {number} coachId
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
		"message": String("regist like successful")
}
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getLikeCoach(coameId, coachId, success, fail) {
  authAxios.get(`/like/${coameId}/coaches/${coachId}`).then(success).catch(fail)
}

/**
 * API번호 : like-2
 * METHOD : GET
 * URI : /like/{coameId}/coachings/{coachingId}
 * 권한 : 1
 * 설명 : 코미는 코칭을 찜콩한다.
 * @param {number} coameId
 * @param {number} coachingId
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
		"message": String("regist like successful")
}
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getLikeCoaching(coameId, coachingId, success, fail) {
  authAxios.get(`/like/${coameId}/coachings/${coachingId}`).then(success).catch(fail)
}

/**
 * API번호 : like-3
 * METHOD : DELETE
 * URI : /like/{coameId}/coaches/{coachId}
 * 권한 : 1
 * 설명 : 코미는 코치 찜콩을 취소한다.
 * @param {number} coameId
 * @param {number} coachId
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
		"message": String("delete like successful")
}
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function deleteLikeCoach(coameId, coachId, success, fail) {
  authAxios.delete(`/like/${coameId}/coaches/${coachId}`).then(success).catch(fail)
}

/**
 * API번호 : like-4
 * METHOD : DELETE
 * URI : /like/{coameId}/coachings/{coachingId}
 * 권한 : 1
 * 설명 : 코미는 코칭 찜콩을 취소한다.
 * @param {number} coameId
 * @param {number} coachingId
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
		"message": String("regist like successful")
}
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function deleteLikeCoaching(coameId, coachingId, success, fail) {
  authAxios.delete(`/like/${coameId}/coachings/${coachingId}`).then(success).catch(fail)
}

/**
 * API번호 : like-5
 * METHOD : GET
 * URI : /like/check/{coameId}/coachings/{coachingId}
 * 권한 : 1
 * 설명 : 코미는 찜콩한 코칭에 대해서 확인할 수 있다.
 * @param {number} coameId
 * @param {number} coachingId
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
		"isLike": boolean
}
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getCheckCoachingLike(coameId, coachingId, success, fail) {
  authAxios.get(`/like/check/${coameId}/coachings/${coachingId}`).then(success).catch(fail)
}

/**
 * API번호 : like-6
 * METHOD : GET
 * URI : /like/check/{coameId}/coaches/{coachId}
 * 권한 : 1
 * 설명 : 코미는 찜콩한 코치에 대해서 확인할 수 있다.
 * @param {number} coameId
 * @param {number} coachId
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
		"isLike": boolean
}
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function getCheckCoachLike(coameId, coachId, success, fail) {
  authAxios.get(`/like/check/${coameId}/coaches/${coachId}`).then(success).catch(fail)
}
