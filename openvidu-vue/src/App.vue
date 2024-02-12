<template>
  <div id="main-container" class="container">
    <div id="join" v-if="!sessionCamera">
      <div id="img-div">
        <img src="resources/images/openvidu_grey_bg_transp_cropped.png" />
      </div>
      <div id="join-dialog" class="jumbotron vertical-center">
        <h1>Join a video session</h1>
        <div class="form-group">
          <p>
            <label>Participant</label>
            <input v-model="myUserName" class="form-control" type="text" required />
          </p>
          <p>
            <label>Session</label>
            <input v-model="mySessionId" class="form-control" type="text" required />
          </p>
          <p class="text-center">
            <button class="btn btn-lg btn-success" @click="joinSession()">
              Join!
            </button>
          </p>
        </div>
      </div>
    </div>

    <div id="session" v-if="sessionCamera">
      <div id="session-header">
        <h1 id="session-title">{{ mySessionId }}</h1>
        <input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession"
          value="Leave session" />
        <OptionControl :publisher="publisher" :participants="participants" />
        <br />
        <Screenshare :OVScreen="OVScreen" :sessionScreen="sessionScreen" :screensharing="screensharing"
          @updateScreensharing="updateScreensharing" @updatePublisher="updatePublisher"></ScreenShare>
      </div>
      <div style="background-color: gainsboro; padding: 30px;">
        <RecordingView :session="mySessionId"></RecordingView>
      </div>
      <div>
        <!-- 화면 공유시 공유화면 -->
        <div v-if="screenShare">
          <h2>공유 화면</h2>
          <user-video :stream-manager="screenShare" />
        </div>
        <!-- 내 화면 -->
        <h2>메인 카메라</h2>
        <user-video id="main-video" :stream-manager="mainStreamManager" />

        <!-- 상대방 화면 -->
        <h2>모든 참가자</h2>
        <div id="video-container" class="col-6" v-for="sub in subscribers" :key="sub.stream.connection.connectionId">
          <button style="position: absolute; top: 10px; right: 10px;" class="btn"
            @click="updateMainVideoStreamManager(sub)">메인 카메라 바꾸기</button>
          <user-video :stream-manager="sub" />
        </div>
      </div>


    </div>
  </div>
</template>

<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "./components/UserVideo";
import OptionControl from "./components/OptionControl.vue";
import Screenshare from "./components/ScreenShare.vue";
import RecordingView from "./components/RecordingView.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

const APPLICATION_SERVER_URL = process.env.NODE_ENV === 'production' ? '' : 'http://localhost:7777/api/';

