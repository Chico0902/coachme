<!-- 코칭 하단 메뉴 컴포넌트
필요한 정보 : 없음
-->
<script setup>
import { ref } from 'vue';

const videoStatus = ref(true)
const micStatus = ref(true)
const props = defineProps({ publisher: Object });
const publisher = props.publisher;
const audioOptions = ref({
  audioOptions: true
});
const videoOptions = ref({
  publishVideo: true
});
const showParticipantsList = ref(false);
console.log(publisher);

const videoSwitch = () => {
  console.log(publisher);
  videoStatus.value = !videoStatus.value
  if (publisher) {
    if (this.audioOptions.publishAudio) {
      publisher.publishAudio(true); // Publish audio
    } else {
      publisher.publishAudio(false); // Stop publishing audio
    }
  }
}

const micSwitch = () => {
  micStatus.value = !micStatus.value
  if (this.publisher) {
    if (this.audioOptions.publishAudio) {
      this.publisher.publishAudio(true); // Publish audio
    } else {
      this.publisher.publishAudio(false); // Stop publishing audio
    }
  }
}

const emit = defineEmits(['changeChatStatus'], ['changePeopleStatus'])

const isChatOpen = ref(false);
const isPeopleOpen = ref(false);

const chatSwitch = () => {
  isChatOpen.value = !isChatOpen.value
  isPeopleOpen.value = false

  const data = { chat: isChatOpen.value, people: isPeopleOpen.value }
  emit('changeChatStatus', data)
}; // 채팅 버튼 클릭


const peopleSwitch = () => {
  isPeopleOpen.value = !isPeopleOpen.value
  isChatOpen.value = false

  const data = { chat: isChatOpen.value, people: isPeopleOpen.value }
  emit('changePeopleStatus', data)
} // 참가자 목록 클릭 
</script>

<template>
  <q-toolbar class="text-white" style="width:1280px; height: 10vh;">
    <q-btn flat @click="videoSwitch">
      <span v-if="videoStatus" class="material-symbols-outlined">
        videocam
      </span>
      <span v-else class="material-symbols-outlined">
        videocam_off
      </span>
      <q-tooltip v-if="videoStatus" anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>카메라 끄기</strong>
      </q-tooltip>
      <q-tooltip v-else anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>카메라 켜기</strong>
      </q-tooltip>
    </q-btn>
    <q-btn flat @click="micSwitch">
      <span v-if="micStatus" class="material-symbols-outlined">
        mic
      </span>
      <span v-else class="material-symbols-outlined">
        mic_off
      </span>
      <q-tooltip v-if="micStatus" anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>마이크 끄기</strong>
      </q-tooltip>
      <q-tooltip v-else anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>마이크 켜기</strong>
      </q-tooltip>
    </q-btn>
    <q-space></q-space>
    <q-btn flat @click="peopleSwitch">
      <span class="material-symbols-outlined">
        group
      </span>
      <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>참가자 목록</strong>
      </q-tooltip>
    </q-btn>
    <q-btn flat>
      <span class="material-symbols-outlined">
        radio_button_checked
      </span>
      <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>녹화하기</strong>
      </q-tooltip>
    </q-btn> <q-btn flat>
      <span class="material-symbols-outlined">
        meeting_room
      </span>
      <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>진실의 방</strong>
      </q-tooltip>
    </q-btn>
    <q-btn flat>
      <span class="material-symbols-outlined">
        draw
      </span>
      <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>화이트 보드</strong>
      </q-tooltip>
    </q-btn>
    <q-btn flat @click="chatSwitch">
      <span class="material-symbols-outlined">
        chat
      </span>
      <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>채팅</strong>
      </q-tooltip>
    </q-btn>
    <q-space></q-space>
    <q-btn flat>
      <span class="material-symbols-outlined">
        logout
      </span>
      <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>나가기</strong>
      </q-tooltip>
    </q-btn>
  </q-toolbar>
</template>

<style scoped>
.material-symbols-outlined {
  font-size: 36px;
}
</style>