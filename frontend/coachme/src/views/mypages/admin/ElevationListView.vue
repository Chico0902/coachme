<script setup>
import navbar from '@/components/molecules/LoginNavBar.vue'
import footerBar from '@/components/molecules/CustomShortFooter.vue'
import MypageSidebar from '@/components/molecules/MypageSidebar.vue'
const SideButtonList = [
  { name: '회원목록', link: '/mypage/admin/list', cssClass: 'manage-button' },
  { name: '권한요청', link: '/mypage/admin/elevations', cssClass: 'manage-button' }
]
</script>
<template>
  <div class="nav-bar">
    <navbar />
  </div>
  <div class="all">
    <div class="main-layout">
      <div class="mypage-outside">
        <MypageSidebar :button-list="SideButtonList" />
        <div class="mainpage shadow-3">메인</div>
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
}
.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}
</style>
