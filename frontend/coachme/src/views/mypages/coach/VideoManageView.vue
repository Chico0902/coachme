<script setup>
import labels from '@/components/atoms/CardLabel.vue'
import CoachingCard from '@/components/molecules/CoachingCard.vue'
import AICreateCard from '@/components/molecules/AICreateCard.vue'
import Swal from 'sweetalert2'
import { ref, computed, onBeforeMount } from 'vue'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import { postEditToAI } from '@/utils/api/ai-api'
import { getVideoList, postNewVideo, getMyCoaching } from '@/utils/api/coach-api'
import { postAIVideo } from '@/utils/api/coaching-api'

/**
 * VARIABLES
 */

// 비디오 목록(api)
const videos = ref([])
const coachings = ref([])
const allCoachingOptions = computed(() => {
  if (coachings.value.length === 0) return [{ value: null, label: '등록된 영상이 없습니다.' }]
  const ret = []
  coachings.value.forEach((coaching) => ret.push({ value: coaching.id, label: coaching.name }))
  return ret
})
const registVideoCoachingId = ref('')
const newVideoFile = ref(null)
const newVideoFileName = ref('')

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
  console.log(grouped)
  return grouped
})

// 등록 모달
const show = ref(false)

// 본인 id
const longId = ref(-1)

// ai 등록용
const showWaitingEditGear = ref(false)
const saveEditModalOpen = ref(false)
const requestEditCoachingId = ref('')
const newAIVideoFileName = ref('')
const newAIVideoFileUrl = ref('')

/**
 * METHODS
 */

onBeforeMount(() => {
  longId.value = decodeToken(getAccessToken()).longId
  // 본인 아이디로 코칭 영상 리스트 조회
  getVideoList(
    longId.value,
    (success) => {
      videos.value = success.data.list
    },
    (fail) => {
      console.error(fail)
    }
  )
  // 모든 코칭들 조회
  getMyCoaching(
    longId.value,
    (success) => {
      coachings.value = success.data.list
    },
    (fail) => console.error(fail)
  )
})

const uploadNewVideo = () => {
  postNewVideo(
    longId.value,
    { videoFile: newVideoFile.value, fileName: newVideoFileName.value, coachingId: registVideoCoachingId.value },
    () => {
      show.value = false
      Swal.fire({
        icon: 'success',
        title: '영상 등록 완료',
        timer: 2000
      })
      setInterval(() => window.location.reload(), 2000)
    },
    (fail) => console.error(fail)
  )
}

const editToAI = (videoUrl, coachingId) => {
  const urlKeys = videoUrl.split('/')
  const urlKey = urlKeys[urlKeys.length - 1]
  const noExtend = urlKey.split('.')[0]
  showWaitingEditGear.value = true
  requestEditCoachingId.value = coachingId
  postEditToAI(
    { key: noExtend },
    (success) => {
      console.log(success)
      if (success.data.url == 'HighLight is zero.') {
        Swal.fire({
          icon: 'error',
          title: '오류 발생',
          text: '하이라이트를 찾지 못했습니다...'
        })
        showWaitingEditGear.value = false
      } else {
        saveEditModalOpen.value = true
        newAIVideoFileUrl.value = success.data.url
      }
    },
    (fail) => {
      console.error(fail)
      showWaitingEditGear.value = false
      Swal.fire({
        icon: 'error',
        title: '오류 발생',
        text: '영상 편집 중 오류 발생!'
      })
    }
  )
}

const uploadAIVideo = () => {
  postAIVideo(
    requestEditCoachingId.value,
    {
      coachId: longId.value,
      videoName: newAIVideoFileName.value,
      url: newAIVideoFileUrl.value
    },
    (success) => {
      console.log(success)
      saveEditModalOpen.value = false
      Swal.fire({
        icon: 'success',
        title: '영상 등록 완료',
        timer: 2000
      })
      setInterval(() => window.location.reload(), 2000)
    },
    (fail) => {
      console.log(fail)
      Swal.fire({
        icon: 'error',
        title: '오류 발생',
        text: '영상 업로드 중 오류 발생'
      })
    }
  )
}

const goEditPage = () => {
  window.open('https://i10a403.p.ssafy.io/edit')
}
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
          <a href="#" @click.prevent="goEditPage"><span data-hover="편집하기">편집하기</span></a>
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
          <template v-for="(video, index) in videosGroup" :key="video.videoId">
            <template v-if="showWaitingEditGear && requestEditCoachingId == video.coachingId && index == 0">
              <div class="coaching-card">
                <AICreateCard> </AICreateCard>
              </div>
            </template>
            <div class="coaching-card">
              <CoachingCard
                :label="video.videoName"
                :caption="caption"
                :ratio="ratio"
                :video="video.url"
                :visible="false"
                :coachingId="video.coachingId"
                :videoId="video.videoId"
                :isAI="video.isAI"
                @edit-to-ai-emit="editToAI"
              >
              </CoachingCard>
            </div>
          </template>
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
      <q-card-section class="bg-amber-5 text-white">
        <q-item>
          <q-item-section>
            <q-select
              v-model="registVideoCoachingId"
              :options="allCoachingOptions"
              label="코칭 이름"
              emit-value
              map-options
              @change="filterVideos"
              style="margin-bottom: 1rem"
            />
            <q-file filled bottom-slots v-model="newVideoFile" label="코칭 영상을 업로드하세요." counter max-files="1">
              <template v-slot:append>
                <q-icon
                  v-if="newVideoFile !== ''"
                  name="close"
                  @click.stop.prevent="newVideoFile = null"
                  class="cursor-pointer"
                />
                <q-icon v-else name="create_new_folder" @click.stop.prevent />
              </template>
            </q-file>
            <q-input standout="bg-primary text-white" v-model="newVideoFileName" label="영상 이름" />
          </q-item-section>
        </q-item>
      </q-card-section>
      <q-card-actions class="modal-option" align="right">
        <q-btn flat label="등록하기" color="primary" @click="uploadNewVideo" />
        <q-btn flat label="취소" color="primary" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
  <q-dialog v-model="saveEditModalOpen">
    <q-card>
      <q-card-section class="bg-amber-5 text-white">
        <q-item>
          <q-item-section>
            <labels caption label="완성된 영상의 이름을 설정하세요!"></labels>
            <q-input standout="bg-primary text-white" v-model="newAIVideoFileName" label="AI 편집영상 이름" />
          </q-item-section>
        </q-item>
      </q-card-section>
      <q-card-actions class="modal-option" align="right">
        <q-btn flat label="등록하기" color="primary" @click="uploadAIVideo" />
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

.menu {
  margin-right: 7vw;
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
}

.coaching-card {
  padding: 10px;
}

.element-with-scrollbar {
  width: 100%;
  margin-left: 1rem;
  display: flex;
  justify-content: space-between;
  flex-flow: nowrap;
}

.card-section {
  max-width: 30%;
  margin-left: 3vw;
}

.coaching-group-title {
  font-size: 20px;
  margin-top: 1rem;
  margin-left: 2rem;
  margin-bottom: 0.5rem;
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
