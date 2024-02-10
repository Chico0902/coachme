<script setup>
import navbar from '@/components/molecules/LoginNavBar.vue'
import CoachingDetailCard from '@/components/molecules/CoachingDetailCard.vue'
import ChatBox from '@/components/molecules/CoachingChatBox.vue'
import DetailTopBar from '@/components/molecules/DetailTopBar.vue'
import Reviews from '@/components/molecules/ReviewDetailCard.vue'
import CoachingScheduleList from '@/components/molecules/CoachingScheduleList.vue'
import CoachingCard from '@/components/molecules/CoachingCard.vue'
import { ref, onMounted, onBeforeMount, computed } from 'vue'
import { useRoute } from "vue-router";
import { getCoachingDetailPage } from '@/utils/api/coaching-api'
import { getVideoList } from '@/utils/api/coach-api'
import { getCoachingReview } from '@/utils/api/review-api'
import footerBar from '@/components/molecules/CustomShortFooter.vue'

const route = useRoute()

const menus = ref(['코칭 소개', '라이브 일정', '영상 목록', '리뷰'])
// 중단 메뉴 리스트

const videoCoachingId = ref(1) // 영상 조회용 코칭 id
const coachingDetail = ref([]) // 코칭 상세
const reviews = ref([]) // 리뷰
const breadCrumbs = ref([]) // 대분류 소분류

// const videos = ref([
//   { coachingId: 1, coachingName: "이것만 알면 당신도 할 수 있다", videoId: 1, videoUrl: "https://naver.com", videoName: "기본편" },
//   { coachingId: 1, coachingName: "이것만 알면 당신도 할 수 있다", videoId: 2, videoUrl: "https://naver.com", videoName: "연습편" },
//   { coachingId: 1, coachingName: "이것만 알면 당신도 할 수 있다", videoId: 6, videoUrl: "https://naver.com", videoName: "실전편" },
//   { coachingId: 1, coachingName: "이것만 알면 당신도 할 수 있다", videoId: 7, videoUrl: "https://naver.com", videoName: "종합" },
// ]);

const videos = ref([])

const reviewData = (data) => {

} // 리뷰 입력폼에서 입력받은 리뷰와 별점을 처리하는 함수

const videoLink = 'https://www.youtube.com/embed/k3_tw44QsZQ?rel=0'
// 코칭 미리보기 영상 링크

var today = new Date()

var year = today.getFullYear()
var month = ('0' + (today.getMonth() + 1)).slice(-2)
var day = ('0' + today.getDate()).slice(-2)

var dateString = year + '/' + month + '/' + day
// 오늘 날짜 구하기

const date = ref(dateString)
// 오늘 날짜로 기본 세팅

const scheduleList = ref([
  { date: '2024/02/01', time: ['14:00', '15:00', '16:00', '17:00', '18:00'] },
  { date: '2024/02/05', time: ['12:00', '16:00', '17:00'] },
  { date: '2024/02/06', time: ['13:00', '15:00', '15:30'] },
  { date: '2024/02/09', time: ['14:00', '15:00', '16:00'] },
  { date: '2024/02/23', time: ['12:00', '16:00', '17:00'] }
])
// 코칭 일자와 코칭 시간표 예시

const filteredDates = ref([])
const filteredTimeTable = ref([])
// 코칭 일자만 저장하는 배열과 코칭 일자에 따른 시간표만 저장하는 배열

const scheduleTimeTable = (date) => {
  if (scheduleList.value.length === 0) {
    filteredTimeTable.value = []
    return
  }
  const scheduleItem = scheduleList.value.find((item) => item.date === date)
  filteredTimeTable.value = scheduleItem ? scheduleItem.time : []
} // 코칭 일자에 따라, 코칭 시간표를 구하는 함수

const getScheduleDate = () => {
  filteredDates.value = scheduleList.value.map((item) => item.date)
} // 코칭 일자만 구하는 함수

onMounted(() => {
  getScheduleDate()
  scheduleTimeTable(date.value)
})
// 최초 오늘 날짜의 코칭 예정을 미리 구하여 처리하기

const ratio = 16 / 9

onBeforeMount(() => {
  const coachingId = route.params.id
  const coachId = ref()

  // 코칭 id로 코칭 상세페이지 
  getCoachingDetailPage(
    coachingId,
    (success) => {
      console.log(success)
      coachingDetail.value = success.data
      breadCrumbs.value = [coachingDetail.value.mainCategory, coachingDetail.value.subCategory]
      coachId.value = coachingDetail.value.coachId
      videoCoachingId.value = coachingId

      // 코치 id를 획득한 뒤, 코칭 영상 조회
      getVideoList(
        coachId.value,
        (success) => {
          console.log(success)
          videos.value = success.data.list
        },
        (fail) => {
          console.log(fail)
        }
      )
    },
    (fail) => {
      console.log(fail)
    }
  )
  // 코칭 id로 리뷰 목록
  getCoachingReview(
    coachingId,
    (success) => {
      console.log(success)
      reviews.value = success.data.list
    },
    (fail) => {
      console.log(fail)
    }
  )
})

const groupedFilteredVideos = computed(() => {
  const filtered = videos.value.filter(video => video.coachingId == videoCoachingId.value);

  const grouped = [];
  filtered.forEach(video => {
    grouped.push(video);
  });

  return grouped;
});
// 코치의 전체 영상 중, 현재 코칭에 해당하는 영상만 추출

