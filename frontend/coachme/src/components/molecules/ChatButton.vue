<!-- 채팅 버튼 컴포넌트 -->

<script setup>
import DmList from '.././molecules/DmList.vue'
import DmWindow from '.././molecules/DmWindow.vue';
import { useCounterStore } from "../../stores/chat-status";
import { storeToRefs } from 'pinia'

const store = useCounterStore();

const { isChatList, showChat, chatList, directMessage } = storeToRefs(store)
const { selectDm, backToList, closeDmWindow, resetDm } = store
/*
윗줄 : dm리스트가 보여지고 있는지 여부, 채팅창이 켜져있는지 여부
아랫줄 : dm리스트에서 1대 dm 들어가는 함수, 1대1에서 dm리스트로 돌아가는 함수, 
dm창 자체를 닫는 함수, dm창을 닫았을 때 리스트로 돌리는 함수
*/

const myId = "coame"
// 나의 id

const clickDm = () => {
  selectDm()
} // dm리스트에서 하나의 방을 골랐을 때

const closeDm = () => {
  backToList()
} // 1:1dm의 홈버튼을 눌렀을 때

const closeChat = () => {
  closeDmWindow()
} // dm창 자체를 닫을 때

const reset = () => {
  resetDm()
} // dm 창 자체가 닫혔을 때, 리스트로 돌아가도록 설정

</script>

<template>
  <!-- dm 버튼 -->
  <q-btn round size="20px" color="amber-7" icon="chat" @click="reset">
    <!-- dm 영역 -->
    <q-menu style="max-height: 400px; max-width: 400px;" v-model="showChat">
      <div class="row no-wrap q-pa-md">
        <!-- dm리스트와 1:1dm을 번갈아가면서 렌더링 -->
        <DmList v-if="isChatList" :dm-list="chatList" @click-dm="clickDm"></DmList>
        <DmWindow v-else :direct-message="directMessage" :myId="myId" @close-dm="closeDm" @close-chat="closeChat"></DmWindow>
      </div>
    </q-menu>
  </q-btn>
</template>

<style scoped>
</style>