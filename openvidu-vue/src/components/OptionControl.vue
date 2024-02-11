<template>
  <div>
    <!-- 오디오 제어 버튼 -->
    <button @click="toggleAudio" class="btn"
      :class="{ 'btn-success': audioOptions.publishAudio, 'btn-danger': !audioOptions.publishAudio }">
      오디오
    </button>

    <!-- 비디오 제어 버튼 -->
    <button @click="toggleVideo" class="btn"
      :class="{ 'btn-success': videoOptions.publishVideo, 'btn-danger': !videoOptions.publishVideo }">
      비디오 
    </button>

    <!-- <button @click="showParticipants" class="btn btn-info">
      Show participants
    </button>
    <div v-if="showParticipantsList">
      <h3>participants:</h3>
      <ul>
        <li v-for="(participant, index) in participants" :key="index">{{ participant }}</li>
      </ul>
    </div> -->
  </div>
</template>
  
<script>
export default {
  props: {
    publisher: {
      type: Object,
      required: true,
    },
    participants: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      audioOptions: {
        publishAudio: true
      },
      videoOptions: {
        publishVideo: true
      },
      showParticipantsList: false,
      screenShareOptions: {
        screensharing: false
      }
    };
  },
  methods: {
    // 오디오 메서드
    toggleAudio() {
      this.audioOptions.publishAudio = !this.audioOptions.publishAudio;
      if (this.publisher) {
        this.updateAudioPublishStatus();
      }
    },
    updateAudioPublishStatus() {
      if (this.audioOptions.publishAudio) {
        this.publisher.publishAudio(true); // Publish audio
      } else {
        this.publisher.publishAudio(false); // Stop publishing audio
      }
    },

    // 비디오 메서드
    toggleVideo() {
      this.videoOptions.publishVideo = !this.videoOptions.publishVideo;
      if (this.publisher) {
        this.updateVideoPublishStatus();
      }
    },

    updateVideoPublishStatus() {
      if (this.videoOptions.publishVideo) {
        this.publisher.publishVideo(true); // Publish video
      } else {
        this.publisher.publishVideo(false); // Stop publishing video
      }
    },

    // 참가자 메서드
    showParticipants() {
      console.log(this.participants);
      this.showParticipantsList = !this.showParticipantsList;
    },
  },
};
</script>

<style>
.btn {
  font-size: 1.5rem;
  margin-right: 10px;
}
</style>
