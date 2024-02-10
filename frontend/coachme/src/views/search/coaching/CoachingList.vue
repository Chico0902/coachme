<!-- 코치 리스트 페이지-->

<script setup>
import CustomCategory from '@/components/molecules/CustomCategory.vue'
import navbar from '@/components/molecules/LoginNavBar.vue'
import SearchCategorySidebar from '@/components/molecules/SearchCategorySidebar.vue'
import SearchCoachingList from '@/components/molecules/SearchCoachingList.vue'
import InputForm from '@/components/molecules/InputForm.vue'
import DmList from '@/components/molecules/DmList.vue'
import footerBar from '@/components/molecules/CustomShortFooter.vue'
import DmWindow from '@/components/molecules/DmWindow.vue'
import { useChatStore } from '@/stores/chat-status'
import { useAuthStore } from '@/stores/auth'
import { useCoachingStore } from '@/stores/coaching'
import { ref, onBeforeMount } from 'vue'
import { storeToRefs } from 'pinia'
import { postCoachingsByCategory } from '@/utils/api/coaching-api'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'

/**
 * VARIABLES
 */

const selectButton = ref(0)
const sideButtonList = [
  [],
  [{ name: 'ALL' }, { name: 'House' }, { name: 'Furniture' }, { name: 'Lifestyle' }, { name: 'Design' }],
  [{ name: 'ALL' }, { name: 'Cooking' }, { name: 'Knitting' }, { name: 'Art' }, { name: 'Beauty' }],
  [{ name: 'ALL' }, { name: 'Soccer' }, { name: 'Basketball' }, { name: 'Tennis' }, { name: 'Golf' }],
  [{ name: 'ALL' }, { name: 'Frontend' }, { name: 'Backend' }, { name: 'Database' }, { name: 'DevOps' }],
  [{ name: 'ALL' }, { name: 'Yoga' }, { name: 'Weight' }, { name: 'Running' }, { name: 'Crossfit' }]
]
const subCategories = ref([])
const selectedMainCategory = ref('all')
const selectedSubCategory = ref('all')

// pinia
const coachingStore = useCoachingStore()
const chatStore = useChatStore()
const authStore = useAuthStore()
const { isLogin } = storeToRefs(authStore)
const { coachings } = storeToRefs(coachingStore)
const { useDmWindow } = storeToRefs(chatStore)

// 로그인 여부
const loginMemberId = isLogin.value ? decodeToken(getAccessToken()).longId : -1

/**
 * METHODS
 */

const { openChatList } = chatStore

// 전체 코칭 조회
onBeforeMount(() => {
  postCoachingsByCategory(
    selectedMainCategory.value.toLowerCase(),
    selectedSubCategory.value.toLowerCase(),
    { words: 'all', loginMemberId },
    (success) => {
      console.log(success)
      coachings.value = success.data.list
      console.log(coachings.value)
    },
    (fail) => console.log(fail)
  )
})

// 대분류 코치 조회
const clickCategory = (index, name) => {
  console.log(index)
  console.log(name)
  selectButton.value = index
  subCategories.value = sideButtonList[selectButton.value]
  selectedMainCategory.value = name
  postCoachingsByCategory(
    selectedMainCategory.value.toLowerCase(),
    selectedSubCategory.value.toLowerCase(),
    { words: 'all', loginMemberId },
    (success) => {
      coachings.value = success.data.list
      console.log(success)
    },
    (fail) => console.log(fail)
  )
}

// 소분류 코치 조회
const clickSubCategory = (name) => {
  selectedSubCategory.value = name
  postCoachingsByCategory(
    selectedMainCategory.value.toLowerCase(),
    selectedSubCategory.value.toLowerCase(),
    { words: 'all', loginMemberId },
    (success) => {
      coachings.value = success.data.list
      console.log(success)
    },
    (fail) => console.log(fail)
  )
}

// 검색 코치조회
const searchByWords = (words) => {
  if (words === '') words = 'all'
  postCoachingsByCategory(
    selectedMainCategory.value.toLowerCase(),
    selectedSubCategory.value.toLowerCase(),
    { words: words.input, loginMemberId },
    (success) => {
      coachings.value = success.data.list
      console.log(success)
    },
    (fail) => console.log(fail)
  )
}
</script>
<template>
  <!-- nav -->
  <div class="nav-bar">
    <navbar />
  </div>
  <div class="all">
    <div class="main-layout">
      <!-- 카테고리 -->
      <CustomCategory style="margin-top: 3vh" @click-category="clickCategory"></CustomCategory>
      <div class="mypage-outside">
        <!-- 사이드메뉴 -->
        <SearchCategorySidebar :button-list="subCategories" @click-sub-category="clickSubCategory" />
        <div class="rightPage">
          <div>
            <InputForm class="search" @inputData="searchByWords"></InputForm>
          </div>
          <div class="mainpage">
            <!-- 코칭 목록과 채팅 버튼-->
            <SearchCoachingList :coaching="coachings" style="margin-left: 0.6vw; margin-top: 1vh"></SearchCoachingList>
          </div>
        </div>
        <div class="chat-button">
          <q-btn round size="20px" color="amber-7" icon="chat" @click="openChatList()"></q-btn>
          <!-- dm 영역 -->
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
  <!-- footer 위치 -->
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

.rightPage {
  background-color: white;
  width: 80%;
  height: 80vh;
  margin-left: 4%;
  margin-top: 3vh;
  border-radius: 1.5rem;
  overflow: scroll;
  display: flex;
  text-align: center;
  flex-direction: column;
  -ms-overflow-style: none;
}

.search {
  margin-left: 1vw;
}

#input {
  width: 50vw;
}

.mainpage {
  background-color: white;
  width: 100%;
  height: 70vh;
  margin-top: -1vh;
  overflow: scroll;
  display: flex;
  text-align: center;
  flex-direction: row;
  -ms-overflow-style: none;
}
.mainpage::-webkit-scrollbar {
  display: none;
}

.mainpage {
  -ms-overflow-style: none;
}
.mainpage::-webkit-scrollbar {
  display: none;
}

.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}

.chat-button {
  position: fixed;
  bottom: 60px;
  right: 14vw;
  color: #fff;
  text-align: center;
}
</style>
