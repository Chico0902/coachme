<script setup>
import Coaching from '@/components/molecules/CoachingCard.vue'
import CustomButton from '@/components/atoms/CustomButton.vue'
import { ref, computed, onBeforeMount } from 'vue'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import { getVideoList } from '@/utils/api/coach-api'

/**
 * VARIABLES
 */

// 비디오 목록(api)
const videos = ref([])

// 검색 옵션
const coachingOptions = computed(() => {
  return [
    { value: null, label: '전체보기' },
    ...Array.from(new Set(videos.value.map((video) => video.coachingId))).map((coachingId) => {
      const matchingVideo = videos.value.find((video) => video.coachingId === coachingId)
      return { value: matchingVideo.coachingId, label: matchingVideo.coachingName }
    })
  ]
})
const selectedCoachingId = ref(null)

// 드롭다운 메뉴에 필요한 제목과 id 리스트 - 중복 제거

// 드롭다운 메뉴

// 선택한 코칭의 id

const groupedFilteredVideos = computed(() => {
  const filtered =
    selectedCoachingId.value !== null
      ? videos.value.filter((video) => video.coachingId === selectedCoachingId.value)
      : videos.value

  const grouped = {}
  filtered.forEach((video) => {
    if (!grouped[video.coachingName]) {
      grouped[video.coachingName] = []
    }
    grouped[video.coachingName].push(video)
  })
  return grouped
})
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
    <div class="video-list-top">
      <div class="main-title">
        나의 영상관리
        <div class="editor-detail">나만의 영상을 편집하고, 게시해보세요!</div>
      </div>
      <div class="menu">
        <q-btn label="영상 등록" color="amber-7" text-color="black"></q-btn>
      </div>
    </div>
    <div class="coaching-dropdown">
      <q-select
        v-model="selectedCoachingId"
        :options="coachingOptions"
        label="코칭 이름"
        emit-value
        map-options
        @change="filterVideos"
      />
    </div>

    <div v-for="(videosGroup, coachingName) in groupedFilteredVideos" :key="coachingName" class="card-section">
      <div class="coaching-group-title">{{ coachingName }}</div>
      <div class="coaching-outside element-with-scrollbar">
        <div v-for="video in videosGroup" :key="video.videoId" class="coaching-card">
          <coaching :label="video.videoName" :caption="caption" :ratio="ratio" :video="video.url" :visible="false"
            :coachingId="video.coachingId" :videoId="video.videoId">
          </coaching>
        </div>
      </div>
    </div>

    <div v-if="!groupedFilteredVideos || Object.keys(groupedFilteredVideos).length === 0"
      class="no-video-message card-section">
      해당하는 영상이 없습니다.
    </div>
  </div>
</template>

<style scoped>

.video-list-top {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.main-title {
  display: inline-block;
  font-size: 2rem;
  margin-top: 1.5rem;
  margin-left: 3rem;
  margin-bottom: 1rem;
}

.editor-detail {
  margin-bottom: 1rem;
  margin-left: 0.5rem;
  color: #034c8c;
  font-size: 0.9rem;
  margin-bottom: 0.5rem;
}

.menu {
  margin-right: 7vw;
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
