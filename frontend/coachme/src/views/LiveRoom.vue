<script setup>
import LiveMenuList from '@/components/molecules/LiveMenuList.vue'
import UserVideo from '@/components/openvidu/UserVideo.vue'
import profile from '@/components/atoms/ProfileImage.vue'
import LiveChat from '@/components/molecules/LiveChat.vue'
import router from '@/router'
import Swal from 'sweetalert2'
import { ref, onBeforeMount, computed, onBeforeUnmount, watch } from 'vue'
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
const iAmCoach = longId == coachId
const participants = computed(() => {
  const rets = []
  subscribers.value.forEach((subscriber) => {
    const userData = JSON.parse(subscriber.stream.connection.data).clientData
    if (rets.find((ret) => ret.id == userData.id) == undefined) rets.push(userData)
  })
  return rets
}) // 참가자 목록

// for render
const isChatOpen = ref(false)
const isPeopleOpen = ref(false)

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
const coachMainStream = ref(undefined)
const coameStreams = ref([])
const coameMainStreamOne = ref(undefined)
const coameMainStreamTwo = ref(undefined)
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
      if (isSave)
        new Promise((resolve) => {
          success.data.list.forEach((url) => window.open(url))
          resolve()
        })
          .then(() => {
            leaveSession()
            router.push('/mypage-coach/live')
          })
          .catch((fail) => console.error(fail))
      else {
        leaveSession()
        router.push('/mypage-coach/live')
      }
    },
    (fail) => {
      console.error(fail)
    }
  )
}

watch(
  subscribers,
  () => {
    // 코치id 찾기, 코치 아닌애들은 코미배열에 넣기
    if (subscribers.value.length == undefined) return
    coameStreams.value = []
    subscribers.value.forEach((subscriber) => {
      const clientData = JSON.parse(subscriber.stream.connection.data)
      const userId = clientData.clientData.id
      if (userId == coachId) coachMainStream.value = subscriber
      else {
        // 코미 id 겹치는지 확인하고 집어넣기
        if (coameStreams.value.length === 0) coameStreams.value.push(subscriber)
        else
          coameStreams.value.forEach((coameStream, index) => {
            const coameClientData = JSON.parse(coameStream.stream.connection.data)
            const coameId = coameClientData.clientData.id
            // 본인이름이랑 겹치면 안넣음
            if (coameId == longId) return
            // 코미id가 안겹칠때는 그냥 넣음
            else if (coameId != userId) coameStreams.value.push(subscriber)
            // 겹칠때는 교체하기
            else coameStreams.value.splice(index, 1, subscriber)
          })
      }
    })
  },
  { deep: true }
)

watch(
  coameStreams,
  () => {
    switch (coameStreams.value.length) {
      case 0:
        return
      case 1: {
        coameMainStreamOne.value = coameStreams.value[0]
        return
      }
      default: {
        coameMainStreamOne.value = coameStreams.value[0]
        coameStreams.value.forEach((coameStream) => {
          const coameClientData = JSON.parse(coameStream.stream.connection.data)
          const coameId = coameClientData.clientData.id
          if (coameId == longId) coameMainStreamTwo.value = coameStream
        })
        return
      }
    }
  },
  { deep: true }
)

onBeforeMount(() => {
  // 최초 입장 시 오픈비두 라이브 채팅방에 접속
  joinSession()
  // 해당 방 참가자 목록 받아오기
})

onBeforeUnmount(() => {
  leaveSession()
})

/**
 * For OpenVidu Session
 */

function updateScreenSharing(value) {
  screensharing.value = value
}

