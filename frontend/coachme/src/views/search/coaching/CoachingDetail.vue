<script setup>
import navbar from '@/components/molecules/LoginNavBar.vue'
import CoachingDetailCard from '@/components/molecules/CoachingDetailCard.vue'
import ChatBox from '@/components/molecules/CoachingChatBox.vue'
import DetailTopBar from '@/components/molecules/DetailTopBar.vue'
import Reviews from '@/components/molecules/ReviewDetailCard.vue'
import CoachingScheduleList from '@/components/molecules/CoachingScheduleList.vue'
import CoachingCard from '@/components/molecules/CoachingCard.vue'
import footerBar from '@/components/molecules/CustomShortFooter.vue'
import router from '@/router'
import Swal from 'sweetalert2'
import DmWindow from '@/components/molecules/DmWindow.vue'
import DmList from '@/components/molecules/DmList.vue'
import { useChatStore } from '@/stores/chat-status'
import { ref, onBeforeMount, computed } from 'vue'
import { useRoute } from 'vue-router'
import { getCoachingDetailPage, getAllLivesInCoaching, getAllCoachingVideos } from '@/utils/api/coaching-api'
import { getCoachingReview, postcoachingReview, deleteMyReview, patchMyReview } from '@/utils/api/review-api'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'

/**
 * VARIABLES
 */

// in pinia
const authStore = useAuthStore()
const chatStore = useChatStore()
const { useDmWindow } = storeToRefs(chatStore)
const { openChatList } = chatStore

const route = useRoute()
const menus = ref(['코칭 소개', '라이브 일정', '영상 목록', '리뷰']) // 메뉴
const videoCoachingId = ref(1) // 영상 조회용 코칭 id
const coachingDetail = ref([]) // 코칭 상세
const reviews = ref([]) // 리뷰
const breadCrumbs = ref([]) // 대분류 소분류
const videos = ref([])
const currentMenu = ref('introduce')
const myLongId = ref()
const coachingLongId = ref()
const coachId = ref('')

// 현재시간 계산
const now = computed(() => {
  const nowObject = new Date()
  const nowMonth = nowObject.getMonth() + 1 < 10 ? `0${nowObject.getMonth() + 1}` : nowObject.getMonth() + 1
  const nowDate = nowObject.getDate() < 10 ? `0${nowObject.getDate()}` : nowObject.getDate()
  return `${nowObject.getFullYear()}-${nowMonth}-${nowDate}`
})
const date = ref(now.value)

const scheduleList = ref({})

// 코칭 일자를 저장하는 배열
const events = computed(() => {
  return Object.keys(scheduleList.value)
})

// 코칭 일자에 따라, 코칭 시간표를 구하는 함수
const timeTable = computed(() => {
  return scheduleList.value[getDateKey(date.value)]
})

/**
 * METHODS
 */

// 클릭한 버튼에 따라 메뉴구성 변경
const changeMenu = (e) => {
  switch (e.srcElement.innerText) {
    case '코칭 소개':
      currentMenu.value = 'introduce'
      break
    case '라이브 일정':
      currentMenu.value = 'live'
      break
    case '영상 목록':
      currentMenu.value = 'video'
      break
    case '리뷰':
      currentMenu.value = 'review'
      break
  }
}

// 코칭 시간을 키로 변경하는 함수
function getDateKey(date) {
  return date.substring(0, 10).replace(/-/g, '/')
}

// 라이브코칭 정보를 스케줄 데이터에 맞게 변경하는 함수
function parseSchedule(list) {
  scheduleList.value = {}
  list.forEach((schedule) => {
    const dateKey = schedule.date.substring(0, 10).replace(/-/g, '/')
    const time = schedule.date.substring(11, 16)

    if (scheduleList.value[dateKey] == undefined) scheduleList.value[dateKey] = [{ id: schedule.id, time: time }]
    else scheduleList.value[dateKey].push({ id: schedule.id, time: time })
  })
}

