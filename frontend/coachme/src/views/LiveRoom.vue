<script setup>
import LiveMenuList from '@/components/molecules/LiveMenuList.vue'
import UserVideo from '@/components/openvidu/UserVideo.vue'
import LiveChat from '@/components/molecules/LiveChat.vue'
import profile from '@/components/atoms/ProfileImage.vue'
import LivePeopleList from '@/components/molecules/LivePeopleList.vue'
import { ref, onBeforeMount } from 'vue'
import { useRoute } from 'vue-router'
import { OpenVidu } from 'openvidu-browser'
import { postLiveCoachingEntrnce, postConnectLiveCoaching } from '@/utils/api/livecoaching-api'
import { useMemberStore } from '@/stores/member'
import router from '@/router'

/**
 * VARIABLES
 */

// for pinia
const memberStore = useMemberStore()
const { longId } = memberStore

// for vue
const route = useRoute()

// local variable
const coachingTitle = ref('이것만 알면 당신도 할 수 있다.') // 코칭 제목
const participants = ref([
  { name: '고코치', imageUrl: '/assets/img/logo.png', profileText: '안녕하세요 고코치입니다.' },
  { name: '고코미', imageUrl: '/assets/icons/coame.png', profileText: '안녕하세요 고코미입니다.' },
  { name: '고양이', imageUrl: '/assets/icons/coame.png', profileText: '안녕하세요 고양이입니다.' },
  { name: '옆동네 고양이', imageUrl: '/assets/icons/coame.png', profileText: '야옹야옹야옹' },
  { name: '코숏', imageUrl: '/assets/icons/coame.png', profileText: 'Korean ShotHair' }
]) // 참가자 목록
const id = 'coame' // 사용자 이름
const dm = ref([]) // 채팅 목록

// for render
const isChatOpen = ref(false)
const isPeopleOpen = ref(false)
const isCoach = ref(true)

// for openvidu
const OVCamera = ref(undefined) // 영상
const OVScreen = ref(undefined) // 화면 공유
const sessionCamera = ref(undefined)
const sessionScreen = ref(undefined)
const mainStreamManager = ref(undefined)
const screenPublisher = ref(undefined)
const screenShare = ref(undefined)
const publisher = ref(undefined)
const subscribers = ref([])

// Join form
const mySessionId = route.params.id
const myUserName = ref(longId)
// const screensharing = ref(false)

/**
 * METHODS
 */

// 채팅창 보여주기
const changeChatStatus = (status) => {
  isChatOpen.value = status.chat
  isPeopleOpen.value = status.people
}

// 회의 참석자 목록 보여주기
const changeParticipantsStatus = (status) => {
  isChatOpen.value = status.chat
  isPeopleOpen.value = status.people
}

// 화상 나가기
const exit = () => {
  if (confirm('정말로 채팅을 종료하시겠습니까?')) {
    leaveSession()
    router.push('/')
  }
}

onBeforeMount(() => {
  // 최초 입장 시 오픈비두 라이브 채팅방에 접속
  console.log(route.params.id)
  joinSession()

  // 해당 방 참가자 목록 받아오기
})

/**
 * For OpenVidu Session
 */

// function updateScreensharing(value) {
//   screensharing.value = value
// }

// function updatePublisher(value) {
//   screenPublisher.value = value
// }

function joinSession() {
  // --- 1) Get an OpenVidu object ---
  OVCamera.value = new OpenVidu()
  OVScreen.value = new OpenVidu()

  // --- 2) Init a session ---
  sessionCamera.value = OVCamera.value.initSession()
  sessionScreen.value = OVScreen.value.initSession()

  // --- 3) Specify the actions when events take place in the session ---

  // ------- 3.1) Handle subscribers of 'CAMERA' type
  sessionCamera.value.on('streamCreated', (event) => {
    if (event.stream.typeOfVideo == 'CAMERA') {
      const subscriber = sessionCamera.value.subscribe(event.stream, 'container-cameras')
      // const metadata = JSON.parse(event.target.options.metadata);
      // const clientData = metadata.clientData;
      // participants.value.push(clientData)
      subscribers.value.push(subscriber)
    }
  })

  // ------- 3.2) Handle subscribers of 'Screen' type
  sessionScreen.value.on('streamCreated', (event) => {
    if (event.stream.typeOfVideo == 'SCREEN') {
      const subscriberScreen = sessionScreen.value.subscribe(event.stream, 'container-screens')
      screenShare.value = subscriberScreen
    }
  })

  // On every Stream destroyed...
  sessionCamera.value.on('streamDestroyed', (stream) => {
    const index = subscribers.value.indexOf(stream.streamManager, 0)
    if (index >= 0) {
      subscribers.value.splice(index, 1)
    }
  })
  // ------- 3.2) Handle subscribers of 'Screen' type
  sessionScreen.value.on('streamDestroyed', (stream) => {
    const index = subscribers.value.indexOf(stream.streamManager, 0)
    if (index >= 0) {
      subscribers.value.splice(index, 1)
    }
    screenShare.value = undefined
  })

  // On every asynchronous exception...
  sessionCamera.value.on('exception', ({ exception }) => {
    console.warn(exception)
  })

  // --- 4) Connect to the session with a valid user token ---

  // Get a token from the OpenVidu deployment
  getToken(mySessionId.value).then((token) => {
    // First param is the token. Second param can be retrieved by every user on event
    // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
    sessionCamera.value
      .connect(token, { clientData: myUserName.value })
      .then(() => {
        // --- 5) Get your own camera stream with the desired properties ---

        // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
        // element: we will manage it on our own) and with the desired properties
        publisher.value = OVCamera.value.initPublisher(undefined, {
          audioSource: undefined, // The source of audio. If undefined default microphone
          videoSource: undefined, // The source of video. If undefined default webcam
          publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
          publishVideo: true, // Whether you want to start publishing with your video enabled or not
          resolution: '640x480', // The resolution of your video
          frameRate: 30, // The frame rate of your video
          insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
          mirror: false // Whether to mirror your local video or not
        })

        // Set the main video in the page to display our webcam and store our Publisher
        mainStreamManager.value = publisher.value
        subscribers.value.push(publisher.value)
        participants.value.push(myUserName.value)

        // --- 6) Publish your stream ---

        sessionCamera.value.publish(publisher.value)
      })
      .catch((error) => {
        console.log('There was an error connecting to the sessionCamera:', error.code, error.message)
      })
  })
  getToken(mySessionId.value).then((tokenScreen) => {
    // Create a token for screen share
    sessionScreen.value
      .connect(tokenScreen, { clientData: myUserName.value })
      .then(() => {
        console.log('Session screen connected')
      })
      .catch((error) => {
        console.warn('There was an error connecting to the session for screen share:', error.code, error.message)
      })
  })

  window.addEventListener('beforeunload', leaveSession)
}

