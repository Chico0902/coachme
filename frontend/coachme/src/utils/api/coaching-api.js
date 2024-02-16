import { publicAxios, authAxios } from '@/utils/http-commons'

/**
 * API번호 : coaching-1
 * METHOD : GET
 * URI : /coachings/live/{live_coaching_id}/{coame_id}
 * 권한 : 1
 * 설명 : 코미가 라이브코칭을 신청한다.
 * @param {number} liveCoachingId 라이브코칭id
 * @param {number} coameId 코미id
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
export function getApplyLiveCoaching(liveCoachingId, coameId, success, fail) {
  authAxios.get(`/coachings/live/${liveCoachingId}/${coameId}`).then(success).catch(fail)
}

/**
 * API번호 : coaching-2
 * METHOD : GET
 * URI : /coachings/live/{live_coaching_id}/coames
 * 권한 : 2
 * 설명 : 코치가 마이페이지>라이브관리에서 라이브코칭을 선택하면 해당 코칭을 수강하고 있는 수강생 목록을 조회한다.
 * @param {number} liveCoachingId 라이브코칭id
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
    "list": [
        {
            "stringId": String,
            "name": String,
            "url": String
        }
}
  * @param {Promise} fail
  * 설명 : 잘못된 요청[400], 서버 오류[500]
  * 코드 : 400, 500
  * body : {
            message : String
          }
  */
export function getLiveCoachingCoame(liveCoachingId, success, fail) {
  authAxios.get(`/coachings/live/${liveCoachingId}/coames`).then(success).catch(fail)
}

/**
 * API번호 : coaching-3
 * METHOD : POST
 * URI : /coachings/categories/{division1(=대분류명)}/{division2 (=소분류명)}
 * 권한 : 0
 * 설명 : 해당 분류 코칭의 정보를 받아온다.
 * @param {string} division1 대분류명
 * @param {string} division2 소분류명
 * @param {object} dto
 * {
    "words": String
    }
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
    "list": [
        {
            "coachingId": Long,
            "avgScore": Long,
            "coachingName": String,
            "reviewCount": Long,
            "profileImg": String
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
export function postCoachingsByCategory(division1, division2, dto, success, fail) {
  publicAxios.post(`/coachings/categories/${division1}/${division2}`, dto).then(success).catch(fail)
}

/**
 * API번호 : coaching-4
 * METHOD : GET
 * URI : /coachings/{coachingId}
 * 권한 : 1
 * 설명 : 코미가 코칭 상세 페이지를 조회한다.
 * @param {number} coachingId 코칭id
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
    "coachName": String,
    "coachProfileImageUrl": String,
    "coachingName": String,
    "htmlDocs": String,
    "summary": String,
    "reviewCount": Long,
    "reviewAvg": float,
    "likeCount": Long
}
  * @param {Promise} fail
  * 설명 : 잘못된 요청[400], 서버 오류[500]
  * 코드 : 400, 500
  * body : {
            message : String
          }
  */
export function getCoachingDetailPage(coachingId, success, fail) {
  authAxios.get(`/coachings/${coachingId}`).then(success).catch(fail)
}

/**
 * API번호 : coaching-5
 * METHOD : GET
 * URI : /coachings/popular
 * 권한 : 0
 * 설명 : 모든 사용자가 메인페이지에서 인기코칭을 확인할 수 있다.
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * 코칭 id, 코칭 대표 영상 url, 코칭 이름, 리뷰 평균 점수, 좋아요 표시(디폴트 : 활성화 안됨)
 * body : {
    
}
  * @param {Promise} fail
  * 설명 : 잘못된 요청[400], 서버 오류[500]
  * 코드 : 400, 500
  * body : {
            message : String
          }
  */
export function getPopularCoachingList(success, fail) {
  publicAxios.get(`/coachings/popular`).then(success).catch(fail)
}

/**
 * API번호 : coaching-6
 * METHOD : GET
 * URI : /coachings/{coaching_id}/videos/{file_id}
 * 권한 : 2
 * 설명 : 코치가 특정 코칭의 대표 영상을 설정할 수 있다.
 * @param {number} coachingId 코칭id
 * @param {number} fileId 영상id(대표영상으로 만들것)
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
		"message": String("regist video successful")
}
  * @param {Promise} fail
  * 설명 : 잘못된 요청[400], 서버 오류[500]
  * 코드 : 400, 500
  * body : {
            message : String
          }
  */
export function getMainVideo(coachingId, fileId, success, fail) {
  authAxios.get(`/coachings/${coachingId}/videos/${fileId}`).then(success).catch(fail)
}

/**
 * API번호 : coaching-7
 * METHOD : GET
 * URI : /coachings/{coaching_id}/live
 * 권한 : 1
 * 설명 : 코칭 상세페이지에서 해당 코칭의 라이브 코칭 목록을 조회한다.
 * @param {number} coachingId 코칭id
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * body : {
		"id": Long,
		"date": LocalDateTime
}
  * @param {Promise} fail
  * 설명 : 잘못된 요청[400], 서버 오류[500]
  * 코드 : 400, 500
  * body : {
            message : String
          }
  */
export function getAllLivesInCoaching(coachingId, success, fail) {
  authAxios.get(`/coachings/${coachingId}/live`).then(success).catch(fail)
}

/**
 * API번호 : coaching-8
 * METHOD : GET
 * URI : /coachings/{coaching_id}/videos
 * 권한 : 1
 * 설명 : 코칭 상세페이지에서 해당 코칭의 영상 목록을 조회한다.
 * @param {number} coachingId 코칭id
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * 	"list": [
				{
						"url": string
				}
		]
  * @param {Promise} fail
  * 설명 : 잘못된 요청[400], 서버 오류[500]
  * 코드 : 400, 500
  * body : {
            message : String
          }
  */
export function getAllCoachingVideos(coachingId, success, fail) {
  authAxios.get(`/coachings/${coachingId}/videos`).then(success).catch(fail)
}

/**
 * API번호 : coaching-9
 * METHOD : POST
 * URI : /coachings/{coaching_id}/videos
 * 권한 : 2
 * 설명 : AI로 편집된 영상이 저장된 S3 URL을 전달받아 DB에 저장한다.
 * @param {number} coachingId 코칭id
 * @param {Object} dto
 * {
		"coachId": Long,
		"videoName": String,
		"url": String
  }
 * @param {Promise} success
 * 설명 : 정상 조회완료
 * 코드 : 200
 * {
		"message": String
  }
  * @param {Promise} fail
  * 설명 : 잘못된 요청[400], 서버 오류[500]
  * 코드 : 400, 500
  * {
		"message": String
    }
  */
export function postAIVideo(coachingId, dto, success, fail) {
  authAxios.post(`/coachings/${coachingId}/videos`, dto).then(success).catch(fail)
}
