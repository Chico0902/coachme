<template>
  <button @click="joinSession()">연결</button>
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
          <template v-if="mainStreamManager">
            <user-video :stream-manager="publisher" @click="updateMainVideoStreamManager(publisher)" />
          </template>
          <template v-else>
            <user-video style="width: 60vw; height: 70vh; background-color: gray;" />
          </template>
        </div>

        <!-- 코미 화면 -->
        <div class="coame element-with-scrollbar">
          <div v-for="sub in subscribers" :key="sub.stream.connection.connectionId">
            <CoameLiveVideo 
            :stream-manager="sub"
            :name="user" 
            :style="{ marginTop: index !== 0 ? '1vh' : '0' }"
            @click="updateMainVideoStreamManager(sub)"></CoameLiveVideo>
          </div>
        </div>

        <!-- 채팅 -->
        <div v-if="isChatOpen" class="chat">
          <LiveChat :directMessage="directMessage" :myId="id"></LiveChat>
        </div>
        <!-- 참가자 리스트 -->
        <div v-if="isPeopleOpen" class="people">
          <LivePeopleList :people="users"></LivePeopleList>
        </div>
      </div>
    </div>
  </div>

  <!-- 강의장 메뉴 리스트 -->
  <div class="buttons" style="background-color: #004C98;">
    <LiveMenuList v-on:change-chat-status="changeChatStatus" v-on:change-people-status="changePeopleStatus"
    :isCoach="isCoach" :publisher="publisher"></LiveMenuList>
  </div>

</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import CoameLiveVideo from"@/components/molecules/CoameLiveVideo.vue";
import UserVideo from "@/components/molecules/LiveUserVideo.vue";
import OptionControl from "@/components/molecules/LiveOptions.vue";
import LiveMenuList from '@/components/molecules/LiveMenuList.vue'
import LiveChat from '@/components/molecules/LiveChat.vue'
import LivePeopleList from '@/components/molecules/LivePeopleList.vue';


axios.defaults.headers.post["Content-Type"] = "application/json";

const APPLICATION_SERVER_URL = process.env.NODE_ENV === 'production' ? '' : 'http://localhost:7777/api/';

export default {
  name: "LiveCoach",

  components: {
    UserVideo,
    OptionControl,
    CoameLiveVideo,
    LiveMenuList
},

  data() {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      mySessionId: "SessionA",
      myUserName: "Participant" + Math.floor(Math.random() * 100),
      users: ["고코치", "고코미", "고양이", "옆동네 고양이", "코숏"],
      isChatOpen: false,
      isPeopleOpen: false,
      coachingTitle: "이것만 알면 당신도 할 수 있다.",
      directMessage: [
        {
          "id": "coame",
          "chat": ["안녕하세요", "코칭에 대해 여쭤보고 싶은게 있어요."]
        },
        {
          "id": "coach",
          "chat": ["안녕하세요. 어떤게 궁금하신가요?"]
        },
        {
          "id": "coame",
          "chat": ["코칭 페이지에는 (중략)이라고 되어있는데 자세히 알고 싶어요"]
        },
        {
          "id": "coach",
          "chat": ["이 코칭에서는 (중략)하는 것을 알려드리며, 필요하시면 더 자세히 피드백을 해드리고 있습니다."]
        }
      ]
    };
  },

  methods: {
    async joinSession() {
      
      this.OV = new OpenVidu();
      this.session = this.OV.initSession();

      this.session.on("streamCreated", ({ stream }) => {
        if (this.mainStreamManager === stream) return;
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      try {
        const token = await this.getToken(this.mySessionId);
        await this.session.connect(token, { clientData: this.myUserName });

        let publisher = this.OV.initPublisher(undefined, {
          audioSource: undefined,
          videoSource: undefined,
          publishAudio: true,
          publishVideo: true,
          resolution: "640x480",
          frameRate: 30,
          insertMode: "APPEND",
          mirror: false,
        });

        this.mainStreamManager = publisher;
        this.publisher = publisher;

        await this.session.publish(this.publisher);
      } catch (error) {
        console.log("There was an error connecting to the session:", error.code, error.message);
      }

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      if (this.session) this.session.disconnect();
      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;
      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    async getToken(mySessionId) {
      const sessionId = await this.createSession(mySessionId);
      return await this.createToken(sessionId);
    },

    async createSession(sessionId) {
      const response = await axios.post(APPLICATION_SERVER_URL + 'live/sessions', { customSessionId: sessionId }, {
        headers: { 'Content-Type': 'application/json', },
      });
      return response.data;
    },

    async createToken(sessionId) {
      const response = await axios.post(APPLICATION_SERVER_URL + 'live/sessions/' + sessionId + '/connections', {}, {
        headers: { 'Content-Type': 'application/json', },
      });
      return response.data;
    },

    changeChatStatus(status) {
      this.isChatOpen = status.chat;
      this.isPeopleOpen = status.people;
    },

    changePeopleStatus(status) {
      this.isChatOpen = status.chat;
      this.isPeopleOpen = status.people;
    },
  },
};
</script>


<style scoped>
.title {
  height: 20px;
  text-align: center;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin: 2vh;
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
  width: 70%;
  /* margin: auto;
  margin-top: 5vh;
  margin-bottom: 5vh; */
  overflow: scroll;
  display: flex;
  text-align: center;
  flex-direction: row;
  -ms-overflow-style: none;
}

.coame {
  min-width: 30%;
  /* height: 70vh; */
  justify-content: center;
  align-items: flex-start;
  overflow-y: scroll;
  position: relative;
  margin-top: 5vh;
  margin-left: 5vw;
}

.element-with-scrollbar {
  overflow: hidden;
}

.element-with-scrollbar:hover {
  overflow: auto;
}

.chat {
  min-width: 24.2vw;
  height: 70vh;
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
  margin-top: 5vh;
  margin-left: 4vw;
  background-color: #fbe5a9;
  border-radius: 15px;
}

.people {
  min-width: 15vw;
  height: 70vh;
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
  margin-top: 5vh;
  margin-left: 4vw;
  border-radius: 15px;
  background-color: #FCBF17;
  color: black;
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