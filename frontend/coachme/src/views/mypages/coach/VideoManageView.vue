<script setup>
import Coaching from '@/components/molecules/CoachingCard.vue'
import { ref, computed, onBeforeMount } from 'vue';
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import { getVideoList } from '@/utils/api/coach-api'

const videos = ref([])
const selectedCoachingId = ref(null);
// 선택한 코칭의 id

const coachingOptions = computed(() => {
  return [{ value: null, label: "전체보기" }, ...Array.from(new Set(videos.value.map(video => video.coachingId)))
    .map(coachingId => {
      const matchingVideo = videos.value.find(video => video.coachingId === coachingId);
      return { value: matchingVideo.coachingId, label: matchingVideo.coachingName };
    })];
});
// 드롭다운 메뉴에 필요한 제목과 id 리스트 - 중복 제거

const groupedFilteredVideos = computed(() => {
  const filtered = selectedCoachingId.value !== null ?
    videos.value.filter(video => video.coachingId === selectedCoachingId.value) :
    videos.value;

  const grouped = {};
  filtered.forEach(video => {
    if (!grouped[video.coachingName]) {
      grouped[video.coachingName] = [];
    }
    grouped[video.coachingName].push(video);
  });
  return grouped;
});
// 각 코칭 별로 코칭 영상 리스트를 추출

  onBeforeMount(() => {
    const longId = decodeToken(getAccessToken()).longId
    // 본인 아이디로 코칭 영상 리스트 조회
    getVideoList(
      longId,
      (success) => {
        console.log(success)
        videos.value = success.data.list
      },
      (fail) => {
        console.log(fail)
      }
    )
  })

</script>
<template>
  <div>
    <div class="video-title">
      <div>영상보기</div>
    </div>
    <div class="coaching-dropdown">
      <q-select v-model="selectedCoachingId" :options="coachingOptions" 
      label="코칭 이름" emit-value map-options @change="filterVideos" />
    </div>

    <div v-for="(videosGroup, coachingName) in groupedFilteredVideos" :key="coachingName" class="card-section">
      <div class="coaching-group-title">{{ coachingName }}</div>
      <div class="coaching-outside element-with-scrollbar">
        <div v-for="video in videosGroup" :key="video.videoId" class="coaching-card">
          <coaching :label="video.videoName" :caption="caption" :ratio="ratio" 
          :video="video.url" :visible="false" :coachingId="video.coachingId" :videoId="video.videoId">
          </coaching>
        </div>
      </div>
    </div>

    <div v-if="!groupedFilteredVideos || Object.keys(groupedFilteredVideos).length === 0" class="no-video-message card-section">
      해당하는 영상이 없습니다.
    </div>

  </div>
</template>

<style scoped>
.video-title {
  width: 80%;
  margin: 5vh 5vh 1vh;
  font-weight: bolder;
  font-size: 26px;
}

.edit-video {
  text-align: center;
}

.coaching-dropdown {
  width: 25%;
  margin-left: 42.3vw;
}

.coaching-outside {
  width: 100%;
  height: 38vh;
  margin-top: 0.2vh;
  margin-bottom: 2vh;
  display: flex;
  justify-content: flex-start;
  overflow-x: auto;
  overflow-y: hidden;
}

.coaching-card {
  margin: 30px 15px 30px;
}

.card-section {
  width: 90%;
  margin-left: 3vw;
}

.coaching-group-title {
  font-size: 20px;
}

.element-with-scrollbar {
  overflow: hidden;
}

.element-with-scrollbar:hover {
  overflow: auto;
}
</style>