// 띄워줄 stream이 value로 들어옴!!!
function updatePublisher(value) {
  screenPublisher.value = value
}

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
      // const metadata = JSON.parse(event.target.options.metadata)
      // const clientData = metadata.clientData
      subscribers.value.push(subscriber)
    }
  })

  // ------- 3.2) Handle subscribers of 'Screen' type
  sessionScreen.value.on('streamCreated', (event) => {
    if (event.stream.typeOfVideo == 'SCREEN') {
      const subscriberScreen = sessionScreen.value.subscribe(event.stream, 'container-screens')
      screenShare.value = subscriberScreen
      screenPublisher.value = subscriberScreen
      screensharing.value = true
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
    screensharing.value = false
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

async function getToken(mySessionId) {
  console.log(getToken)
  const sessionId = await postConnectLiveCoaching({ customSessionId: mySessionId })
  const response = await postLiveCoachingEntrnce(sessionId, myUserName.value)
  return response.token
}

const changeCoameTwo = (selectedCoameId) => {
  if (coameStreams.value.length === 0) return
  new Promise((resolve) => {
    coameMainStreamTwo.value = undefined
    resolve()
  }).then(() =>
    coameStreams.value.forEach((coameStream) => {
      const coameClientData = JSON.parse(coameStream.stream.connection.data)
      const coameId = coameClientData.clientData.id
      if (coameId == selectedCoameId) {
        coameMainStreamTwo.value = coameStream
        console.log(coameStream)
      }
    })
  )
}

// 창 변경
const layoutCount = ref(1)
const changeLayoutCount = () => {
  if (layoutCount.value < 4) layoutCount.value++
  else layoutCount.value = 1
}
</script>
<template>
  <div class="all">
    <div class="title">
      {{ coachingTitle }}
      <q-btn icon="grid_view" @click="changeLayoutCount"> </q-btn>
    </div>

    <div class="video-layout">
      <!-- 코치만 있을때 -->
      <Transition>
        <template v-if="layoutCount === 1">
          <div class="layout-one">
            <UserVideo :stream-manager="coachMainStream"></UserVideo>
          </div>
        </template>
      </Transition>

      <!-- 코치1명 코미 1명 -->
      <Transition>
        <template v-if="layoutCount === 2">
          <!-- {{ coameStreams }} -->
          <div class="layout-two">
            <div class="coach-layout-two">
              <UserVideo :stream-manager="coachMainStream"></UserVideo>
            </div>
            <div class="coame-layout-two">
              <!-- 코치는 오른쪽 코미들로 채우기 -->
              <template v-if="iAmCoach">
                <UserVideo :stream-manager="coameMainStreamOne"></UserVideo>
              </template>
              <!-- 코미는 오른쪽 본인으로 채우기 -->
              <template v-else>
                <UserVideo :stream-manager="mainStreamManager"></UserVideo>
              </template>
            </div>
          </div>
        </template>
      </Transition>

      <!-- 코치1명 코미 2명 이상 -->
      <Transition>
        <template v-if="layoutCount === 3">
          <div class="layout-three">
            <div class="coach-layout-three">
              <UserVideo :stream-manager="coachMainStream"></UserVideo>
            </div>
            <div class="coame-layout-three">
              <div class="coame-layout-three-flex">
                <!-- 코치는 오른쪽 코미들로 채우기 -->
                <template v-if="iAmCoach">
                  <div class="coame-layout-three-inner">
                    <UserVideo :stream-manager="coameMainStreamOne"></UserVideo>
                  </div>
                  <div class="coame-layout-three-inner">
                    <UserVideo :stream-manager="coameMainStreamTwo"></UserVideo>
                  </div>
                </template>
                <!-- 코미는 오른쪽 위 본인으로 채우기 -->
                <template v-else>
                  <div class="coame-layout-three-inner">
                    <UserVideo :stream-manager="mainStreamManager"></UserVideo>
                  </div>
                  <div class="coame-layout-three-inner">
                    <UserVideo :stream-manager="coameMainStreamTwo"></UserVideo>
                  </div>
                </template>
              </div>
            </div>
          </div>
        </template>
      </Transition>

      <!-- 영상화면 공유 -->
      <Transition>
        <template v-if="layoutCount === 4">
          <template v-if="screensharing">
            <div class="share-layout">
              <UserVideo :stream-manager="screenPublisher"></UserVideo>
            </div>
          </template>
          <template v-else>
            <div class="share-layout-info"><p>현재 공유중인 화면이 없습니다.</p></div>
          </template>
        </template>
      </Transition>
    </div>

    <!-- 강의장 메뉴 리스트 -->
    <div class="button-layout">
      <LiveMenuList
        @change-chat-status="changeChatStatus"
        @change-participants-status="changeParticipantsStatus"
        @start-record="startRecording"
        @stop-record="stopRecording"
        @update-publisher="updatePublisher"
        @update-screensharing="updateScreenSharing"
        @exit="exit"
        :iAmCoach="iAmCoach"
        :publisher="publisher"
        :participants="participants"
        :OVScreen="OVScreen"
        :sessionScreen="sessionScreen"
        :screensharing="screenShare"
      ></LiveMenuList>
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
  <div v-if="isPeopleOpen" class="shadow-2 rounded-borders people">
    <template v-for="participant in participants" :key="participant.id">
      <q-item clickable v-ripple @click="changeCoameTwo(participant.id)">
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
</template>

<style scoped>
.all {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
.title {
  width: 100%;
  height: 10vh;
  font-size: 1.5rem;
  color: #004c98;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
.video-layout {
  width: 100%;
  height: 80vh;
  background-color: #f2af5c;
}
.button-layout {
  background-color: #004c98;
  width: 100%;
  height: 10vh;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
}
.layout-one {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 720px;
  height: 540px;
  border: solid 5px #004c98;
  background-color: #004c98;
}

.layout-two {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 1200px;
  height: 540px;
}

.coach-layout-two {
  border: solid 5px #004c98;
  background-color: #004c98;
  position: absolute;
  top: 50%;
  left: 5%;
  transform: translate(0, -50%);
  width: 480px;
  height: 360px;
}

.coame-layout-two {
  border: solid 5px #004c98;
  background-color: #004c98;
  position: absolute;
  top: 50%;
  right: 5%;
  transform: translate(0, -50%);
  width: 480px;
  height: 360px;
}

.layout-three {
  position: relative;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 1200px;
  height: 540px;
}

.coach-layout-three {
  border: solid 5px #004c98;
  background-color: #004c98;
  position: absolute;
  top: 50%;
  left: 0;
  transform: translate(0, -50%);
  width: 720px;
  height: 540px;
}

.coame-layout-three {
  position: absolute;
  top: 50%;
  right: 0;
  transform: translate(0, -50%);
  width: 420px;
  height: 540px;
}
.coame-layout-three-flex {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.coame-layout-three-inner {
  border: solid 5px #004c98;
  background-color: #004c98;
  width: 320px;
  height: 240px;
}
.share-layout {
  position: absolute;
  top: 10vh;
  left: 0;
  width: 100%;
  height: 80vh;
}
.share-layout-info {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}
.share-layout-info p {
  font-size: 2rem;
  color: #004c98;
}
.people {
  position: absolute;
  top: 15vh;
  left: 5%;
  background-color: #8ebefd;
}
.dm-window-container {
  position: absolute;
  top: 15vh;
  right: 5%;
  color: #fff;
  background-color: white;
  text-align: center;
  z-index: 7000;
  border-radius: 0.5rem;
  max-height: 600px;
  max-width: 300px;
  overflow: scroll;
}
</style>