export default {
  name: "App",

  components: {
    UserVideo,
    Screenshare,
    OptionControl,
    RecordingView
  },

  data() {
    return {
      // OpenVidu objects
      OVCamera: undefined,        // 영상
      OVScreen: undefined,        // 화면 공유
      sessionCamera: undefined,
      sessionScreen: undefined,
      mainStreamManager: undefined,
      screenPublisher: undefined,
      screenShare: undefined,
      publisher: undefined,
      subscribers: [],
      participants: [],

      // Join form
      mySessionId: "SessionA",
      myUserName: "Participant" + Math.floor(Math.random() * 100),
      screensharing: false
    };
  },

  methods: {
    updateScreensharing(value) {
      this.screensharing = value;
    },
    updatePublisher(value) {
      this.screenPublisher = value;
    },
    joinSession() {
      // --- 1) Get an OpenVidu object ---
      this.OVCamera = new OpenVidu();
      this.OVScreen = new OpenVidu()

      // --- 2) Init a session ---
      this.sessionCamera = this.OVCamera.initSession();
      this.sessionScreen = this.OVScreen.initSession();


      // --- 3) Specify the actions when events take place in the session ---

      // ------- 3.1) Handle subscribers of 'CAMERA' type
      this.sessionCamera.on('streamCreated', event => {
        if (event.stream.typeOfVideo == "CAMERA") {
          var subscriber = this.sessionCamera.subscribe(event.stream, 'container-cameras');
          // const metadata = JSON.parse(event.target.options.metadata);
          // const clientData = metadata.clientData;
          // this.participants.push(clientData)
          this.subscribers.push(subscriber);
        }
      });

      // ------- 3.2) Handle subscribers of 'Screen' type
      this.sessionScreen.on('streamCreated', event => {
        if (event.stream.typeOfVideo == "SCREEN") {
          var subscriberScreen = this.sessionScreen.subscribe(event.stream, 'container-screens');
          this.screenShare = subscriberScreen
        }
      });

      // On every Stream destroyed...
      this.sessionCamera.on('streamDestroyed', (stream) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });
      // ------- 3.2) Handle subscribers of 'Screen' type
      this.sessionScreen.on('streamDestroyed', (stream) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
        this.screenShare = undefined;
      });

      // On every asynchronous exception...
      this.sessionCamera.on("exception", ({ exception }) => {
        console.warn(exception);
      });


      // --- 4) Connect to the session with a valid user token ---

      // Get a token from the OpenVidu deployment
      this.getToken(this.mySessionId).then((token) => {

        // First param is the token. Second param can be retrieved by every user on event
        // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
        this.sessionCamera.connect(token, { clientData: this.myUserName })
          .then(() => {

            // --- 5) Get your own camera stream with the desired properties ---

            // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
            // element: we will manage it on our own) and with the desired properties
            let publisher = this.OVCamera.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            // Set the main video in the page to display our webcam and store our Publisher
            this.mainStreamManager = publisher;
            this.subscribers.push(publisher);
            this.participants.push(this.myUserName);
            this.publisher = publisher;

            // --- 6) Publish your stream ---

            this.sessionCamera.publish(this.publisher);
          })
          .catch((error) => {
            console.log("There was an error connecting to the sessionCamera:", error.code, error.message);
          });
      });
      this.getToken(this.mySessionId).then((tokenScreen) => {
        // Create a token for screen share
        this.sessionScreen.connect(tokenScreen, { clientData: this.myUserName }).then(() => {
          console.log("Session screen connected");
        }).catch((error => {
          console.warn('There was an error connecting to the session for screen share:', error.code, error.message);
        }));
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },


    leaveSession() {
      // --- 7) Leave the session by calling 'disconnect' method over the Session object ---
      if (this.sessionCamera) this.sessionCamera.disconnect();

      // Empty all properties...
      this.sessionCamera = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OVCamera = undefined;

      // Remove beforeunload listener
      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    /**
     * --------------------------------------------
     * GETTING A TOKEN FROM YOUR APPLICATION SERVER
     * --------------------------------------------
     * The methods below request the creation of a Session and a Token to
     * your application server. This keeps your OpenVidu deployment secure.
     *
     * In this sample code, there is no user control at all. Anybody could
     * access your application server endpoints! In a real production
     * environment, your application server must identify the user to allow
     * access to the endpoints.
     *
     * Visit https://docs.openvidu.io/en/stable/application-server to learn
     * more about the integration of OpenVidu in your application server.
     */
    async getToken(mySessionId) {
      const sessionId = await this.createSession(mySessionId);
      return await this.createToken(sessionId);
    },

    async createSession(sessionId) {
      const response = await axios.post(APPLICATION_SERVER_URL + 'live/sessions', 
      { customSessionId: sessionId }, 
      {
        headers: { 'Content-Type': 'application/json', },
      });
      return response.data; // The sessionId
    },

    async createToken(sessionId) {
      const response = await axios.post(APPLICATION_SERVER_URL + 'live/sessions/' + sessionId + '/connections', 
      {memberId :  this.myUserName},
      {
        headers: { 'Content-Type': 'application/json', },
      });
      console.log(response.data.token);
      console.log(response.data.memberId);
      console.log(response.data.memberName);
      console.log(response.data.memberProfileUrl);
      return response.data.token; // The token
    },
  },
};
</script>
