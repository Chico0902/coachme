<!-- 채팅 버튼 컴포넌트 -->

<script setup>
import DmList from '.././molecules/DmList.vue'
import DmWindow from '.././molecules/DmWindow.vue';
import { useCounterStore } from "../../stores/chat-status";
import { storeToRefs } from 'pinia'

const store = useCounterStore();

const { isChatList, showChat } = storeToRefs(store)
const { selectDm, backToList, closeDmWindow, resetDm, } = store

const myId = "coame"
const otherId = "coach"
// 나의 id와 상대의 id

const chat = [{
  "id": "coame",
  "chat": ["hey, how are you?", "I'm so so these day"]
}, {
  "id": "coach",
  "chat": ["doing fine, how r you?"]
}, {
  "id": "coach",
  "chat": ["I just feel like typing a really, really, REALLY long message to annoy you..."]
}]
/* 주고받은 dm 내용
예시로 작성되었으며, 1:1 리스트에 표시될 내역
*/


const dm = [{
  img: "https://cdn.quasar.dev/img/avatar2.jpg",
  name: "Mary",
  lastDm: "Hello",
},
{
  img: "https://cdn.quasar.dev/img/avatar4.jpg",
  name: "John",
  lastDm: "Your suggestion is considered.",
},
{
  img: "https://src.hidoc.co.kr/image/lib/2022/11/15/1668491763670_0.jpg",
  name: "White Cat",
  lastDm: "meow",
}]
/* dm 리스트
프로필사진, 이름, 마지막 메시지
*/

const clickDm = () => {
  selectDm()
} // dm리스트에서 하나의 방을 골랐을 때

const closeDm = () => {
  backToList()
} // 1:1dm의 홈버튼을 눌렀을 때

const closeChat = () => {
  closeDmWindow()
} // 개선 예정

const reset = () => {
  resetDm()
} // dm 창 자체가 닫혔을 때, 리스트로 돌아가도록 설정

</script>

<template>
  <!-- dm 버튼 -->
  <q-btn round size="30px" color="amber-7" icon="chat" @click="reset">
    <!-- dm 영역 -->
    <q-menu style="max-height: 400px; max-width: 400px;" v-model="showChat">
      <div class="row no-wrap q-pa-md">
        <!-- dm리스트와 1:1dm을 번갈아가면서 렌더링 -->
        <DmList v-if="isChatList" :dm-list="dm" @click-dm="clickDm"></DmList>
        <DmWindow v-else :chatList="chat" :myId="myId" :otherId="otherId" @close-dm="closeDm" @close-chat="closeChat"></DmWindow>
      </div>
    </q-menu>
  </q-btn>
</template>

<style scoped></style>