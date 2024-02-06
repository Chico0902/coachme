<script setup>
import DmList from '.././molecules/DmList.vue'
import DmWindow from '.././molecules/DmWindow.vue'
import { useChatStore } from '../../stores/chat-status'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'
import { getEnterDmRoom } from '@/utils/api/dm-api'
import { decodeToken } from '@/utils/functions/auth'

/**
 * VARIABLES
 */

// in pinia
const chatStore = useChatStore()
const authStore = useAuthStore()
const { isChatList, showChat, chatList, directMessage } = storeToRefs(chatStore)
const { selectDm, backToList, closeDmWindow, resetDm } = chatStore
const { accessToken } = storeToRefs(authStore)

/*
윗줄 : dm리스트가 보여지고 있는지 여부, 채팅창이 켜져있는지 여부
아랫줄 : dm리스트에서 1대 dm 들어가는 함수, 1대1에서 dm리스트로 돌아가는 함수,
dm창 자체를 닫는 함수, dm창을 닫았을 때 리스트로 돌리는 함수
*/

// DM룸 현황 받아오기
const myId = 'coame'

// 나의 정보
const clickDm = (coachLongId) => {
  const longId = decodeToken(accessToken.value).longId
  getEnterDmRoom(
    accessToken.value,
    longId,
    coachLongId,
    (success) => console.log(success),
    (fail) => console.log(fail)
  )
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
    <q-menu style="max-height: 400px; max-width: 400px" v-model="showChat">
      <div class="row no-wrap q-pa-md">
        <!-- dm리스트와 1:1dm을 번갈아가면서 렌더링 -->
        <DmList v-if="isChatList" :dm-list="chatList" @click-dm="clickDm"></DmList>
        <DmWindow
          v-else
          :direct-message="directMessage"
          :myId="myId"
          @close-dm="closeDm"
          @close-chat="closeChat"
        ></DmWindow>
      </div>
    </q-menu>
  </q-btn>
</template>

<style scoped></style>
