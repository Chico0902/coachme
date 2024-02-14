<script setup>
import CoachingCard from '@/components/molecules/CoachingCard.vue'
import { ref, computed, onBeforeMount } from 'vue'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import { getVideoList } from '@/utils/api/coach-api'
import VideoUploader from '@/components/molecules/videoManage/VideoUploader.vue'

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

// 각 코칭 별로 코칭 영상 리스트를 추출
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

// 등록 모달
const show = ref(false)

/**
 * METHODS
 */

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
  <div class="main">
    <div class="main-title">
      나의 영상관리
      <div class="editor-detail">나만의 영상을 편집하고, 게시해보세요!</div>
    </div>
    <div class="menu-bar">
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
      <div class="menu">
        <div class="SMN_effect-42">
          <RouterLink :to="{ name: 'Desktop-5-6' }"><span data-hover="편집하기">편집하기</span></RouterLink>
        </div>
        <div class="SMN_effect-42">
          <a href="#" @click.prevent="show = true"><span data-hover="등록하기">등록하기</span></a>
        </div>
      </div>
    </div>

    <div class="coaching-outside">
      <div v-for="(videosGroup, coachingName) in groupedFilteredVideos" :key="coachingName" class="card-section">
        <div class="coaching-group-title">
          {{ coachingName }}
        </div>
        <div class="element-with-scrollbar">
          <div v-for="video in videosGroup" :key="video.videoId" class="coaching-card">
            <CoachingCard
              :label="video.videoName"
              :caption="caption"
              :ratio="ratio"
              :video="video.url"
              :visible="false"
              :coachingId="video.coachingId"
              :videoId="video.videoId"
            >
            </CoachingCard>
          </div>
        </div>
      </div>
    </div>
    <div
      v-if="!groupedFilteredVideos || Object.keys(groupedFilteredVideos).length === 0"
      class="no-video-message card-section"
    >
      해당하는 영상이 없습니다.
    </div>
  </div>
  <q-dialog v-model="show">
    <q-card>
      <q-card-section class="bg-primary text-white">
        <q-item>
          <q-item-section>
            <VideoUploader @uploadVideo="uploadNewVideo" />
            <q-item-label class="coaching-name">{{ coachingName }}</q-item-label>
            <q-item-label caption class="coaching-detail">{{ coachingSummary }}</q-item-label>
          </q-item-section>
        </q-item>
      </q-card-section>
      <q-card-actions class="modal-option" align="right">
        <q-btn flat label="등록하기" color="primary" @click="router.push(`/search/coaching/detail/${coachingId}`)" />
        <q-btn flat label="취소" color="primary" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
.menu-bar {
  display: flex;
  justify-content: space-between;
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

.edit-video {
  text-align: center;
}

.edit-video {
  text-align: center;
}

.coaching-dropdown {
  margin-left: 5rem;
  width: 25%;
}

.coaching-outside {
  max-width: 100%;
  height: 38vh;
  margin-top: 2rem;
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
}

.coaching-card {
  margin: 30px 15px 30px;
}

.card-section {
  max-width: 30%;
  margin-left: 3vw;
}

.coaching-group-title {
  font-size: 20px;
  text-align: center;
  margin-bottom: 0;
}

.element-with-scrollbar {
  overflow: hidden;
}

.element-with-scrollbar:hover {
  overflow: auto;
}
.menu {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 5rem;
}
.menu a {
  color: rgba(0, 0, 0, 0.8);
  font-size: 1.2rem;
  font-weight: 400;
  padding: 15px 25px;
  position: relative;
  display: inline-block;
  text-decoration: none;
  text-transform: uppercase;
}
.SMN_effect-42 a {
  position: relative;
}

.SMN_effect-42 a:before {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 1.5rem;
  background-color: #034c8c;
  transform-origin: 100% 50%;
  transform: scale(0, 1);
  top: 0;
  left: 0;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1);
}

.SMN_effect-42 a:after {
  content: '';
  position: absolute;
  width: 100%;
  height: 2px;
  background-color: 034c8c (0, 0, 0);
  left: 0;
  bottom: 0;
  transform-origin: 0% 50%;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0.2s;
}

.SMN_effect-42 a span {
  position: relative;
}

.SMN_effect-42 a span:after {
  content: attr(data-hover);
  position: absolute;
  left: 0;
  width: 100%;
  height: 100%;
  transition: opacity 0.2s cubic-bezier(0.33, 0.91, 0.42, 1) 0s;
}

.SMN_effect-42 a:hover:before {
  transform: scale(1);
  transform-origin: 0 50%;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0.2s;
}

.SMN_effect-42 a:hover:after {
  transform: scale(0, 1);
  transform-origin: 100% 50%;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0s;
}

.SMN_effect-42 a:hover span:after {
  color: #ffffff;
  transition: color 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0.2s;
}
</style>
