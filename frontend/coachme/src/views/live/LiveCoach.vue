<script setup>
import LiveMenuList from '@/components/molecules/LiveMenuList.vue'
import ComeLiveVideo from '@/components/molecules/CoameLiveVideo.vue'
import LiveChat from '@/components/molecules/LiveChat.vue'
import LivePeopleList from '@/components/molecules/LivePeopleList.vue';

import { ref, reactive } from 'vue';

const users = ref(["h1", "h2", "h3", "h4", "h5"])

const isChatOpen = ref(false);
const isPeopleOpen = ref(false);

const changeChatStatus = (status) => {
  isChatOpen.value = status.chat
  isPeopleOpen.value = status.people
}

const changePeopleStatus = (status) => {
  isChatOpen.value = status.chat
  isPeopleOpen.value = status.people
}

const coachingTitle = ref("이것만 알면 당신도 할 수 있다.")

const id = "coame"

const directMessage = reactive([{
    "id": "coame",
    "chat": ["hey, how are you?", "I'm so so these day"]
  }, {
    "id": "coach",
    "chat": ["doing fine, how r you?"]
  }, {
    "id": "coach",
    "chat": ["I just feel like typing a really, really, REALLY long message to annoy you..."]
  }])

const people = ref(["고코치", "고코미", "고양이"])

</script>
<template>
  <div class="title">
    <div>
      <h5>{{ coachingTitle }}</h5>
    </div>
  </div>

  <div class="all">
    <div class="main-layout">
      <div class="chat-outside">
        <!-- 코치 화면 -->
        <div class="coach">
          <div style="width: 60vw; height: 70vh; background-color: gray;"></div>
        </div>

        <!-- 코미 화면 -->
        <div class="coame element-with-scrollbar">
          <div v-for="(user, index) in users" :key="user">
            <ComeLiveVideo :coame="user" :style="{ marginTop: index !== 0 ? '1vh' : '0' }"></ComeLiveVideo>
          </div>
        </div>

        <!-- 채팅 -->
        <div v-if="isChatOpen" class="chat">
          <LiveChat :directMessage="directMessage" :myId="id"></LiveChat>
        </div>
        <!-- 참가자 리스트 -->
        <div v-else-if="isPeopleOpen" class="people">
          <LivePeopleList :people="people"></LivePeopleList>
        </div>
      </div>
    </div>
  </div>

  <!-- 강의장 메뉴 리스트 -->
  <div class="buttons" style="background-color: #004C98;">
    <LiveMenuList v-on:change-chat-status="changeChatStatus" v-on:change-people-status="changePeopleStatus"></LiveMenuList>
  </div>
</template>

<style scoped>
.title {
  width: 1280px;
  height: 20px;
  text-align: center;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin: auto;
  padding-top: 4.5vh;
}

.all {
  display: flex;
  justify-content: center;
}

.main-layout {
  width: 1280px;
  min-height: calc(88vh - 20px);
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
}

.chat-outside {
  display: flex;
  justify-content: space-between;
}

.coach {
  background-color: white;
  width: 100%;
  height: 70vh;
  margin: auto;
  margin-top: 5vh;
  margin-bottom: 5vh;
  overflow: scroll;
  display: flex;
  text-align: center;
  flex-direction: row;
  -ms-overflow-style: none;
}

.coame {
  min-width: fit-content;
  height: 70vh;
  justify-content: center;
  align-items: flex-start;
  overflow-y: scroll;
  position: relative;
  margin-top: 5vh;
  margin-left: 5vw;
}

.element-with-scrollbar {
  overflow: hidden;
}

.element-with-scrollbar:hover {
  overflow: auto;
}

.chat {
  min-width: 24.2vw;
  height: 70vh;
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
  margin-top: 5vh;
  margin-left: 4vw;
  background-color: #fbe5a9;
  border-radius: 15px;
}

.people {
  min-width: 24.2vw;
  height: 70vh;
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
  margin-top: 5vh;
  margin-left: 4vw;
  border-radius: 15px;
  background-color:  #FCBF17;
  color: black;
  border: 3px solid #fbe5a9;
}

.buttons {
  height: 10vh;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>