<script setup>
import navbar from '@/components/molecules/LoginNavBar.vue'
import footerBar from '@/components/molecules/CustomShortFooter.vue'
import { decodeToken } from '@/utils/functions/auth'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'

/**
 * VARIABLES
 */

// 메인페이지 진입 시 권한 확인해서 side bar에 전달
const authStore = useAuthStore()
const { accessToken } = storeToRefs(authStore)
const privilege = decodeToken(accessToken.value).privilege

// 해당 페이지 떠날 때 경고메시지
</script>
<template>
  <div class="nav-bar">
    <navbar />
  </div>
  <div class="all">
    <div class="main-layout">
      <div class="mypage-outside">
        <RouterView name="sidebar" :privilege="privilege" />
        <div class="mainpage shadow-3">
          <RouterView name="main"></RouterView>
        </div>
      </div>
    </div>
  </div>
  <div class="footer"><footerBar /></div>
</template>

<style scoped>
@font-face {
  font-family: 'TheJamsil5Bold';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2302_01@1.0/TheJamsil5Bold.woff2') format('woff2');
  font-weight: 700;
  font-style: normal;
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
  height: 70vh;
  margin: auto;
  margin-top: 5vh;
  margin-bottom: 5vh;
  border-radius: 1.5rem;
  overflow-y: scroll;
  overflow-x: scroll;
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
</style>
