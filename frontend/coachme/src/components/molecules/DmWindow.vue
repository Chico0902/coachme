<script setup>
import { Client } from '@stomp/stompjs'
import { onBeforeMount, onBeforeUnmount, ref, watch } from 'vue'
import { useChatStore } from '@/stores/chat-status'
import { storeToRefs } from 'pinia'
import { getAccessToken } from '@/utils/functions/auth'

/**
 * VARIABLES
 */

// for stomp
const client = ref(null)
const connected = ref(false)
const roomJoined = ref(false)
const newMessage = ref('')
const error = ref('')
const token = getAccessToken()

// in pinia
const chatStore = useChatStore()
const { chats, useDmWindow, coachName, myLongId, roomId, coachImageUrl } = storeToRefs(chatStore)

/**
 * METHODS
 */
onBeforeMount(() => {
  connectAndSubscribe()
})

watch(
  () => roomId.value,
  () => {
    connectAndSubscribe()
  }
)

onBeforeUnmount(() => {
  disconnect()
})

async function connectAndSubscribe() {
  try {
    await connectWebSocket() // WebSocket 연결을 기다림
    subscribeRoom() // WebSocket 연결 완료 후에 방에 구독
  } catch (error) {
    console.error('WebSocket 연결 실패:', error)
  }
}
function connectWebSocket() {
  if (client.value === null) {
    return new Promise((resolve, reject) => {
      client.value = new Client({
        brokerURL: 'wss://i10A403.p.ssafy.io/api/ws-dm',
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
        connectHeaders: {
          Authorization: getAccessToken()
        }
      })
      client.value.onConnect = (frame) => {
        connected.value = true
        resolve()
      }

      client.value.onStompError = (frame) => {
        console.error('에러 발생:', frame.headers['message'])
        console.error('에러 내용:', frame.body)
        error.value = `에러 발생: ${frame.headers['message']}, 내용: ${frame.body}`
        reject()
      }

      client.value.activate()
    })
  }
}
function disconnect() {
  if (client.value && connected.value) {
    client.value.deactivate()
    client.value = null
    connected.value = false
  }
}
function subscribeRoom() {
  if (!connected.value) {
    console.error('소켓 연결을 먼저 시작해주세요.')
    return
  }
  if (!roomId.value === '') {
    console.error('방 번호를 입력해주세요.')
    return
  }
  client.value.subscribe(`/topic/room/${roomId.value}`, (message) => {
    const newMessageBody = JSON.parse(message.body)
    chats.value.push(newMessageBody)
  })
  roomJoined.value = true
}

function sendMessage() {
  if (!connected.value) {
    console.error('소켓 연결을 먼저 시작해주세요.')
    return
  }
  if (newMessage.value === '' || roomId.value === '') {
    console.error('메시지와 방 번호를 입력해주세요.')
    return
  }

  client.value.publish({
    destination: `/app/sendDm/${roomId.value}`,
    headers: {
      Authorization: `Bearer ${token}`
    },
    body: JSON.stringify({
      content: newMessage.value,
      sender: myLongId.value // 발신자명을 설정할 수 있습니다.
    })
  })
  newMessage.value = ''
  const end = document.getElementById('end')
  end.scrollIntoView({
    behavior: 'smooth'
  })
}

// 시간 파싱함수
function parseTime(timestamp) {
  const NewTime = new Date(
    timestamp.substring(0, 4),
    timestamp.substring(5, 7) - 1,
    timestamp.substring(8, 10),
    timestamp.substring(11, 13),
    timestamp.substring(14, 16)
  )
  const CurTime = new Date()
  const timePass = CurTime.getTime() - NewTime.getTime()
  if (timePass < 1000 * 60) return '방금'
  else if (timePass < 1000 * 60 * 60) return `${Math.floor(timePass / 1000 / 60)}분 전`
  else if (timePass < 1000 * 60 * 60 * 24) return `${Math.floor(timePass / 1000 / 60 / 60)}시간 전`
  else return `${Math.floor(timePass / 1000 / 60 / 60 / 24)}일 전`
}
</script>

<template>
  <q-page id="window" style="padding-top: 60px; padding-bottom: 60px" class="window-box" padding>
    <div v-for="chat in chats" :key="chat">
      <div v-if="chat.memberId === myLongId">
        <q-chat-message
          :avatar="chat.memberProfileUrl"
          :text="chat.message"
          sent
          bg-color="amber-5"
          :stamp="`${parseTime(chat.createDate)}`"
        ></q-chat-message>
      </div>
      <div v-else>
        <q-chat-message
          :avatar="chat.memberProfileUrl"
          :name="chat.memberName"
          :text="chat.message"
          text-color="white"
          bg-color="primary"
          :stamp="`${parseTime(chat.createDate)}`"
        ></q-chat-message>
      </div>
    </div>
    <div id="end" style="height: 55px"></div>
    <q-page-sticky expand position="bottom">
      <q-toolbar class="bg-white">
        <form @submit.prevent="sendMessage">
          <q-input bottom-slots v-model="newMessage" counter dense style="min-width: 276px">
            <template v-slot:after>
              <q-btn @click="sendMessage" round dense flat icon="send" :disabled="!connected" />
            </template>
          </q-input>
        </form>
      </q-toolbar>
    </q-page-sticky>

    <q-page-sticky expand position="top">
      <q-toolbar class="bg-amber-5 text-white">
        <q-avatar><img :src="coachImageUrl" /></q-avatar>
        <span class="coach-name-container">{{ coachName }}</span>

        <q-space />

        <q-btn dense flat icon="close" @click="useDmWindow = false" />
      </q-toolbar>
    </q-page-sticky>
  </q-page>
</template>

<style scoped>
.window-box {
  text-align: left;
  color: black;
  font-size: 0.9rem;
}

.coach-name-container {
  color: black;
  font-size: 0.9rem;
  margin-left: 0.7rem;
  font-weight: bold;
  line-height: normal;
}
.material-symbols-outlined {
  font-size: 25px;
}
.input {
  min-width: 200px;
  min-height: 30px;
  max-height: 60px;
  overflow: scroll;
  display: flex;
  align-self: baseline;
}
</style>
