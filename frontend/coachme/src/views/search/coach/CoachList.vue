<script setup>
import CoachCardList from '@/components/molecules/SearchCoachCardList.vue'
import DmWindow from '@/components/molecules/DmWindow.vue'
import CustomCategory from '@/components/molecules/CustomCategory.vue'
import navbar from '@/components/molecules/LoginNavBar.vue'
import SearchCategorySidebar from '@/components/molecules/SearchCategorySidebar.vue'
import SearchCoachList from '@/components/molecules/SearchCoachList.vue'
import InputForm from '@/components/molecules/InputForm.vue'
import DmList from '@/components/molecules/DmList.vue'
import footerBar from '@/components/molecules/CustomShortFooter.vue'
import { useCoachStore } from '@/stores/coach'
import { onBeforeMount, ref } from 'vue'
import { storeToRefs } from 'pinia'
import { useChatStore } from '@/stores/chat-status'
import { onBeforeRouteUpdate, useRoute, useRouter } from 'vue-router'
/**
 * VARIABLES
 */

// pinia
const coachStore = useCoachStore()
const chatStore = useChatStore()
const { selectedMainCategory, selectedSubCategory, subCategories } = storeToRefs(coachStore)
const { sideButtonList } = coachStore
const { useDmWindow } = storeToRefs(chatStore)

// for side button
const buttonList = ref([])

// for card <-> list
const isMatching = ref(false)

// for route param
const router = useRouter()
const route = useRoute()

/**
 * METHODS
 */

const { receiveCoachesByCategoryAndWord } = coachStore
const { openChatList } = chatStore

// 최초 로딩
onBeforeMount(() => {
  const category1 = route.params.category1
  const category2 = route.params.category2
  const keyword = route.params.keyword
  receiveCoachesByCategoryAndWord(category1, category2, keyword)
})

// router param으로 검색
onBeforeRouteUpdate((to) => {
  const category1 = to.params.category1
  const category2 = to.params.category2
  const keyword = to.params.keyword
  receiveCoachesByCategoryAndWord(category1, category2, keyword)
})

// 대분류 코치 조회
const clickCategory = (subCategoryIndex, mainCatagoryName) => {
  const upperCategory1 = mainCatagoryName.toLowerCase()
  router.push(`/search/coach/list/${upperCategory1}/all/all`)

  // 세부 카테고리 변경
  subCategories.value = sideButtonList[subCategoryIndex]
  buttonList.value = subCategories.value
}

// 소분류 코치 조회
const clickSubCategory = (subCategoryIndex) => {
  const upperCategory1 = selectedMainCategory.value.toLowerCase()
  const upperCategory2 = buttonList.value[subCategoryIndex].name.toLowerCase()
  router.push(`/search/coach/list/${upperCategory1}/${upperCategory2}/all`)
}

// 검색 코치조회
const searchByWords = (keyword) => {
  const upperCategory1 = selectedMainCategory.value.toLowerCase()
  const upperCategory2 = selectedSubCategory.value.toLowerCase()
  router.push(`/search/coach/list/${upperCategory1}/${upperCategory2}/${keyword.input}`)
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
        <SearchCategorySidebar :button-list="buttonList" @click-sub-category="clickSubCategory" />
        <div class="rightPage">
          <!-- 검색창 -->
          <div>
            <form @submit.prevent="inputData">
              <InputForm class="search" @inputData="searchByWords"></InputForm>
            </form>
          </div>
          <div class="mainpage">
            <!-- 코치 매칭 카드  -->
            <CoachCardList v-if="isMatching"></CoachCardList>
            <SearchCoachList v-else style="margin-top: 2vh; margin-left: 0.6vw"></SearchCoachList>
          </div>
        </div>

        <!-- 채팅 버튼 -->
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

        <!-- 전환 버튼 -->
        <div class="matching-button">
          <q-btn
            v-if="isMatching"
            round
            style="width: 50px; height: 50px"
            size="20px"
            color="blue-9"
            icon="list"
            @click="isMatching = false"
          >
            <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
              <strong>리스트로 보기</strong>
            </q-tooltip>
          </q-btn>
          <q-btn
            v-else
            round
            style="width: 50px; height: 50px"
            size="20px"
            color="blue-9"
            icon="style"
            @click="isMatching = true"
          >
            <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
              <strong>매칭 하기</strong>
            </q-tooltip>
          </q-btn>
        </div>
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
  max-height: 65vh;
}

.rightPage {
  background-color: white;
  width: 80%;
  margin-left: 4%;
  margin-top: 3vh;
  border-radius: 1.5rem;
  overflow: scroll;
  display: flex;
  text-align: center;
  flex-direction: column;
  -ms-overflow-style: none;
}
.rightPage::-webkit-scrollbar {
  display: none;
}

.search {
  margin-left: 1vw;
}

#input {
  width: 50vw;
}

.mainpage {
  background-color: white;
  width: 90%;
  /* max-height: 70vh; */
  margin-top: -1vh;
  overflow-x: hidden;
  overflow-y: scroll;
  display: flex;
  text-align: center;
  flex-direction: row;
  -ms-overflow-style: none;
}

.mainpage::-webkit-scrollbar {
  width: 10px;
  height: 0.5rem;
}

.mainpage::-webkit-scrollbar-thumb {
  background-color: #6593ff;
  border-radius: 1.5rem;
  min-width: 50px;
}

.mainpage::-webkit-scrollbar-thumb:hover {
  background-color: #3370ff;
}

.mainpage::-webkit-scrollbar-track {
  background-color: #c7c7c7;
  border-radius: 1.5rem;
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
.chat-button {
  position: fixed;
  bottom: 12vh;
  right: 10vw;
  color: #fff;
  text-align: center;
}

.matching-button {
  position: fixed;
  bottom: 12vh;
  right: 16vw;
  color: #fff;
  text-align: center;
}
</style>
