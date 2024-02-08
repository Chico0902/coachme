<script setup>
import { onBeforeMount } from 'vue'
import { getEnterDmRoom } from '@/utils/api/dm-api'
import { useChatStore } from '@/stores/chat-status'
import InputForm from './InputForm.vue'
import { storeToRefs } from 'pinia'
import { getAccessToken, decodeToken } from '@/utils/functions/auth'

const chatStore = useChatStore()
const longId = decodeToken(getAccessToken()).longId
const { directMessages, closeDmWindow, resetDm } = storeToRefs(chatStore)
</script>

<template>
  <div class="q-pa-md row justify-center window-box">
    <div class="q-pa-md row justify-center chat-box">
      <!-- dm 리스트로 돌아가는 버튼 -->
      <q-btn flat @click="resetDm">
        <span class="material-symbols-outlined"> Home </span>
      </q-btn>
      <!-- dm창을 닫는 버튼 -->
      <q-btn flat @click="closeDmWindow">
        <span class="material-symbols-outlined"> Close </span>
      </q-btn>
      <div style="width: 100%; max-width: 400px">
        <div v-for="list in directMessages" :key="list">
          <!-- 자신이 보낸 부분-->
          <div v-if="longId == list.memberId">
            <q-chat-message :name="'나'" :text="list.chat" sent bg-color="amber-7"></q-chat-message>
          </div>

          <!-- 상대가 보낸 부분-->
          <div v-else>
            <q-chat-message :name="list.id" :text="list.chat" size="6" text-color="white" bg-color="primary">
            </q-chat-message>
          </div>
        </div>
      </div>
    </div>
    <div class="input">
      <InputForm></InputForm>
    </div>
  </div>
</template>

<style scoped>
.window-box {
  max-width: min-content;
  max-height: max-content;
}
.chat-box {
  background-color: #ffeec1;
  max-width: 400px;
  max-height: 500px;
  overflow-y: auto;
}

.material-symbols-outlined {
  font-size: 25px;
}
.input {
  max-width: 400px;
}
</style>
