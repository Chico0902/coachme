<script setup>
import navbar from '../components/molecules/NavBar.vue'
import LoginNavBar from '@/components/molecules/LoginNavBar.vue'
import buttons from '../components/atoms/CustomButton.vue'
import carousel from '../components/atoms/CustomCarousel.vue'
import search from '../components/molecules/MainSearch.vue'
import category from '../components/molecules/CustomCategory.vue'
import card from '../components/molecules/CoachCard.vue'
import coaching from '../components/molecules/CoachingCard.vue'
import footerBar from '../components/molecules/CustomFooter.vue'
import MainSearchTitle from '../components/texts/MainSearchTitle.vue'
import MainCategoryTitle from '../components/texts/MainCategoryTitle.vue'
import MainCoachTitle from '../components/texts/MainCoachTitle.vue'
import MainCoachingTitle from '../components/texts/MainCoachingTitle.vue'
import profile from '../components/atoms/ProfileImage.vue'
import { ref, onMounted,onUnmounted, computed } from 'vue'
import { useMemberStore } from '@/stores/member'
import { useAuthStore } from '../stores/auth'
import { storeToRefs } from 'pinia'
import { decodeToken } from '@/utils/functions/auth'


/**
 * VARIABLES
 */

// 검색 컴포넌트의 버튼 색상, 드롭다운 색상, 버튼 라벨, 드롭다운 메뉴 순
const bColor = '#FCBF17'
const dColor = 'blue-10'
const label0 = '검색'
const list = ['코치명', '코칭제목']

// 회원정보 조회
const authStore = useAuthStore()
const memberStore = useMemberStore()
const { accessToken } = storeToRefs(authStore)
const { profileText, profileImageUrl } = storeToRefs(memberStore)
const username = computed(() => {
  console.log(decodeToken(accessToken.value))
  if (accessToken.value != '') return decodeToken(accessToken.value).name
  else return ''
})

// 코치(+코칭) 카드 라벨, 카드 캡션, 카드 이미지
const label = 'whiteCat'
const caption = 'Cat is white'
const image = 'https://velog.velcdn.com/images/lee02g29/post/d28f0459-139c-418c-bde4-b8c0b7ce33c8/image.png'

// 코칭 카드 영상 비율, 주소
const ratio = 16 / 9
const video = 'https://www.youtube.com/embed/k3_tw44QsZQ?rel=0'

/**
 * METHODS
 */

// 로그아웃
const logoutWithConfirm = () => {
  if (!confirm('로그아웃 하시겠습니까?')) return
  accessToken.value = ''
  profileText.value = '프로필을 등록하세요.'
  profileImageUrl.value = '/src/assets/icons/coame.png'
  alert('로그아웃 되었습니다.')
  window.location.reload()
}

const screenWidth = ref(window.innerWidth);


const updateScreenWidth = () => {
  screenWidth.value = window.innerWidth
}

onMounted(() => {
  window.addEventListener('resize', updateScreenWidth)
})

onUnmounted(() => {
  window.removeEventListener('resize', updateScreenWidth)
})

