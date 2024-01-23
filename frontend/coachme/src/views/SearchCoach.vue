<script setup>
import chatButton from '../components/molecules/ChatButton.vue'
import CustomCategory from '@/components/molecules/CustomCategory.vue';
import navbar from '@/components/molecules/LoginNavBar.vue'
import MypageSidebar from '@/components/molecules/MypageSidebar.vue'
import { ref } from 'vue'

const SideButtonList = ref([
  { name: '코치등록' },
  { name: '정보수정' },
  { name: '코칭일정' },
  { name: '관심강의' },
  { name: '영상보기' },
  { name: '회원탈퇴' }
])

</script>

<template>
  <div class="nav-bar">
    <navbar />
  </div>
  <div class="all">
    <div class="main-layout">
      <CustomCategory style="margin-top: 3vh;"></CustomCategory>
      <div class="mypage-outside">
        <MypageSidebar :button-list="SideButtonList" />
        <div class="mainpage">
          <chatButton style="margin-left: 60vw; margin-top: 55vh; width: 50px; height: 50px;" >
          </chatButton>
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
  display: flex;
  text-align: center;
  flex-direction: column;
}

.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}
</style>
