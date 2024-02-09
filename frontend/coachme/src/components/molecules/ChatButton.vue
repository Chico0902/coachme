<script setup>
import { useChatStore } from '../../stores/chat-status'

/**
 * VARIABLES
 */

// in pinia
const chatStore = useChatStore()
const { openChatList } = chatStore
</script>

<template>
  <!-- dm 버튼 -->
  <q-btn round size="20px" color="amber-7" icon="chat" @click="openChatList()"> </q-btn>
</template>

<style scoped></style>
