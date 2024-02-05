<!-- 코치 리스트 페이지-->

<script setup>
import CoachCardList from '@/components/molecules/SearchCoachCardList.vue'
import chatButton from '@/components/molecules/ChatButton.vue'
import CustomCategory from '@/components/molecules/CustomCategory.vue'
import navbar from '@/components/molecules/LoginNavBar.vue'
import SearchCategorySidebar from '@/components/molecules/SearchCategorySidebar.vue'
import SearchCoachList from '@/components/molecules/SearchCoachList.vue'

import { ref, reactive } from 'vue'

const selectButton = ref(0)
// 선택한 카테고리 index

const SideButtonList = [[
  { name: 'House', },
  { name: 'Furniture' },
  { name: 'Lifestyle' },
  { name: 'Design' },
  { name: 'Etc' },
], [
  { name: 'Cocking' },
  { name: 'Knitting' },
  { name: 'Art' },
  { name: 'Beauty' },
  { name: 'Etc' },
], [
  { name: 'Soccer' },
  { name: 'Basketball' },
  { name: 'Tennis' },
  { name: 'Golf' },
  { name: 'Etc' },
], [
  { name: 'Frontend' },
  { name: 'Backend' },
  { name: 'Database' },
  { name: 'Devops' },
  { name: 'Etc' },
], [
  { name: 'Yoga' },
  { name: 'Weight' },
  { name: 'Running' },
  { name: 'Crossfit' },
  { name: 'Etc' },
],]
// 선택한 카테고리에 따라 변경될 사이드 메뉴 리스트, 소뷴류

const selectedCategory = ref([])
// 선택된 상단 메뉴 리스트. props로 전달되는 리스트

const clickCategory = (index) => {
  selectButton.value = index
  selectedCategory.value = SideButtonList[selectButton.value]
}
// 카테고리 클릭시 상단 사이드 메뉴 변경

const isMatching = ref(false)

const changeListAndMatching = () => {
  isMatching.value = !isMatching.value
}

const coach = reactive([
  {
    coachId: "1",
    coachName: "title one",
    coaching: 'one',
    rating: "4.7",
    reviewCount: 122,
    img: "https://cdn.quasar.dev/img/avatar1.jpg"
  }, {
    coachId: "2",
    coachName: "title two",
    coaching: 'two',
    rating: "4.6",
    reviewCount: 122,
    img: "https://cdn.quasar.dev/img/avatar2.jpg"
  }, {
    coachId: "3",
    coachName: "title three",
    coaching: 'three',
    rating: "4.5",
    reviewCount: 122,
    img: "https://cdn.quasar.dev/img/avatar3.jpg"
  }, {
    coachId: "4",
    coachName: "title four",
    coaching: 'four',
    rating: "3.9",
    reviewCount: 122,
    img: "https://cdn.quasar.dev/img/avatar4.jpg"
  }, {
    coachId: "5",
    coachName: "title five",
    coaching: 'five',
    rating: "4",
    reviewCount: 122,
    img: "https://cdn.quasar.dev/img/avatar5.jpg"
  }, {
    coachId: "6",
    coachName: "title six",
    coaching: 'six',
    rating: "4.9",
    reviewCount: 122,
    img: "https://cdn.quasar.dev/img/avatar6.jpg"
  }, {
    coachId: "7",
    coachName: "title seven",
    coaching: 'seven',
    rating: "4.1",
    reviewCount: 122,
    img: "https://cdn.quasar.dev/img/avatar1.jpg"
  }
]) // 코치 목록 예시

</script>
<template>
  <!-- nav -->
  <div class="nav-bar">
    <navbar />
  </div>
  <div class="all">
    <div class="main-layout">
      <!-- 카테고리 -->
      <CustomCategory style="margin-top: 3vh;" @click-category="clickCategory"></CustomCategory>
      <div class="mypage-outside">
        <!-- 사이드메뉴 -->
        <SearchCategorySidebar :button-list="selectedCategory" />
        <div class="mainpage">
          <!-- 코치 매칭 카드  -->
          <CoachCardList v-if="isMatching" :cards="coach" style="margin-left: 7vw;"></CoachCardList>
          <SearchCoachList v-else :coach="coach" style="margin-top: 2vh; margin-left: 0.6vw;"></SearchCoachList>
        </div>
        <!-- 채팅 버튼 -->
        <div class="chat-button">
          <chatButton style="width: 50px; height: 50px;">
          </chatButton>
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
  margin-top: 2vh;
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
  color: #FFF;
  text-align: center;
}

.matching-button {
  position: fixed;
  bottom: 60px;
  right: 16vw;
  color: #FFF;
  text-align: center;
}
</style>


