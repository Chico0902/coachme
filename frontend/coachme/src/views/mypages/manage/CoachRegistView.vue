<script setup>
import { getAccessToken, decodeToken } from '@/utils/functions/auth'
import navbar from '@/components/molecules/LoginNavBar.vue'
import MypageSidebar from '@/components/molecules/MypageSidebar.vue'
import { ref, onBeforeMount } from 'vue'
import router from '@/router'
import CustomInput from '@/components/atoms/CustomInput.vue'
import CustomButton from '@/components/atoms/CustomButton.vue'
const SideButtonList = ref([
  { name: '코치등록', link: '/mypage/coach/regist', cssClass: 'manage-button' },
  { name: '정보수정', link: '/mypage/profile' },
  { name: '코칭일정', link: '/mypage/coaching/coame' },
  { name: '관심강의', link: '/mypage/interest' },
  { name: '영상보기', link: '/mypage/video' },
  { name: '회원탈퇴', link: '/mypage/resign' }
])
const color = "#fcbf17"
const label = "등록신청"
const textcolor = "black"

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
        
          <div class="coach-register outside">
          <div class="coach-register maintitle">
            코치 등록
          </div>
          <div class="coach-register detail">
            코치 정보를 등록하고 나만의 코칭을 시작하세요!
          </div>
        </div>
        <div class="intropage outside">
          <div class="intropage title">
            소개글
          </div>
          <div class="intropage box">
            <CustomInput type = "textarea" placeholder="소개글을 입력해주세요."/>
          </div>
        </div>
        <div class="career outside">
          <div class="career title">
            이력
          </div>
          <div class="career box">
            <CustomInput type = "textarea" placeholder="이력을 입력해주세요."/>
          </div>
        </div>
        <div class="coaching-detail outside">
          <div class="coaching-detail title">
            코칭내용
          </div>
          <div class="coaching-detail box">
            <CustomInput type = "textarea" placeholder="코칭 내용을 입력해주세요."/>
          </div>
        </div>
        <div class="curriculum outside">
          <div class="curriculum title">
            커리큘럼
          </div>
          <div class="curriculum box">
            <CustomInput type = "textarea" placeholder="커리큘럼을 입력해주세요."/>
          </div>
          <div class="button-div">
            <div class="button">
              <CustomButton
                style="height: 56px"
                :label="label"
                :background="color"
                :color="textcolor"
                @click="searchData"
              ></CustomButton>
            </div>
          </div>
        </div>
        
        

</template>

<style scoped>

@font-face {
    font-family: 'TheJamsil5Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2302_01@1.0/TheJamsil5Bold.woff2') format('woff2');
    font-weight: 700;
    font-style: normal; 
}

.all {
  font-family: 'TheJamsil5Bold', sans-serif;
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
  overflow: scroll;
}

.outside{
  width: 80%;
  margin: 20px auto;
  text-align: left;
}

.maintitle {
  padding-left: 10px;
  display: inline-block;
  font-size: xx-large;
  margin: 50px 10px 20px;
}

.detail {
  font-size: x-large;
}
.title{
  padding-left: 10px;
  display: inline-block;
  font-size: large;
  margin: 20px 10px; 
}

.box{
  background-color: rgb(236, 236, 236); 
}
.button-div{ 
  display: flex;
  justify-content: center;
}
.button {
  margin: 50px;
}
.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}
</style>
