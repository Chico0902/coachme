<script setup>
import CoachCardList from '@/components/molecules/SearchCoachCardList.vue'
import chatButton from '@/components/molecules/ChatButton.vue'
import CustomCategory from '@/components/molecules/CustomCategory.vue'
import navbar from '@/components/molecules/LoginNavBar.vue'
import SearchCategorySidebar from '@/components/molecules/SearchCategorySidebar.vue'
import SearchCoachList from '@/components/molecules/SearchCoachList.vue'
import InputForm from '@/components/molecules/InputForm.vue'
import { getCoachesByCategory } from '@/utils/api/coach-api'
import { onBeforeMount, ref } from 'vue'
import { storeToRefs } from 'pinia'

/**
 * VARIABLES
 */

const selectButton = ref(0)
const SideButtonList = [
  [],
  [{ name: 'ALL' }, { name: 'House' }, { name: 'Furniture' }, { name: 'Lifestyle' }, { name: 'Design' }],
  [{ name: 'ALL' }, { name: 'Cooking' }, { name: 'Knitting' }, { name: 'Art' }, { name: 'Beauty' }],
  [{ name: 'ALL' }, { name: 'Soccer' }, { name: 'Basketball' }, { name: 'Tennis' }, { name: 'Golf' }],
  [{ name: 'ALL' }, { name: 'Frontend' }, { name: 'Backend' }, { name: 'Database' }, { name: 'DevOps' }],
  [{ name: 'ALL' }, { name: 'Yoga' }, { name: 'Weight' }, { name: 'Running' }, { name: 'Crossfit' }]
]
const subCategories = ref([])
const isMatching = ref(false)
const selectedMainCategory = ref('')

// pinia
const coachStore = useCoachStore()
const { coaches } = storeToRefs(coachStore)

/**
 * METHODS
 */

const changeListAndMatching = () => {
  isMatching.value = !isMatching.value
}

// 전체 코치 조회
onBeforeMount(() => {
  getCoachesByCategory(
    'all',
    'all',
    (success) => {
      coaches.value = success.data.list
      console.log(coaches.value)
    },
    (fail) => console.log(fail)
  )
})

// 대분류 코치 조회
const clickCategory = (index, name) => {
  selectButton.value = index
  subCategories.value = SideButtonList[selectButton.value]
  selectedMainCategory.value = name
  getCoachesByCategory(
    name.toLowerCase(),
    'all',
    (success) => {
      coaches.value = success.data.list
      console.log(success)
    },
    (fail) => console.log(fail)
  )
}

// 소분류 코치 조회
const clickSubCategory = (name) => {
  getCoachesByCategory(
    selectedMainCategory.value.toLowerCase(),
    name.toLowerCase(),
    (success) => {
      coaches.value = success.data.list
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
          <!-- 검색창 -->
          <div>
            <InputForm class="search" :background="bColor"></InputForm>
          </div>
          <div class="mainpage">
            <!-- 코치 매칭 카드  -->
            <CoachCardList v-if="isMatching" :cards="coaches"></CoachCardList>
            <SearchCoachList v-else :coach="coaches" style="margin-top: 2vh; margin-left: 0.6vw"></SearchCoachList>
          </div>
        </div>
        <!-- 채팅 버튼 -->
        <div class="chat-button">
          <chatButton style="width: 50px; height: 50px"> </chatButton>
        </div>

        <!-- 전환 버튼 -->
        <div class="matching-button">
          <q-btn v-if="isMatching" round style="width: 50px; height: 50px" size="20px" color="blue-9" icon="list"
            @click="changeListAndMatching">
            <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
              <strong>리스트로 보기</strong>
            </q-tooltip>
          </q-btn>
          <q-btn v-else round style="width: 50px; height: 50px" size="20px" color="blue-9" icon="style"
            @click="changeListAndMatching">
            <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
              <strong>매칭 하기</strong>
            </q-tooltip>
          </q-btn>
        </div>

        <!-- 전환 버튼 -->
        <div class="matching-button">
          <q-btn v-if="isMatching" round style="width: 50px; height: 50px;" size="20px" color="blue-9" icon="list"
            @click="changeListAndMatching">
            <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
              <strong>리스트로 보기</strong>
            </q-tooltip>
          </q-btn>
          <q-btn v-else round style="width: 50px; height: 50px;" size="20px" color="blue-9" icon="style"
            @click="changeListAndMatching">
            <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
              <strong>매칭 하기</strong>
            </q-tooltip>
          </q-btn>
        </div>

        <!-- 전환 버튼 -->
        <div class="matching-button">
          <q-btn v-if="isMatching" round style="width: 50px; height: 50px;" size="20px" color="blue-9" icon="list"
            @click="changeListAndMatching">
            <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
              <strong>리스트로 보기</strong>
            </q-tooltip>
          </q-btn>
          <q-btn v-else round style="width: 50px; height: 50px;" size="20px" color="blue-9" icon="style"
            @click="changeListAndMatching">
            <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
              <strong>매칭 하기</strong>
            </q-tooltip>
          </q-btn>
        </div>
      </div>
    </div>
  </div>
  <!-- footer 위치 -->
  <div class="footer"></div>
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
  height: 75vh;
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
  min-width: 450px;
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

.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}

.chat-button {
  position: fixed;
  bottom: 60px;
  right: 10vw;
  color: #fff;
  text-align: center;
}

.matching-button {
  position: fixed;
  bottom: 60px;
  right: 16vw;
  color: #fff;
  text-align: center;
}
</style>
