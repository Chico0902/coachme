import { publicAxios, authAxios } from '@/utils/http-commons'

/**
 * API번호 : review-1
 * METHOD : POST
 * URI : /review/coach
 * 권한 : 1
 * 설명 : 코미는 코치에 대한 리뷰를 작성한다.
 * @param {object} dto
 * {
    "coameId" : 1,
    "coachId" : 2,
    "comment" : "코치 리뷰 작성",
    "score" : 4
}
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
		"message": String("Coach review write successfully completed")
}
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function postcoachReview(dto, success, fail) {
  authAxios.post(`/review/coach`, dto).then(success).catch(fail)
}

/**
 * API번호 : review-2
 * METHOD : POST
 * URI : /review/coaching
 * 권한 : 1
 * 설명 : 코미는 코칭에 대한 리뷰를 작성한다.
 * @param {object} dto
 * {
    "coameId" : 1,
    "coachingId" : 1,
    "comment" : "코칭 리뷰 작성",
    "score" : 4
}
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
		"message": String("Coaching review write successfully completed")
}
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function postcoachingReview(dto, success, fail) {
  authAxios.post(`/review/coaching`, dto).then(success).catch(fail)
}

/**
 * API번호 : review-3
 * METHOD : PATCH
 * URI : /review/{reviewId}
 * 권한 : 1
 * 설명 : 코미는 본인의 리뷰를 수정할 수 있다.
 * @param {number} reviewId  리뷰Id
 * @param {object} dto
 * {
    "comment" : "리뷰 수정",
    "score" : 5
}
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
    "message": "Review update successfully completed"
}
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function patchMyReview(reviewId, dto, success, fail) {
  authAxios.patch(`/review/${reviewId}`, dto).then(success).catch(fail)
}

/**
 * API번호 : review-4
 * METHOD : DELETE
 * URI : /review/{reviewId}
 * 권한 : 1
 * 설명 : 코미는 본인의 리뷰를 삭제할 수 있다.
 * @param {number} reviewId  리뷰Id
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
    "message": "Review delete successfully completed"
}
 * @param {Promise} fail
 * 설명 : 잘못된 요청[400], 서버 오류[500]
 * 코드 : 400, 500
 * body : {
            message : String
          }
 */
export function deleteMyReview(reviewId, success, fail) {
  authAxios.delete(`/review/${reviewId}`).then(success).catch(fail)
}

/**
 * API번호 : review-5
 * METHOD : GET
 * URI : /review/coach/{coachId}
 * 권한 : 0
 * 설명 : 코치에 대한 리뷰를 조회한다.
 * @param {number} coachId  코치Id
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
    "list": [
        {
            "coameId": 1,
            "coachId": 2,
            "comment": "코치 리뷰 작성 1",
            "score": 5
        },
        {
            "coameId": 2,
            "coachId": 2,
            "comment": "코치 리뷰 작성 2",
            "score": 4
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
export function getCoachReview(coachId, success, fail) {
  authAxios.get(`/review/coach/${coachId}`).then(success).catch(fail)
}

/**
 * API번호 : review-6
 * METHOD : GET
 * URI : /review/coaching/{coachingId}
 * 권한 : 0
 * 설명 : 코칭에 대한 리뷰를 조회한다.
 * @param {number} coachingId  코칭Id
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
    "list": [
        {
            "coameId": 1,
            "coachingId": 1,
            "comment": "코칭 리뷰 작성 1",
            "score": 4
        },
        {
            "coameId": 2,
            "coachingId": 1,
            "comment": "코칭 리뷰 작성 2",
            "score": 5
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
export function getCoachingReview(coachingId, success, fail) {
  authAxios.get(`/review/coaching/${coachingId}`).then(success).catch(fail)
}