// 리뷰 작성
const reviewData = (data) => {
  myLongId.value = decodeToken(getAccessToken()).longId

  const dto = {
    coameId: myLongId.value,
    coachingId: coachingLongId.value,
    comment: data.review,
    score: data.rating
  }
  // 리뷰 dto

  // 리뷰 작성 후 리로드
  new Promise((resolve, reject) =>
    // 리뷰 작성
    postcoachingReview(
      dto,
      (success) => {
        Swal.fire({
          icon: 'success',
          title: '리뷰를 작성했습니다.',
          showConfirmButton: true,
          timer: 1500
        })
        resolve()
      },
      (fail) => {
        reject(fail)
      }
    )
  ).then(() => {
    // 코칭 상세 정보
    getCoachingDetailPage(
      coachingLongId.value,
      (success) => {
        coachingDetail.value = success.data
      },
      (fail) => console.error(fail)
    )

    // 코칭 리뷰
    getCoachingReview(
      coachingLongId.value,
      (success) => {
        reviews.value = success.data.list
      },
      (fail) => console.error(fail)
    )
  })
}

// 리뷰 삭제
const deleteReview = (reviewId) => {
  new Promise((resolve, reject) =>
    // 리뷰 삭제
    deleteMyReview(
      reviewId,
      (success) => {
        Swal.fire({
          icon: 'success',
          title: '리뷰를 삭제했습니다.',
          showConfirmButton: true,
          timer: 1500
        })
        resolve()
      },
      (fail) => {
        reject(fail)
      }
    )
  ).then(() => {
    // 리뷰 작성 후 정보들 다시 리로드

    // 코치 상세 정보
    getCoachingDetailPage(
      coachingLongId.value,
      (success) => {
        coachingDetail.value = success.data
      },
      (fail) => {
        console.error(fail)
      }
    ),
      // 코치 리뷰
      getCoachingReview(
        coachingLongId.value,
        (success) => {
          reviews.value = success.data.list
        },
        (fail) => {
          console.error(fail)
        }
      )
  })
}

const updateReview = (data) => {
  const reviewDto = {
    comment: data.review.value,
    score: data.ratingScore.value
  }

  new Promise((resolve, reject) =>
    // 리뷰 수정
    patchMyReview(
      data.reviewId,
      reviewDto,
      (success) => {
        Swal.fire({
          icon: 'success',
          title: '리뷰를 수정했습니다.',
          showConfirmButton: true,
          timer: 1500
        })
        resolve()
      },
      (fail) => {
        reject(fail)
      }
    )
  ).then(() => {
    // 리뷰 수정 후 정보들 다시 리로드

    // 코치 상세 정보
    getCoachingDetailPage(
      coachingLongId.value,
      (success) => {
        coachingDetail.value = success.data
      },
      (fail) => {
        console.error(fail)
      }
    ),
      // 코치 리뷰
      getCoachingReview(
        coachingLongId.value,
        (success) => {
          reviews.value = success.data.list
        },
        (fail) => {
          console.error(fail)
        }
      )
  })
}

