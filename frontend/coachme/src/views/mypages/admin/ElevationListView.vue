<script setup>
import navbar from '@/components/molecules/LoginNavBar.vue'
import footerBar from '@/components/molecules/CustomShortFooter.vue'
<<<<<<< HEAD
import router from '@/router'
=======
import MypageSidebar from '@/components/molecules/MypageSidebar.vue'
const SideButtonList = [
  { name: '회원목록', link: '/mypage/admin/list', cssClass: 'manage-button' },
  { name: '권한요청', link: '/mypage/admin/elevations', cssClass: 'manage-button' }
]
>>>>>>> feature/Mypage-중복코드-제거
</script>
<template>
  <div class="nav-bar">
    <navbar />
  </div>
  <div class="all">
    <div class="main-layout">
      <div class="mypage-outside">
<<<<<<< HEAD
        <div class="sidebar">
          <div class="sidebar-button shadow-3" @click="router.push('/mypage/admin/list')"><p>회원목록</p></div>
          <div class="sidebar-button shadow-3"><p>권한요청</p></div>
        </div>
=======
        <MypageSidebar :button-list="SideButtonList" />
>>>>>>> feature/Mypage-중복코드-제거
        <div class="mainpage shadow-3">메인</div>
      </div>
    </div>
  </div>
  <div class="footer"><footerBar /></div>
</template>

<style scoped>
<<<<<<< HEAD
p {
  margin: 0;
}
=======
>>>>>>> feature/Mypage-중복코드-제거
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
<<<<<<< HEAD

=======
>>>>>>> feature/Mypage-중복코드-제거
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
<<<<<<< HEAD
.sidebar {
  width: 10%;
  height: 70vh;
  margin: auto;
}

.sidebar-button {
  width: 100%;
  height: 8vh;
  border-radius: 1rem;
  margin-top: 0;
  margin-bottom: 1.5rem;
  display: flex;
  justify-content: center;
  align-items: center;
}
.sidebar-button p {
  font-size: 1rem;
  letter-spacing: 0.1rem;
}
.sidebar-button:nth-child(2) {
  background-color: #bd58d9;
  opacity: 90%;
  color: #fff;
}
.sidebar-button:hover {
  background-color: #bd58d9;
  opacity: 100%;
  color: #fff;
  cursor: pointer;
}
.mainpage {
  background-color: white;
  width: 85%;
=======
.mainpage {
  background-color: white;
  width: 80%;
>>>>>>> feature/Mypage-중복코드-제거
  height: 70vh;
  margin: auto;
  margin-top: 5vh;
  margin-bottom: 5vh;
  border-radius: 1.5rem;
<<<<<<< HEAD
  display: flex;
  flex-direction: column;
  overflow: scroll;
=======
>>>>>>> feature/Mypage-중복코드-제거
}
.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}
</style>
