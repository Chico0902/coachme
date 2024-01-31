<script setup>
import MypageTopBar from '@/components/molecules/MypageTopBar.vue'
import ProfileImage from '@/components/atoms/ProfileImage.vue'
import CustomInput from '@/components/atoms/CustomInput.vue'

const TopButtonList = [
  { name: '포트폴리오', link: '/mypage/coach/manage/portfolio' },
  { name: '코칭관리', link: '/mypage/coach/manage/coaching', cssClass: 'selected-button' },
  { name: '영상관리', link: '/mypage/coach/manage/video' }
]
</script>
<template>
  <MypageTopBar :button-list="TopButtonList" />
  <div class="outside">
    <div class="portfolio-layout">
      <div class="intro">
        <div class="intro-coach title">코치 소개</div>
        <div class="intro-box">
          <div class="intro-img">
            <ProfileImage size="18vh" />
          </div>
          <div class="intro-detail">
            <CustomInput type="textarea" placeholder="소개글을 입력해주세요." style="width: 60vh" />
          </div>
        </div>
      </div>
      <div class="coaching-list-outside">
      <div class="coaching-list-top">
        <div class="intro-coach title">개설한 강의</div>
        <div class="menu SMN_effect-42">
          <RouterLink :to="{name:'Desktop-5-4'}"><span data-hover="코칭만들기">코칭만들기</span></RouterLink>
        </div>
      </div>
        <div class="coaching-list-main">
          <div class="coaching-list list">
            리스트 하나
          </div>
          <div class="coaching-list list">
            리스트 하나
          </div>
          <div class="coaching-list list">
            리스트 하나
          </div>
        </div>
    </div>
    </div>
  </div>
</template>

<style scoped>
@import url(https://fonts.googleapis.com/css?family=Lato:400,300,700,900);

.title {
  border-bottom: 2px solid black;
  display: inline-block;
  margin: 2rem 3rem;
}

.portfolio-layout {
  background-color: aliceblue;
  width: 80%;
  margin: auto;
  border-radius: 1.5rem;
}
.intro-box {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
.intro-img {
  margin: 20px;
  display: inline-block;
}

.intro-detail {
  display: inline-block;
  align-items: center;
}
.coaching-list-top {
  display: flex;
  justify-content: space-between; 
}
.coaching-list-main {
  width: 90%;
  margin: 2vh auto;
  border: 2px solid black;
}
.list {
  border: 2px solid black;
  height: 20vh;
}
.menu{
  display: flex;
  align-items: center;
  margin: 0 2rem;
}
.menu a {
  color: rgba(0, 0, 0, 0.8);
  font-size: 10pt;
  font-weight: 400;
  padding: 15px 25px;
  /**/
  position: relative;
  display: inline-blockk;
  text-decoration: none;
  text-transform: uppercase;
}
.SMN_effect-42 a {
  position: relative;
}

.SMN_effect-42 a:before {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 1.5rem;
  background-color: #6e6e6e;
  transform-origin: 100% 50%;
  transform: scale(0, 1);
  top: 0;
  left: 0;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1);
}

.SMN_effect-42 a:after {
  content: '';
  position: absolute;
  width: 100%;
  height: 2px;
  background-color: rgb(0, 0, 0);
  left: 0;
  bottom: 0;
  transform-origin: 0% 50%;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0.2s;
}

.SMN_effect-42 a span {
  position: relative;
}

.SMN_effect-42 a span:after {
  content: attr(data-hover);
  position: absolute;
  left: 0;
  width: 100%;
  height: 100%;
  transition: opacity 0.2s cubic-bezier(0.33, 0.91, 0.42, 1) 0s;
}

.SMN_effect-42 a:hover:before {
  transform: scale(1);
  transform-origin: 0 50%;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0.2s;
}

.SMN_effect-42 a:hover:after {
  transform: scale(0, 1);
  transform-origin: 100% 50%;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0s;
}

.SMN_effect-42 a:hover span:after {
  color: #ffffff;
  transition: color 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0.2s;
}
</style>
