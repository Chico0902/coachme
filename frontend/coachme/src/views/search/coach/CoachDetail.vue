<script setup>
import navbar from '@/components/molecules/LoginNavBar.vue'
import CoachDetailCard from '@/components/molecules/CoachDetailCard.vue';
import ChatBox from '@/components/molecules/CoachChatBox.vue';
import DetailTopBar from '@/components/molecules/DetailTopBar.vue';
import ChatButton from '@/components/molecules/ChatButton.vue';
import Reviews from '@/components/molecules/ReviewDetailCard.vue';
import { ref } from 'vue'

const coachings = ["soccer", "soccer2", "football"]
// 제공 코칭 리스트

const menus = ref(['코치 소개', '제공 코칭', '리뷰'])
// 중단 메뉴 리스트

const name = ref("고코치")
const ratingModel = ref(4.3)
const review = ref(124)
const lastEdit = ref("2024. 01. 29")
// 코치 정보 예시

const reviews = ref([{
  name : "고양이",
  reviewDate : "2024/01/30 11:26 AM", 
  ratingModel : 4.5,
  review : "좋은 강의입니다."
}, {
  name : "고코미",
  reviewDate : "2024/01/30 02:50 PM", 
  ratingModel : 4.0,
  review : "마음에 들었습니다."
}])
// 리뷰 예시

const reviewData = (data) => {
  reviews.value.push({name : "옆동네 고양이", reviewDate: "2024/01/31 11:06 AM", ratingModel : data.rating,
  review : data.review})
  review.value = review.value + 1
} // 리뷰 입력폼에서 입력받은 리뷰와 별점을 처리하는 함수

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
            <CoachDetailCard :coach="name" :rating-model="ratingModel" :review-count="review" :last-edit-date="lastEdit"></CoachDetailCard>
            <q-separator></q-separator>
        
            <!-- 코치 포트폴리오 중단 메뉴 -->
            <div class="portfolio-menu">
              <DetailTopBar :menus="menus"></DetailTopBar>
            </div>

            <!-- 코치 소개. 직접 작성한 부분이 이곳에 들어감 -->
            <div class="coach-introduction">
              <h2>코치 소개</h2>
              <div class="coach-desc">
                소개합니다.
              </div>
            </div>

            <q-separator></q-separator>

            <!-- 제공 코칭 목록 -->
            <div class="coaching-category">
              <h2>제공 코칭</h2>
              <div style="margin-left: 0.8vw;">
                <q-chip icon="book" size="1.2rem" class="row no-wrap items-center" v-for="coaching in coachings" :key="coaching">
                  {{ coaching }}
                </q-chip> 
              </div>
            </div>

            <q-separator></q-separator>

            <!-- 리뷰 -->
            <div class="coach-review">
              <h2>리뷰</h2>
              <Reviews :reviews="reviews" :rating-model="ratingModel" v-bind:review-count="review"
              @review-data="reviewData"></Reviews>
            </div>
            
          </div>
        </div>
        <!-- 우측 안내창 -->
        <div class="chat-box">
          <ChatBox :coach="name"></ChatBox>
        </div>

        <!-- 채팅 플로팅 버튼 -->
        <div class="chat-button">
          <ChatButton style="width: 50px; height: 50px;">
          </ChatButton>
        </div>
      </div>
    </div>
  </div>

  <div class="footer">
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
.mainpage::-webkit-scrollbar{
  display:none;
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
  position:fixed;
  bottom:13vh;
  right:14vw;
  color:#FFF;
  text-align:center;
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
}

.coach-desc {
  margin-left: 1.1vw;
}

.coaching-category {
  text-align: left;
  margin-bottom: 3vh;
}

.coach-review {
  
}

.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}
</style>