</script>

<template>
  <!-- nav -->
  <div class="nav-bar">
    <navbar></navbar>
  </div>
  <div class="all">
    <div class="main-layout">
      <div class="mypage-outside">
        <div class="mainpage">
          <div class="profile">
            <!-- 코칭 상세 정보 -->
            <CoachingDetailCard :title="coachingDetail.coachingName" :coach="coachingDetail.coachName"
              :rating-model="coachingDetail.reviewAvg" :review-count="coachingDetail.reviewCount"
              :bread-crumbs="breadCrumbs" :previewVideoSrc="videoLink" style="margin-left: 0.6vw">
            </CoachingDetailCard>
            <q-separator></q-separator>

            <!-- 코칭 상세페이지 중단 메뉴 -->
            <div class="coaching-menu">
              <DetailTopBar :menus="menus"></DetailTopBar>
            </div>

            <!-- 코칭 소개. 직접 작성한 부분이 이곳에 들어감 -->
            <div class="coaching-introduction">
              <h2>코칭 소개</h2>
              <div v-html="coachingDetail.htmlDocs" class="coaching-desc"></div>
            </div>

            <q-separator></q-separator>

            <!-- 라이브 코칭 목록 -->
            <div class="coaching-live-schedule">
              <h2>라이브 일정</h2>
              <div class="coaching-live-calender">
                <q-date name="Schedule" v-model="date" @click="scheduleTimeTable(date)" color="blue-10" today-btn
                  :events="filteredDates"></q-date>
                <CoachingScheduleList :date="date" :timeTable="filteredTimeTable"></CoachingScheduleList>
              </div>
            </div>

            <q-separator></q-separator>

            <!-- 영상 목록 -->
            <div class="coaching-video-list">
              <h2>영상 목록</h2>
              <div class="coaching-card-outside element-with-scrollbar">
                <div v-if="groupedFilteredVideos.length > 0">
                  <div v-for="videoGroup in groupedFilteredVideos" :key="videoGroup.coachingName" class="coaching-card">
                    <CoachingCard :label="videoGroup.videoName" :ratio="ratio"></CoachingCard>
                  </div>
                </div>
                <div v-else class="coaching-card" style="font-size: 16px;">
                  조회 가능한 영상이 없습니다.
                </div>
              </div>
            </div>

            <q-separator></q-separator>

            <!-- 리뷰 -->
            <div class="coaching-review">
              <h2>리뷰</h2>
              <Reviews :reviews="reviews" :rating-model="coachingDetail.reviewAvg"
                v-bind:review-count="coachingDetail.reviewCount" @review-data="reviewData"></Reviews>
            </div>
          </div>
        </div>
        <!-- 우측 안내창 -->
        <div class="chat-box">
          <ChatBox :coach="coachingDetail.coachName"></ChatBox>
        </div>

        <!-- 채팅 플로팅 버튼 -->
        <div class="chat-button">
          <q-btn round size="20px" color="amber-7" icon="chat"></q-btn>
        </div>
      </div>
    </div>
  </div>

  <div class="footer">
    <footerBar />
  </div>
</template>

<style scoped>
.all {
  display: flex;
  justify-content: center;
}

.main-layout {
  width: 1280px;
  min-height: calc(90vh - 70px);
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
}

.nav-bar {
  width: 100%;
  height: 70px;
  justify-content: center;
  text-align: center;
  border-bottom: #034c8c 1px solid;
  display: flex;
  justify-content: center;
}

.mypage-outside {
  display: flex;
  justify-content: space-between;
}

.mainpage {
  background-color: white;
  width: 80%;
  height: 70vh;
  margin: auto;
  margin-top: 5vh;
  margin-bottom: 5vh;
  border-radius: 1.5rem;
  overflow: scroll;
  display: flex;
  text-align: center;
  flex-direction: row;
  -ms-overflow-style: none;
}

.mainpage::-webkit-scrollbar {
  display: none;
}

.profile {
  width: 80%;
}

.chat-box {
  max-height: fit-content;
  margin-top: 30vh;
  margin-right: 3vw;
}

.chat-button {
  position: fixed;
  bottom: 13vh;
  right: 14vw;
  color: #fff;
  text-align: center;
}

h2 {
  font-size: 1.5rem;
  padding: 0;
  margin: 0;
  margin-left: 1vw;
  text-align: left;
}

.coaching-card-outside {
  display: flex;
  margin-left: 1.2vw;
  margin-top: 2vh;
  margin-bottom: 5vh;
  overflow-x: scroll;
}

.coaching-card {
  margin: 30px 15px 30px;
}

.coaching-introduction {
  text-align: left;
  margin-bottom: 4vh;
}

.coaching-desc {
  margin-left: 1.2vw;
  margin-top: 2vh;
  font-size: 16px;
}

.coaching-live-schedule {
  text-align: left;
  margin-bottom: 4vh;
}

.coaching-live-calender {
  display: flex;
  margin-left: 0.8vw;
  margin-bottom: 3vh;
}

.coaching-video-list {
  text-align: left;
  margin-bottom: 3vh;
}

.element-with-scrollbar {
  overflow: hidden;
}

.element-with-scrollbar:hover {
  overflow: auto;
}

.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}
</style>