</script>
<template>
  <div v-if="screenWidth < 768" class="nav-bar">
    <LoginNavBar />
  </div>
  <div v-else class="nav-bar">
    <template v-if="accessToken === ''">
      <navbar>
        <template #search-coach>
          <RouterLink :to="{ name: 'Desktop-13' }">
            <buttons flat :name="`search-coach`" :label="`코치찾기`"></buttons>
          </RouterLink>
        </template>
        <template #search-coaching>
          <RouterLink :to="{ name: 'Desktop-14' }">
            <buttons flat :name="`search-coaching`" :label="`코칭찾기`"></buttons>
          </RouterLink>
        </template>
        <template #live>
          <RouterLink :to="{ name: 'Desktop-17-1' }">
            <buttons flat :name="`live`" :label="`강의장`"></buttons>
          </RouterLink>
        </template>
        <template #login>
          <RouterLink to="/login">
            <buttons flat :name="`login`" :label="`로그인`"></buttons>
          </RouterLink>
        </template>
        <template #regist>
          <RouterLink to="/regist">
            <buttons flat :name="`regist`" :label="`회원가입`"></buttons>
          </RouterLink>
        </template>
      </navbar>
    </template>
    <template v-else>
      <navbar>
        <template #search-coach>
          <RouterLink :to="{ name: 'Desktop-13' }">
            <buttons flat :name="`search-coach`" :label="`코치찾기`"></buttons>
          </RouterLink>
        </template>
        <template #search-coaching>
          <RouterLink :to="{ name: 'Desktop-14' }">
            <buttons flat :name="`search-coaching`" :label="`코칭찾기`"></buttons>
          </RouterLink>
        </template>
        <template #live>
          <RouterLink :to="{ name: 'Desktop-16' }">
            <buttons flat :name="`live`" :label="`강의장`"></buttons>
          </RouterLink>
        </template>
        <template #welcome>
          <div class="welcome">
            <p>{{ username }}님 환영합니다!</p>
            <RouterLink :to="{ path: `/mypage`, props: { accessToken } }">
              <q-btn flat>
                <profile :img="profileImageUrl"></profile>
              </q-btn>
            </RouterLink>
          </div>
        </template>
        <template #logout>
          <buttons flat :name="`logout`" :label="`로그아웃`" @click="logoutWithConfirm"></buttons>
        </template>
      </navbar>
    </template>
  </div>
  <div class="all">
    <div class="main-layout">
      <div class="carousel-outside">
        <div class="carousel">
          <carousel> </carousel>
        </div>
      </div>
      <div class="search-outside">
        <div class="search-title"><MainSearchTitle /></div>
        <div class="search">
          <search :label="label0" :dColor="dColor" :bColor="bColor" :list="list"></search>
        </div>
      </div>
      <div class="category-outside">
        <div class="category-title"><MainCategoryTitle /></div>
        <div class="category-imoji">
          <category></category>
        </div>
      </div>
      <div class="coach-outside">
        <div class="coach-title"><MainCoachTitle /></div>
        <div class="coach-card-outside">
          <div class="coach-card">
            <card :label="label" :caption="caption" :img="image"></card>
          </div>
          <div class="coach-card"><card :label="label" :caption="caption" :img="image"></card></div>
          <div class="coach-card"><card :label="label" :caption="caption" :img="image"></card></div>
        </div>
      </div>

      <div class="coaching-outside">
        <div class="coaching-title"><MainCoachingTitle /></div>
        <div class="coaching-card-outside">
          <div class="coaching-card">
            <coaching :label="label" :caption="caption" :ratio="ratio" :video="video"></coaching>
          </div>
          <div class="coaching-card">
            <coaching :label="label" :caption="caption" :ratio="ratio" :video="video"></coaching>
          </div>
          <div class="coaching-card">
            <coaching :label="label" :caption="caption" :ratio="ratio" :video="video"></coaching>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="footer"><footerBar /></div>
</template>

<style scoped>

.all {
  display: flex;
  justify-content: center;
}
.main-layout {
  width: 1280px;
  min-height: 100vh;
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
}
.nav-bar {
  position: sticky;
  top: 0;
  z-index: 128;
  background-color: white;
  margin-bottom: 5vh;
  display: flex;
  justify-content: center;
  
}
.welcome {
  display: flex;
  align-items: center;
}
.welcome p {
  margin: 0;
}
.carousel-outside {
  display: flex;
  justify-content: center;
}
.carousel {
  width: 70%;
  margin-bottom: 5vh;
  flex-shrink: 0;
  background: #fff;
  justify-content: center;
  text-align: center;
  min-width: 50rem;
}

.search-outside {
  display: flex;
  justify-content: center;
  flex-direction: column;
  margin: 10vh auto;
}
.search-title {
  margin: auto;
  width: 70%;
}
.search {
  margin: auto;
  margin-top: 5vh;
}
.category-outside {
  display: flex;
  justify-content: center;
  flex-direction: column;
  margin: 10vh auto;
}
.category-imoji {
  margin: 10vh auto;
  display: flex;
  justify-content: center;
  width: 100%;
}
.category-title {
  margin: 5vh auto;
  width: 70%;
}
.coach-card-outside {
  display: flex;
  justify-content: space-around;
  width: 80%;
}

.coach-outside {
  height: 30vh;
  margin: 20vh auto;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}
.coach-card {
  width: 200px;
  height: 200px;
  margin: 30px;
}
.coach-title {
  height: 70px;
  margin: 5vh auto;
  width: 70%;
}
.coaching-card-outside {
  display: flex;
  justify-content: space-around;
  width: 80%;

}
.coaching-outside {
  height: 30vh;
  margin: 30vh auto;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}
.coaching-card {
  width: 200px;
  height: 200px;
  margin: 30px;
}
.coaching-title {
  height: 70px;
  width: 70%;
  margin: 5vh auto;
}
.footer {
  margin-top: 30vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}

</style>
