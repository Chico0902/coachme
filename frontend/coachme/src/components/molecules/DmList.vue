<script setup>
import profile from '../atoms/ProfileImage.vue'
import { useAuthStore } from '@/stores/auth'
import { useChatStore } from '../../stores/chat-status'
import { storeToRefs } from 'pinia'
import { onBeforeMount } from 'vue'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import { getMyDmRooms, getEnterDmRoom } from '@/utils/api/dm-api'
import router from '@/router'

/**
 * VARIABLES
 */

// pinia 사용
const chatStore = useChatStore()
const authStore = useAuthStore()
const { isLogin } = storeToRefs(authStore)
const { showChat, chatList } = storeToRefs(chatStore)
const { closeDmWindow } = chatStore

/**
 * METHODS
 */

// emit 정의
const emit = defineEmits(['clickDm'])
const clickDm = (index) => {
  emit('clickDm', index)
}

onBeforeMount(() => {
  // 로그인 여부 확인
  if (isLogin.value === false) {
    alert('DM을 보내시려면 로그인이 필요합니다. 로그인 페이지로 이동합니다.')
    showChat.value = false
    router.push('/login')

    // DM목록 받아오기
  } else {
    const token = getAccessToken()
    const longId = decodeToken(token).longId
    getMyDmRooms(
      token,
      longId,
      (success) => console.log(success),
      (fail) => console.log(fail)
    )
  }
})
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

    <div v-for="dm in chatList" :key="dm.id">
      <q-item clickable v-ripple @click="clickDm(dm.id)">
        <!-- 프로필 사진 영역 -->
        <q-item-section avatar>
          <profile :img="dm.img"></profile>
        </q-item-section>

        <!-- 주고받은 dm 영역-->
        <q-item-section>
          <!-- 이름 -->
          <q-item-label>{{ dm.name }}</q-item-label>
          <!-- 마지막 dm -->
          <q-item-label caption>{{ dm.lastDm }}</q-item-label>
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
