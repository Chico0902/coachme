<script setup>
import navbar from '@/components/molecules/LoginNavBar.vue'
import CoachDetailCard from '@/components/molecules/CoachDetailCard.vue';
import ChatBox from '@/components/molecules/ChatBox.vue';
import DetailTopBar from '@/components/molecules/DetailTopBar.vue';
import ChatButton from '@/components/molecules/ChatButton.vue';
import Reviews from '@/components/molecules/ReviewDetailCard.vue';
</script>

<template>
  <!-- nav -->
  <div class="nav-bar">
    <navbar />
  </div>
  <div class="all">
    <div class="main-layout">
      <div class="mypage-outside">
        <div class="mainpage">
          <div class="profile">
            <CoachDetailCard></CoachDetailCard>
            <q-separator></q-separator>

            <div class="portfolio-menu">
              <DetailTopBar></DetailTopBar>
            </div>

            <div class="coach-introduction">
              <h2>코치 소개</h2>
            </div>
            <q-separator></q-separator>
            <div class="coaching-category">
              <h2>제공 코칭</h2>
            </div>
            <q-separator></q-separator>
            <div class="coach-review">
              <h2>리뷰</h2>
              <Reviews></Reviews>
            </div>
            
          </div>
        </div>
        <div class="chat-box">
          <ChatBox></ChatBox>
        </div>
        <div class="chat-button">
          <ChatButton style="margin-left: 10vw; margin-top: 57vh; width: 50px; height: 50px;">
          </ChatButton>
        </div>
      </div>
    </div>
  </div>

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
  margin-top: 5vh;
  margin-bottom: 5vh;
  border-radius: 1.5rem;
  overflow: scroll;
  display: flex;
  text-align: center;
  flex-direction: row;
}

.mainpage{
  -ms-overflow-style: none;
}
.mainpage::-webkit-scrollbar{
  display:none;
}

.profile {
  width: 80%;
}

.chat-box {
  max-height: fit-content;
  padding-top: 30vh;
  padding-right: 3vw;
}

.chat-button {
  position:fixed;
  bottom:13vh;
  right:14vw;
  color:#FFF;
  text-align:center;
}

h2 {
  font-size: 1.5rem;
  padding: 0;
  margin: 0;
  padding-left: 1vw;
  text-align: left;
}

.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}
</style>