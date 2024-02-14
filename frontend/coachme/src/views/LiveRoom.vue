<script setup>
import LiveMenuList from '@/components/molecules/LiveMenuList.vue'
import CoachUserVideo from '@/components/openvidu/UserVideo-coach.vue'
import CoameUserVideo from '@/components/openvidu/UserVideo-coame.vue'
import profile from '@/components/atoms/ProfileImage.vue'
import LiveChat from '@/components/molecules/LiveChat.vue'
import router from '@/router'
import Swal from 'sweetalert2'
import { ref, onBeforeMount, computed, onBeforeUnmount } from 'vue'
import { useRoute } from 'vue-router'
import { OpenVidu } from 'openvidu-browser'
import {
  postLiveCoachingEntrnce,
  postConnectLiveCoaching,
  getRecordStart,
  getRecordStop,
  getRecordFinish
} from '@/utils/api/livecoaching-api'
import { useMemberStore } from '@/stores/member'
import { decodeToken } from '@/utils/functions/auth'
import { useAuthStore } from '@/stores/auth'

/**
 * VARIABLES
 */

// for pinia
const authStore = useAuthStore()
const myName = decodeToken(authStore.accessToken).name
const memberStore = useMemberStore()
const { longId, profileText, profileImageUrl } = memberStore

// for vue
const route = useRoute()

// local variable
const coachingTitle = `${route.params.id}번 라이브 방` // 코칭 제목
const myData = { id: longId, memberName: myName, imageUrl: profileImageUrl, profileText: profileText }
const coachId = route.params.coachId
const participants = computed(() => {
  const ret = []
  subscribers.value.forEach((subscriber) => ret.push(JSON.parse(subscriber.stream.connection.data).clientData))
  return ret
}) // 참가자 목록
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
const screensharing = ref(false)

// for recording
const recordingId = ref('')

// for coaching video
const coachMainVideo = ref(undefined)
/**
 * METHODS
 */

// 채팅창 보여주기
const changeChatStatus = (status) => {
  isChatOpen.value = status.chat
}

// 회의 참석자 목록 보여주기
const changeParticipantsStatus = (status) => {
  isChatOpen.value = status.chat
  isPeopleOpen.value = status.people
}

// 레코드 시작
const startRecording = () => {
  getRecordStart(
    mySessionId,
    (success) => {
      recordingId.value = success.data.recordingId
      alert('녹화를 시작합니다.')
    },
    (fail) => {
      console.log(fail)
    }
  )
}

// 레코드 종료
const stopRecording = () => {
  getRecordStop(
    recordingId.value,
    (success) => {
      console.log(success)
      alert('녹화를 중지합니다.')
    },
    (fail) => {
      console.log(fail)
    }
  )
}

// 화상 나가기
const exit = async () => {
  const result = await Swal.fire({
    title: '정말로 화상을 종료하시겠습니까?',
    icon: 'question',
    showCancelButton: true,
    confirmButtonText: '예',
    cancelButtonText: '아니오'
  })

  // 사용자가 중지
  if (!result.isConfirmed) return

  // 화상 저장여부
  let isSave = false
  if (recordingId.value != '') {
    isSave = await Swal.fire({
      title: '녹화영상을 저장하시겠습니까?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: '예',
      cancelButtonText: '아니오'
    })
  }

  getRecordFinish(
    mySessionId,
    (success) => {
      console.log(success)
      if (isSave)
        new Promise((resolve) => {
          success.data.list.forEach((url) => window.open(url))
          resolve()
        })
          .then(() => {
            leaveSession()
            router.push('/mypage-coach/live')
          })
          .catch((fail) => console.log(fail))
      else {
        leaveSession()
        router.push('/mypage-coach/live')
      }
    },
    (fail) => {
      console.log(fail)
    }
  )
}

onBeforeMount(() => {
  // 최초 입장 시 오픈비두 라이브 채팅방에 접속
  console.log(route.params.id)
  joinSession()
  // 해당 방 참가자 목록 받아오기
})

