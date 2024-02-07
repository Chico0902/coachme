<script setup>
import profile from '../atoms/ProfileImage.vue'
import { useAuthStore } from '@/stores/auth'
import { useChatStore } from '../../stores/chat-status'
import { storeToRefs } from 'pinia'
import { onBeforeMount } from 'vue'
import { decodeToken } from '@/utils/functions/auth'
import { getEnterDmRoom, getMyDmRooms } from '@/utils/api/dm-api'
import router from '@/router'

/**
 * VARIABLES
 */

// pinia 사용
const chatStore = useChatStore()
const authStore = useAuthStore()
const { isLogin, accessToken } = storeToRefs(authStore)
const { selectDm, showChat, chatList, closeDmWindow, directMessages } = storeToRefs(chatStore)

/**
 * METHODS
 */

// 최초 DM방 목록 받아오기
onBeforeMount(() => {
  // 로그인 여부 확인
  if (isLogin.value === false) {
    alert('DM을 보내시려면 로그인이 필요합니다. 로그인 페이지로 이동합니다.')
    showChat.value = false
    router.push('/login')
  } else {
    const longId = decodeToken(accessToken.value).longId
    getMyDmRooms(
      longId,
      (success) => {
        chatList.value = success.data.list
      },
      (fail) => console.log(fail)
    )
  }
})

function clickDm(coachLongId) {
  const longId = decodeToken(accessToken.value).longId
  getEnterDmRoom(
    longId,
    coachLongId,
    (success) => {
      directMessages.value = success.data.list
      console.log(success)
    },
    (fail) => console.log(fail)
  )
}
</script>

<template>
  <q-list bordered class="rounded-borders" style="width: 100%; max-width: 400px">
    <q-item>
      <q-item-section>
        <q-item-label class="text-h6" style="font-size: 1.1rem">디엠 목록</q-item-label>
      </q-item-section>

      <q-item-section side top>
        <q-btn flat @click="closeDmWindow()" style="margin-right: -2vw; margin-left: 5vw">
          <span class="material-symbols-outlined"> Close </span>
        </q-btn>
      </q-item-section>
    </q-item>

    <q-separator spaced style="margin-top: 0"></q-separator>

    <div v-for="chat in chatList" :key="chat.memberId">
      <q-item clickable v-ripple @click="clickDm(chat.memberId)">
        <!-- 프로필 사진 영역 -->
        <q-item-section avatar>
          <profile :img="chat.img"></profile>
        </q-item-section>

        <!-- 주고받은 dm 영역-->
        <q-item-section>
          <!-- 이름 -->
          <q-item-label>{{ chat.name }}</q-item-label>
          <!-- 마지막 dm -->
          <q-item-label caption>{{ chat.lastDm }}</q-item-label>
          <!-- 새로운 dm이 있을 경우 표시 -->
          <q-badge rounded floating color="red"></q-badge>
        </q-item-section>
      </q-item>

      <!-- 가장 밑에 있는 dm일 경우, 구분자를 출력하지 않음 -->
      <div v-if="index != chatList.length - 1"><q-separator spaced></q-separator></div>
    </div>
  </q-list>
</template>

<style scoped>
.material-symbols-outlined {
  font-size: 24px;
}
</style>