function leaveSession() {
  // --- 7) Leave the session by calling 'disconnect' method over the Session object ---
  if (sessionCamera.value) sessionCamera.value.disconnect()

  // Empty all properties...
  sessionCamera.value = undefined
  mainStreamManager.value = undefined
  publisher.value = undefined
  subscribers.value = []
  OVCamera.value = undefined

  // Remove beforeunload listener
  window.removeEventListener('beforeunload', leaveSession)
}

function updateMainVideoStreamManager(stream) {
  if (mainStreamManager.value === stream) return
  mainStreamManager.value = stream
}

async function getToken(mySessionId) {
  const sessionId = await postConnectLiveCoaching({ mySessionId })
  return await postLiveCoachingEntrnce(sessionId)
}
</script>
<template>
  <div class="title">
    <div>
      <h5>{{ coachingTitle }}</h5>
    </div>
  </div>

  <div class="all">
    <div class="main-layout">
      <div class="chat-outside">
        <!-- 코치 화면 -->
        <div class="coach">
          <UserVideo id="main-video" :stream-manager="mainStreamManager" />
        </div>

        <!-- 코미 화면 -->
        <div class="coame element-with-scrollbar">
          <div id="video-container" class="col-6" v-for="sub in subscribers" :key="sub.stream.connection.connectionId">
            <button
              style="position: absolute; top: 10px; right: 10px"
              class="btn"
              @click="updateMainVideoStreamManager(sub)"
            >
              메인 카메라 바꾸기
            </button>
            <user-video :stream-manager="sub" />
          </div>
        </div>

        <!-- 채팅 -->
        <q-layout
          v-if="isChatOpen"
          view="lHh Lpr lFf"
          container
          style="height: 70vh; width: 30rem"
          class="shadow-2 rounded-borders dm-window-container"
        >
          <q-page-container>
            <LiveChat :directMessage="dm" :myId="id" @chatOff="isChatOpen = false"></LiveChat>
          </q-page-container>
        </q-layout>

        <!-- 참가자 리스트 -->
        <div v-else-if="isPeopleOpen" class="people">
          <template v-for="participant in participants" :key="participant.id">
            <q-item clickable v-ripple>
              <q-item-section avatar>
                <q-item>
                  <profile :img="participant.imageUrl"></profile>
                </q-item>
              </q-item-section>
              <q-item-section>
                <q-item-label>{{ participant.name }}</q-item-label>
                <q-item-label caption>{{ participant.profileText }}</q-item-label>
              </q-item-section>
            </q-item>
          </template>
        </div>
      </div>
    </div>
  </div>

  <!-- 강의장 메뉴 리스트 -->
  <div class="buttons" style="background-color: #004c98">
    <LiveMenuList
      @change-chat-status="changeChatStatus"
      @change-participants-status="changeParticipantsStatus"
      @exit="exit"
      :isCoach="isCoach"
      :publisher="publisher"
      :participants="participants"
      :OVScreen="OVScreen"
      :sessionScreen="sessionScreen"
      :screensharing="screenShare"
    ></LiveMenuList>
  </div>
</template>

<style scoped>
.title {
  width: 1280px;
  height: 20px;
  text-align: center;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin: auto;
  padding-top: 4.5vh;
}

.all {
  display: flex;
  justify-content: center;
}

.main-layout {
  width: 1280px;
  min-height: calc(88vh - 20px);
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
}

.chat-outside {
  display: flex;
  justify-content: space-between;
}

.coach {
  background-color: white;
  width: 100%;
  height: 70vh;
  margin: auto;
  overflow: scroll;
  display: flex;
  text-align: center;
  flex-direction: row;
  -ms-overflow-style: none;
}

.coame {
  min-width: fit-content;
  height: 70vh;
  justify-content: center;
  align-items: flex-start;
  overflow-y: scroll;
  position: relative;
}

.element-with-scrollbar {
  overflow: hidden;
}

.element-with-scrollbar:hover {
  overflow: auto;
}

.chat {
  min-width: 24.2vw;
  max-height: 70vh;
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
  margin-top: 5vh;
  margin-left: 4vw;
  border-radius: 15px;
}

.people {
  min-width: 15vw;
  max-height: 70vh;
  position: relative;
  margin-top: 5vh;
  margin-left: 4vw;
  border: 3px solid #fbe5a9;
}

.buttons {
  height: 10vh;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