onBeforeUnmount(() => {
  leaveSession()
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
      const metadata = JSON.parse(event.target.options.metadata)
      const clientData = metadata.clientData
      console.log(clientData)
      subscribers.value.push(subscriber)
      subscribers.value.forEach((subscriber) => {
        console.log(subscriber)
        const newId = JSON.parse(subscriber.stream.connection.data)
        console.log(newId.clientData.id)
        if (newId.clientData.id == coachId) coachMainVideo.value = subscriber
      })
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
  getToken(mySessionId).then((token) => {
    const clientData = myData
    // First param is the token. Second param can be retrieved by every user on event
    // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
    sessionCamera.value
      .connect(token, { clientData })
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
        coachMainVideo.value = publisher.value
        subscribers.value.push(publisher.value)

        // --- 6) Publish your stream ---

        sessionCamera.value.publish(publisher.value)
      })
      .catch((error) => {
        console.log('There was an error connecting to the sessionCamera:', error.code, error.message)
      })
  })
  getToken(mySessionId).then((tokenScreen) => {
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
  console.log(getToken)
  const sessionId = await postConnectLiveCoaching({ customSessionId: mySessionId })
  const response = await postLiveCoachingEntrnce(sessionId, myUserName.value)
  const newParticipant = {
    id: response.memberId,
    memberName: response.memberName,
    imageUrl: response.memberProfileUrl,
    profileText: response.memberProfileText
  }
  const inParticipantsFind = participants.value.find((participant) => participant.id === newParticipant.id)
  if (inParticipantsFind === undefined) participants.value.push(newParticipant)
  return response.token
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
          <!-- <div style="max-width: 5vw;"> -->
          <CoachUserVideo id="main-video" :stream-manager="coachMainVideo" />
          <!-- </div> -->
        </div>

        <!-- <div class="person" v-for="sub in subscribers" :key="sub.stream.connection.connectionId">
          <UserVideo :stream-manager="sub" />
        </div> -->
        <!-- 코미 화면 -->
        <div class="coame-container">
          <div class="coame element-with-scrollbar">
            <div v-for="sub in subscribers" :key="sub.stream.connection.connectionId">
              <CoameUserVideo :stream-manager="sub" />
            </div>
          </div>
        </div>

        <!-- 채팅 -->
        <div v-show="isChatOpen">
          <q-layout
            view="lHh Lpr lFf"
            container
            style="height: 70vh; width: 30rem"
            class="shadow-2 rounded-borders dm-window-container"
          >
            <q-page-container>
              <LiveChat :mySessionId="mySessionId" @chatOff="isChatOpen = false"></LiveChat>
            </q-page-container>
          </q-layout>
        </div>

        <!-- 참가자 리스트 -->
        <div v-if="isPeopleOpen" class="people">
          <template v-for="participant in participants" :key="participant.id">
            <q-item clickable v-ripple>
              <q-item-section avatar>
                <q-item>
                  <profile :img="participant.imageUrl"></profile>
                </q-item>
              </q-item-section>
              <q-item-section>
                <q-item-label>{{ participant.memberName }}</q-item-label>
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
      @start-record="startRecording"
      @stop-record="stopRecording"
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
#main-video {
  min-width: 100%;
}

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
.main-layout::-webkit-scrollbar {
  width: 10px;
  height: 0.5rem;
}

.main-layout::-webkit-scrollbar-thumb {
  background-color: #6593ff;
  border-radius: 1.5rem;
  min-width: 50px;
}

.main-layout::-webkit-scrollbar-thumb:hover {
  background-color: #3370ff;
}

.main-layout::-webkit-scrollbar-track {
  background-color: #c7c7c7;
  border-radius: 1.5rem;
}
.chat-outside {
  display: flex;
  justify-content: space-between;
}

.coach {
  background-color: white;
  width: 100%;
  min-width: 70%;
  margin: auto;
  display: flex;
  text-align: center;
  flex-direction: row;
  -ms-overflow-style: none;
  overflow: hidden;
  flex: 1;
}
.coach::-webkit-scrollbar {
  width: 10px;
  height: 0.5rem;
}

.coach::-webkit-scrollbar-thumb {
  background-color: #6593ff;
  border-radius: 1.5rem;
  min-width: 50px;
}

.coach::-webkit-scrollbar-thumb:hover {
  background-color: #3370ff;
}

.coach::-webkit-scrollbar-track {
  background-color: #c7c7c7;
  border-radius: 1.5rem;
}

.coame {
  min-width: fit-content;
  /* width: 10vw; */
  max-width: fit-content;
  height: 70vh;
  overflow-y: auto;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  flex: 1;
}

.coame::-webkit-scrollbar {
  width: 10px;
  height: 0.5rem;
}

.coame::-webkit-scrollbar-thumb {
  background-color: #6593ff;
  border-radius: 1.5rem;
  min-width: 50px;
}

.coame::-webkit-scrollbar-thumb:hover {
  background-color: #3370ff;
}

.coame::-webkit-scrollbar-track {
  background-color: #c7c7c7;
  border-radius: 1.5rem;
}
.coame {
  max-width: 50vw;
}

.element-with-scrollbar {
  overflow: scroll;
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