onBeforeMount(() => {
  if (authStore.isLogin === false) {
    Swal.fire({
      icon: 'warning',
      title: '로그인이 필요합니다.',
      text: ' 로그인 페이지로 이동합니다.',
      showConfirmButton: true,
      timer: 1500
    })
    router.push('/login')
  }

  const coachingId = route.params.id
  coachingLongId.value = coachingId

  // 코칭 id로 코칭 상세페이지
  new Promise((resolve, reject) =>
    getCoachingDetailPage(
      coachingId,
      (success) => {
        coachingDetail.value = success.data
        breadCrumbs.value = [coachingDetail.value.mainCategory, coachingDetail.value.subCategory]
        coachId.value = coachingDetail.value.coachId
        videoCoachingId.value = coachingId
        resolve()
      },
      (fail) => reject(fail)
    )
  )
    .then(() => {
      // 코칭 영상 조회
      getAllCoachingVideos(
        coachingId,
        (success) => {
          videos.value = success.data.list
        },
        (fail) => console.error(fail)
      )
      // 리뷰 목록 조회
      getCoachingReview(
        coachingId,
        (success) => {
          reviews.value = success.data.list
        },
        (fail) => console.error(fail)
      )
      // 코칭 스케줄 조회
      getAllLivesInCoaching(
        coachingId,
        (success) => {
          parseSchedule(success.data.list)
        },
        (fail) => console.error(fail)
      )
    })
    .catch((e) => console.error(e))
})
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
            <CoachingDetailCard
              :title="coachingDetail.coachingName"
              :coach="coachingDetail.coachName"
              :img="coachingDetail.coachProfileImageUrl"
              :rating-model="coachingDetail.reviewAvg"
              :review-count="coachingDetail.reviewCount"
              :bread-crumbs="breadCrumbs"
              :previewVideoSrc="coachingDetail.representUrl"
              style="margin-left: 0.6vw"
            >
            </CoachingDetailCard>
            <q-separator></q-separator>

            <!-- 코칭 상세페이지 중단 메뉴 -->
            <div class="coaching-menu">
              <DetailTopBar :menus="menus" @click="changeMenu"></DetailTopBar>
            </div>

            <!-- 코칭 소개. 직접 작성한 부분이 이곳에 들어감 -->
            <template v-if="currentMenu === 'introduce'">
              <div class="coaching-introduction">
                <h2>코칭 소개</h2>
                <div v-html="coachingDetail.htmlDocs" class="coaching-desc"></div>
              </div>
            </template>

            <!-- 라이브 코칭 목록 -->
            <template v-if="currentMenu === 'live'">
              <div class="coaching-live-schedule">
                <h2>라이브 일정</h2>
                <div class="coaching-live-calender">
                  <q-date
                    name="Schedule"
                    v-model="date"
                    color="blue-10"
                    today-btn
                    mask="YYYY-MM-DD"
                    :events="events"
                  ></q-date>
                  <CoachingScheduleList :timeTable="timeTable"></CoachingScheduleList>
                </div>
              </div>
            </template>

            <!-- 영상 목록 -->
            <template v-if="currentMenu === 'video'">
              <div class="coaching-video-list">
                <h2>영상 목록</h2>
                <div class="coaching-card-outside element-with-scrollbar">
                  <div v-if="videos.length > 0">
                    <div v-for="videoGroup in videos" :key="videoGroup.coachingName" class="coaching-card">
                      <CoachingCard
                        :label="videoGroup.name"
                        :video="videoGroup.url"
                        :ratio="16 / 9"
                      ></CoachingCard>
                    </div>
                  </div>
                  <div v-else class="coaching-card" style="font-size: 16px">조회 가능한 영상이 없습니다.</div>
                </div>
              </div>
            </template>

            <!-- 리뷰 -->
            <template v-if="currentMenu === 'review'">
              <div class="coaching-review">
                <h2>리뷰</h2>
                <Reviews
                  :reviews="reviews"
                  :rating-model="coachingDetail.reviewAvg"
                  v-bind:review-count="coachingDetail.reviewCount"
                  @review-data="reviewData"
                  @delete-review="deleteReview"
                  @update-review="updateReview"
                ></Reviews>
              </div>
            </template>
          </div>
        </div>
      </div>
      <!-- 우측 안내창 -->
      <div class="chat-box">
        <ChatBox
          :coach="coachingDetail.coachName"
          :coachingId="coachingLongId"
          :coachId="coachId"
          :profileImg="coachingDetail.coachProfileImageUrl"
        ></ChatBox>
      </div>

      <!-- 채팅 플로팅 버튼 -->
      <div class="chat-button">
        <q-btn round size="20px" color="amber-7" icon="chat" @click="openChatList()"></q-btn>
        <q-menu style="max-height: 400px; max-width: 400px">
          <DmList />
        </q-menu>
      </div>
      <q-layout
        v-if="useDmWindow === true"
        view="lHh Lpr lFf"
        container
        style="height: 400px"
        class="shadow-2 rounded-borders dm-window-container"
      >
        <q-page-container>
          <DmWindow />
        </q-page-container>
      </q-layout>
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
  margin: auto;
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
  position: fixed;
  top: 30vh;
  right: 15vw;
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
  padding-bottom: 1rem;
}

.coaching-desc {
  margin-left: 1.2vw;
  margin-top: 2vh;
  font-size: 16px;
  padding-bottom: 1rem;
}

.coaching-live-schedule {
  text-align: left;
  margin-bottom: 3vh;
}

.coaching-live-calender {
  display: flex;
  margin-left: 0.8vw;
  margin-bottom: 3vh;
  padding-bottom: 1rem;
}

.coaching-video-list {
  text-align: left;
  margin-bottom: 3vh;
  padding-bottom: 1rem;
}

.element-with-scrollbar {
  overflow: hidden;
}

.element-with-scrollbar:hover {
  overflow: auto;
}
.coaching-review {
  padding-bottom: 1rem;
}

.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}
.dm-window-container {
  position: fixed;
  bottom: 150px;
  right: 10vw;
  color: #fff;
  background-color: white;
  text-align: center;
  z-index: 7000;
  max-height: 600px;
  max-width: 300px;
  overflow: scroll;
}
</style>
