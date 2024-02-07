<script setup>
import Coaching from '@/components/molecules/CoachingCard.vue'
import { ref, computed } from 'vue';

const videos = ref([
  { coachingId: 1, coachingName: "abc", videoId: 1, videoUrl: "https://naver.com", videoName: "a" },
  { coachingId: 1, coachingName: "abc", videoId: 2, videoUrl: "https://naver.com", videoName: "b" },
  { coachingId: 2, coachingName: "cde", videoId: 3, videoUrl: "https://naver.com", videoName: "c" },
  { coachingId: 2, coachingName: "cde", videoId: 4, videoUrl: "https://naver.com", videoName: "d" },
  { coachingId: 3, coachingName: "fgh", videoId: 5, videoUrl: "https://naver.com", videoName: "e" },
]);

const coachingOptions = Array.from(new Set(videos.value.map(video => video.coachingId)))
  .map(coachingId => {
    const matchingVideo = videos.value.find(video => video.coachingId === coachingId);
    return { value: matchingVideo.coachingId, label: matchingVideo.coachingName };
  });


const selectedCoachingId = ref(null);

const filteredVideos = computed(() => {
  if (selectedCoachingId.value !== null) {
    return videos.value.filter(video => video.coachingId === selectedCoachingId.value);
  } else {
    return videos.value;
  }
});

const filterVideos = () => {
  // 선택된 coachingId로 필터링된 동영상 목록 업데이트
};

</script>
<template>
  <div>
    <div class="video-title">
      <div>영상보기</div>
    </div>
    <div class="coaching-dropdown">
      <q-select v-model="selectedCoachingId" :options="coachingOptions" label="Coaching Name" emit-value map-options
        @change="filterVideos" />
    </div>

    <div class="coaching-outside">
      <div v-for="video in filteredVideos" :key="video" class="coaching-card">
        <coaching :label="video.coachingName" :caption="caption" :ratio="ratio" :video="video" :visible="false">
        </coaching>
      </div>
    </div>

    <div class="pagenation">
      <div>페네자리</div>
    </div>
  </div>
</template>

<style scoped>
.video-title {
  width: 80%;
  margin: 5vh auto;
  font-weight: bolder;
}

.coaching-dropdown {
  width: 25%;
  margin-left: 42.3vw;
}

.coaching-outside {
  width: 100%;
  height: 30vh;
  margin: 5vh auto;
  display: flex;
  justify-content: center;
  overflow-x: auto;
  /* 가로 오버플로우 스크롤 추가 */
  overflow-y: hidden;
  /* 세로 오버플로우 숨김 */
  flex-wrap: nowrap;
  /* 강제로 한 줄에 모든 요소 배치 */
}

.coaching-card {
  width: 200px;
  height: 200px;
  margin: 30px;
}

.pagenation {
  margin: 5vh;
}
</style>
