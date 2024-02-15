<script setup>
import navbar from '@/components/molecules/LoginNavBar.vue'
import CoachDetailCard from '@/components/molecules/CoachDetailCard.vue'
import ChatBox from '@/components/molecules/CoachChatBox.vue'
import DetailTopBar from '@/components/molecules/DetailTopBar.vue'
import Reviews from '@/components/molecules/ReviewDetailCard.vue'
import footerBar from '@/components/molecules/CustomShortFooter.vue'
import router from '@/router'
import Swal from 'sweetalert2'
import DmWindow from '@/components/molecules/DmWindow.vue'
import DmList from '@/components/molecules/DmList.vue'
import { ref, onBeforeMount } from 'vue'
import { useRoute } from 'vue-router'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import { getCoachDetailPage } from '@/utils/api/coach-api'
import { getCoachReview, postcoachReview, deleteMyReview, patchMyReview } from '@/utils/api/review-api'
import { useAuthStore } from '@/stores/auth'
import { useChatStore } from '@/stores/chat-status'
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
const coachId = ref()
const myLongId = ref()
const coachDetail = ref([]) // 코치 상세
const reviews = ref([]) // 리뷰
const menus = ref(['코치 소개', '제공 코칭', '리뷰'])
const currentMenu = ref('introduce')

// 코칭 상세보기
const show = ref(false)
const coachingMainCategory = ref('')
const coachingName = ref('')
const coachingSubCategory = ref('')
const coachingSummary = ref('')
const coachingId = ref('')

/**
 * METHODS
 */

// 리뷰 작성
const reviewData = (data) => {
  myLongId.value = decodeToken(getAccessToken()).longId

  const dto = {
    coameId: myLongId.value,
    coachId: coachId.value,
    comment: data.review,
    score: data.rating
  } // 리뷰 dto

  new Promise((resolve, reject) =>
    // 리뷰 작성
    postcoachReview(
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
    // 리뷰 작성 후 정보들 다시 리로드

    // 코치 상세 정보
    getCoachDetailPage(
      coachId.value,
      (success) => {
        coachDetail.value = success.data
      },
      (fail) => {
        console.error(fail)
      }
    ),
      // 코치 리뷰
      getCoachReview(
        coachId.value,
        (success) => {
          reviews.value = success.data.list
        },
        (fail) => {
          console.error(fail)
        }
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
    getCoachDetailPage(
      coachId.value,
      (success) => {
        coachDetail.value = success.data
      },
      (fail) => {
        console.error(fail)
      }
    ),
      // 코치 리뷰
      getCoachReview(
        coachId.value,
        (success) => {
          reviews.value = success.data.list
        },
        (fail) => {
          console.error(fail)
        }
      )
  })
}

// 리뷰 수정
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
    // 리뷰 작성 후 정보들 다시 리로드

    // 코치 상세 정보
    getCoachDetailPage(
      coachId.value,
      (success) => {
        coachDetail.value = success.data
      },
      (fail) => {
        console.error(fail)
      }
    ),
      // 코치 리뷰
      getCoachReview(
        coachId.value,
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

  const coachLongId = route.params.id
  coachId.value = coachLongId

  // 코치 id로 코치 포트폴리오
  getCoachDetailPage(
    coachLongId,
    (success) => {
      coachDetail.value = success.data
    },
    (fail) => {
      console.error(fail)
    }
  )

  // 코칭 id로 코칭 리뷰
  getCoachReview(
    coachLongId,
    (success) => {
      reviews.value = success.data.list
    },
    (fail) => {
      console.error(fail)
    }
  )
})

// 클릭한 버튼에 따라 메뉴구성 변경
const changeMenu = (e) => {
  switch (e.srcElement.innerText) {
    case '코치 소개':
      currentMenu.value = 'introduce'
      break
    case '제공 코칭':
      currentMenu.value = 'coachings'
      break
    case '리뷰':
      currentMenu.value = 'review'
      break
  }
}

