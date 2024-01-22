<script setup>
import { getAccessToken, decodeToken } from '@/utils/functions/auth'
import navbar from '@/components/molecules/LoginNavBar.vue'
import MypageSidebar from '@/components/molecules/MypageSidebar.vue'
import { ref, onBeforeMount } from 'vue'
import router from '@/router'
const SideButtonList = ref([
  { name: '코치등록', link: '/mypage/coach/regist', cssClass: 'manage-button' },
  { name: '정보수정', link: '/mypage/profile' },
  { name: '코칭일정', link: '/mypage/coaching/coame' },
  { name: '관심강의', link: '/mypage/interest', cssClass: 'selected-button' },
  { name: '영상보기', link: '/mypage/video' },
  { name: '회원탈퇴', link: '/mypage/resign' }
])

// 코치, 코미인지 확인해서 버튼 바꾸기
onBeforeMount(() => {
  try {
    switch (decodeToken(getAccessToken()).privilege) {
      case 'COAME':
        break
      case 'COACH':
        SideButtonList.value[0] = {
          name: '코치관리',
          link: '/mypage/coach/manage/portfolio',
          cssClass: 'manage-button'
        }
        break
      default:
        // Exception : 권한 형식이 잘못되었을 경우
        throw new Error('잘못된 권한 형식입니다.')
    }
  } catch (e) {
    alert(e.message + ' 로그인 페이지로 이동합니다.')
    router.push('/login')
  }
})
</script>
<template>
  <div class="nav-bar">
    <navbar />
  </div>
  <div class="all">
    <div class="main-layout">
      <div class="mypage-outside">
        <MypageSidebar :button-list="SideButtonList" />
        <div class="mainpage shadow-3">
          <h1>Interest View</h1>
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
