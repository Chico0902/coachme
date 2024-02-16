<script setup>
import { ref } from 'vue'

/**
 * VARIABLES
 */

// from parent(LiveRoom.vue)
const props = defineProps({
  iAmCoach: Boolean,
  publisher: Object,
  participants: Array,
  OVScreen: Object,
  sessionScreen: Object,
  screensharing: Boolean
})

// for change status
const videoStatus = ref(true)
const micStatus = ref(true)
const isChatOpen = ref(false)
const isPeopleOpen = ref(false)

// for show chat or participants
const emit = defineEmits(
  ['changeChatStatus'],
  ['changeParticipantsStatus'],
  ['updateScreensharing'],
  ['updatePublisher'],
  ['startRecord'],
  ['stopRecord'],
  ['exit']
)

// for recording
const isRecording = ref(false)

/**
 * METHODS
 */

// 비디오 스위치 버튼
const videoSwitch = () => {
  videoStatus.value = !videoStatus.value
  console.log(props.publisher)
  if (videoStatus.value) props.publisher.publishVideo(true)
  else props.publisher.publishVideo(false)
}

// 마이크 스위치 버튼
const micSwitch = () => {
  micStatus.value = !micStatus.value
  if (micStatus.value) props.publisher.publishAudio(true)
  else props.publisher.publishAudio(false)
}

// 채팅 스위치 버튼
const chatSwitch = () => {
  isChatOpen.value = !isChatOpen.value
  const data = { chat: isChatOpen.value, people: isPeopleOpen.value }
  emit('changeChatStatus', data)
}

// 참가자 스위치 버튼
const peopleSwitch = () => {
  isPeopleOpen.value = !isPeopleOpen.value
  const data = { chat: isChatOpen.value, people: isPeopleOpen.value }
  emit('changeParticipantsStatus', data)
}

// 화면공유 메서드
function publishScreenShare() {
  // --- 9.1) To create a publisherScreen set the property 'videoSource' to 'screen'
  const publisherScreen = props.OVScreen.initPublisher(undefined, { videoSource: 'screen' })

  // --- 9.2) Publish the screen share stream only after the user grants permission to the browser
  publisherScreen.once('accessAllowed', () => {
    emit('updateScreensharing', true)
    console.log('--------------update------------')

    // If the user closes the shared window or stops sharing it, unpublish the stream
    publisherScreen.stream
      .getMediaStream()
      .getVideoTracks()[0]
      .addEventListener('ended', () => {
        props.sessionScreen.unpublish(publisherScreen)
        emit('updateScreensharing', false)
      })
    props.sessionScreen.publish(publisherScreen)

    emit('updatePublisher', publisherScreen)
  })

  publisherScreen.on('videoElementCreated', function (event) {
    console.log(event)
  })

  publisherScreen.once('accessDenied', () => {
    console.error('Screen Share: Access Denied')
  })
}

// 녹화기능
const toggleRecord = () => {
  isRecording.value = !isRecording.value
  if (isRecording.value) emit('startRecord')
  else emit('stopRecord')
}

// 화상회의 나가기
const exit = () => {
  emit('exit')
}
</script>

<template>
  <q-toolbar class="text-white" style="width: 1280px; height: 10vh">
    <q-btn flat @click="videoSwitch">
      <span v-if="videoStatus" class="material-symbols-outlined"> videocam </span>
      <span v-else class="material-symbols-outlined"> videocam_off </span>
      <q-tooltip v-if="videoStatus" anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>카메라 끄기</strong>
      </q-tooltip>
      <q-tooltip v-else anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>카메라 켜기</strong>
      </q-tooltip>
    </q-btn>

    <q-btn flat @click="micSwitch">
      <span v-if="micStatus" class="material-symbols-outlined"> mic </span>
      <span v-else class="material-symbols-outlined"> mic_off </span>
      <q-tooltip v-if="micStatus" anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>마이크 끄기</strong>
      </q-tooltip>
      <q-tooltip v-else anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>마이크 켜기</strong>
      </q-tooltip>
    </q-btn>

    <q-space></q-space>

    <q-btn flat @click="peopleSwitch">
      <span class="material-symbols-outlined"> group </span>
      <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>참가자 목록</strong>
      </q-tooltip>
    </q-btn>

    <q-btn v-if="props.iAmCoach" flat>
      <template v-if="isRecording">
        <span class="material-symbols-outlined" style="color: red" @click="toggleRecord"> radio_button_checked </span>
        <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
          <strong>녹화하기</strong>
        </q-tooltip>
      </template>
      <template v-else>
        <span class="material-symbols-outlined" @click="toggleRecord"> radio_button_checked </span>
        <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
          <strong>녹화하기</strong>
        </q-tooltip>
      </template>
    </q-btn>

    <q-btn flat @click="publishScreenShare">
      <span class="material-symbols-outlined"> draw </span>
      <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>화면공유</strong>
      </q-tooltip>
    </q-btn>

    <q-btn flat @click="chatSwitch">
      <span class="material-symbols-outlined"> chat </span>
      <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <strong>채팅</strong>
      </q-tooltip>
    </q-btn>

    <q-space></q-space>
    <q-btn flat @click="exit">
      <span class="material-symbols-outlined"> logout </span>
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