// 코칭 보여주기
const showCoaching = (mainCategory, name, subCategory, summary, id) => {
  coachingMainCategory.value = mainCategory
  coachingName.value = name
  coachingSubCategory.value = subCategory
  coachingSummary.value = summary
  coachingId.value = id
  show.value = true
}
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
            <!-- 코치 상세 정보 -->
            <CoachDetailCard
              :coach="coachDetail.coachName"
              :rating-model="coachDetail.reviewAvg"
              :review-count="coachDetail.reviewCount"
              :img="coachDetail.coachProfileImageUrl"
            ></CoachDetailCard>
            <q-separator></q-separator>

            <!-- 코치 포트폴리오 중단 메뉴 -->
            <div class="portfolio-menu">
              <DetailTopBar :menus="menus" @click="changeMenu"></DetailTopBar>
            </div>

            <!-- 코치 소개. 직접 작성한 부분이 이곳에 들어감 -->
            <template v-if="currentMenu === 'introduce'">
              <div class="coach-introduction">
                <h2>코치 소개</h2>
                <div v-html="coachDetail.portFolioHtmlDocs" class="coach-desc"></div>
              </div>
            </template>

            <!-- 제공 코칭 목록 -->
            <template v-if="currentMenu === 'coachings'">
              <div class="coaching-category">
                <h2>제공 코칭</h2>
                <div style="margin-left: 0.8vw">
                  <q-chip
                    icon="book"
                    size="1.2rem"
                    class="row no-wrap items-center"
                    v-for="coaching in coachDetail.list"
                    :key="coaching"
                  >
                    <div
                      class="coaching-category-detail"
                      @click.prevent="
                        showCoaching(
                          coaching.coachingMainCategory,
                          coaching.coachingName,
                          coaching.coachingSubCategory,
                          coaching.coachingSummary,
                          coaching.coachingId
                        )
                      "
                    >
                      {{ coaching.coachingName }}
                    </div>
                  </q-chip>
                </div>
              </div>
            </template>

            <!-- 리뷰 -->
            <template v-if="currentMenu === 'review'">
              <div class="coach-review">
                <h2>리뷰</h2>
                <Reviews
                  :reviews="reviews"
                  :rating-model="coachDetail.reviewAvg"
                  v-bind:review-count="coachDetail.reviewCount"
                  @review-data="reviewData"
                  @delete-review="deleteReview"
                  @update-review="updateReview"
                >
                </Reviews>
              </div>
            </template>
          </div>
        </div>
        <!-- 우측 안내창 -->
        <div class="chat-box">
          <ChatBox
            :coach="coachDetail.coachName"
            :coachId="coachId"
            :profileImg="coachDetail.coachProfileImageUrl"
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
  </div>

  <div class="footer">
    <footerBar />
  </div>

  <q-dialog v-model="show">
    <q-card>
      <q-card-section class="bg-primary text-white">
        <q-item>
          <q-item-section>
            <q-item-label class="coaching-name">{{ coachingName }}</q-item-label>
            <q-item-label caption class="coaching-detail">{{ coachingSummary }}</q-item-label>
          </q-item-section>
        </q-item>
      </q-card-section>
      <q-separator></q-separator>
      <q-card-section class="bg-amber-5" style="text-align: center">
        <span class="coaching-caption">
          {{ coachingMainCategory.replace(/^[a-z]/, (char) => char.toUpperCase()) }}</span
        >
        <span class="coaching-caption-small">
          {{ coachingSubCategory.replace(/^[a-z]/, (char) => char.toUpperCase()) }}</span
        >
      </q-card-section>
      <q-separator></q-separator>
      <q-card-actions class="modal-option" align="right">
        <q-btn flat label="상세보기" color="primary" @click="router.push(`/search/coaching/detail/${coachingId}`)" />
        <q-btn flat label="취소" color="primary" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
.coaching-category-detail:hover {
  cursor: pointer;
}
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

.coach-introduction {
  text-align: left;
  margin-bottom: 4vh;
  padding-bottom: 1rem;
}

.coach-desc {
  margin-left: 1.1vw;
  margin-top: 2vh;
  font-size: 16px;
  padding-bottom: 1rem;
}

.coaching-category {
  text-align: left;
  margin-bottom: 3vh;
}

.coach-review {
  padding-bottom: 1rem;
}

.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}

.coaching-name {
  margin: 0;
  margin-bottom: 0.3rem;
  font-size: 1.3rem;
  font-family: 'TheJamsil5Bold';
}
.coaching-detail {
  font-size: 0.9rem;
  margin-bottom: 0.3rem;
  margin-right: 0.5rem;
  font-family: 'TheJamsil5Bold';
  color: #c7c183;
}
.coaching-caption {
  font-size: 1.1rem;
  margin-bottom: 0.3rem;
  margin-right: 1rem;
  font-family: 'TheJamsil5Bold';
}
.coaching-caption-small {
  margin: 0;
  font-size: 0.8rem;
  color: rgb(126, 126, 126);
  font-family: 'TheJamsil5Bold';
}
.modal-option {
  font-family: 'TheJamsil5Bold';
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
