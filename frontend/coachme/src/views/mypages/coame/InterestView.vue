<script setup>
import ProfileImage from '@/components/atoms/ProfileImage.vue'
import like from '@/components/atoms/CustomLike.vue'

const size = '100px'
</script>
<template>
  <div class="profile-outside">
    <div class="profile-box">
      <div class="profile-img">
        <div><ProfileImage :size="size" /></div>
      </div>
      <div class="profile-detail-box">
        <div class="profile-detail-title">김싸피</div>
        <div class="now-coaching-num-box inside-num">
          <div class="now-coaching-num-title">현재까지 신청한 강의 수</div>
          <div class="now-coahing-num-detail">2개</div>
        </div>
        <!-- <div class="jjim-num-box inside-num">
          <div class="jjim-num-title">찜한 강의 수</div>
          <div class="jjim-num-detail">4개</div> -->
        <!-- </div> -->
      </div>
    </div>
    <div class="jjim-list-outside">
      <!-- <div class="jjim-switch-box">
        <div class="menu SMN_effect-12">
          <a href=""><span data-hover="Home">찜한코치</span></a>
          <a href=""><span data-hover="About">찜한 강의</span></a>
        </div> -->
      <!-- </div> -->
      <div class="jjim-toggle-box">
        <div class="jjim-toggle">
          <div class="jjim-toggle-title">수강한강의</div>
          <!-- <div class="jjim-icon"><like /></div> -->
        </div>
        <div class="jjim-list-box">
          <div class="jjim-list">리스트</div>
          <div class="jjim-list">리스트</div>
          <div class="jjim-list">리스트</div>
          <div class="jjim-list">리스트</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url(https://fonts.googleapis.com/css?family=Lato:400,300,700,900);

@font-face {
  font-family: 'TheJamsil5Bold';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2302_01@1.0/TheJamsil5Bold.woff2') format('woff2');
  font-weight: 700;
  font-style: normal;
}

.menu a {
  color: rgb(0, 0, 0);
  font-size: 12pt;
  font-weight: 400;
  padding: 15px 25px;

  position: relative;
  display: inline-blockk;
  text-decoration: none;
  text-transform: uppercase;
}
.SMN_effect-12 a:before,
.SMN_effect-12 a:after {
  position: absolute;
  width: 45px;
  height: 2px;
  background: #ffd000;
  content: '';
  opacity: 0.2;
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  transition: all 0.3s;
  pointer-events: none;
}

.SMN_effect-12 a:before {
  top: 0;
  left: 0;
  -webkit-transform: rotate(90deg);
  -moz-transform: rotate(90deg);
  transform: rotate(90deg);
  -webkit-transform-origin: 0 0;
  -moz-transform-origin: 0 0;
  transform-origin: 0 0;
}

.SMN_effect-12 a:after {
  right: 0;
  bottom: 0;
  -webkit-transform: rotate(90deg);
  -moz-transform: rotate(90deg);
  transform: rotate(90deg);
  -webkit-transform-origin: 100% 0;
  -moz-transform-origin: 100% 0;
  transform-origin: 100% 0;
}

.SMN_effect-12 a:hover:before,
.SMN_effect-12 a:hover:after,
.SMN_effect-12 a:focus:before,
.SMN_effect-12 a:focus:after {
  opacity: 1;
}

.SMN_effect-12 a:hover:before,
.SMN_effect-12 a:focus:before {
  left: 50%;
  -webkit-transform: rotate(0deg) translateX(-50%);
  -moz-transform: rotate(0deg) translateX(-50%);
  transform: rotate(0deg) translateX(-50%);
}

.SMN_effect-12 a:hover:after,
.SMN_effect-12 a:focus:after {
  right: 50%;
  -webkit-transform: rotate(0deg) translateX(50%);
  -moz-transform: rotate(0deg) translateX(50%);
  transform: rotate(0deg) translateX(50%);
}

.profile-box {
  width: 80%;
  min-width: 80%;
  height: 150px;
  background-color: aliceblue;
  margin: 30px auto;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.profile-img {
  display: inline-block;
}

.profile-detail-title {
  margin: 20px 0;
}

.inside-num {
  display: flex;
  justify-content: space-between;
  width: 230px;
  margin: 20px 0;
}
.jjim-list-outside {
  width: 80%;
  margin: 30px auto;
}
.jjim-switch-box {
  display: flex;
  justify-content: right;
}
.jjim-switch {
  margin: 0 15px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.jjim-switch:hover {
  background-color: lightblue;
}

.jjim-toggle-box {
  background-color: aliceblue;
  margin: 30px auto;
}

.jjim-toggle {
  display: flex;
  justify-content: right;
}

.jjim-list-box {
  display: flex;
  flex-direction: column;
}

.jjim-list {
  width: 90%;
  background-color: rgb(225, 225, 225);
  margin: 1rem auto;
  border-radius: 0.8rem;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
}

.jjim-toggle-title {
  margin: 1rem;
  font-size: large;
  font-weight: bold;
  display: flex;
  align-items: center;
}

.jjim-icon {
  margin: 1rem;
}
</style>
