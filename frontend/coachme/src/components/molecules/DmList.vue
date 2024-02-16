<script setup>
import profile from '../atoms/ProfileImage.vue'
import { useChatStore } from '../../stores/chat-status'
import { storeToRefs } from 'pinia'

/**
 * VARIABLES
 */

// pinia 사용
const chatStore = useChatStore()
const { chatList } = storeToRefs(chatStore)
const { openChatByRoomId } = chatStore
</script>

<template>
  <q-list bordered class="rounded-borders" style="width: 100%; max-width: 400px">
    <q-bar class="bg-amber-5">
      <q-avatar><img src="/assets/img/coame.png" /></q-avatar>
      <span style="font-size: 0.8rem; margin-left: 0.3rem; font-weight: bold; line-height: normal">채팅</span>

      <q-space />
    </q-bar>
    <div v-if="chatList.length > 0">
      <template v-for="chat in chatList" :key="chat.roomId">
        <q-item clickable v-ripple @click="openChatByRoomId(chat.roomId, chat.memberName, chat.memberProfileUrl)">
          <!-- 프로필 사진 영역 -->
          <q-item-section avatar>
            <q-item>
              <profile :img="chat.memberProfileUrl"></profile>
              <q-badge v-if="!chat.hasRead" rounded floating color="red" style="top: 3px; right: 8px"></q-badge>
            </q-item>
          </q-item-section>

          <!-- 주고받은 dm 영역-->
          <q-item-section>
            <!-- 이름 -->
            <q-item-label>{{ chat.memberName }}</q-item-label>
            <!-- 마지막 dm -->
            <q-item-label caption>{{ chat.lastDm }}</q-item-label>
          </q-item-section>

          <!-- 새로운 dm이 있을 경우 표시 -->
        </q-item>
      </template>
    </div>
    <div v-else>
      <q-item><q-item-label>채팅 내역이 없습니다.</q-item-label> </q-item>
      <q-item dense><q-item-label caption>지금 바로 원하는 코치님에게 문의하세요!</q-item-label></q-item>
    </div>
  </q-list>
</template>

<style scoped>
.material-symbols-outlined {
  font-size: 24px;
}
</style>
