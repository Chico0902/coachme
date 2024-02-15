<script setup>
import labels from '../atoms/CardLabel.vue'
import { getMainVideo } from '@/utils/api/coaching-api'
import { postEditToAI } from '@/utils/api/ai-api'
import Swal from 'sweetalert2'

const props = defineProps({
  label: {
    type: String,
    default: ''
  },
  caption: {
    type: [String, Number],
    default: ''
  },
  ratio: {
    type: Number,
    default: 16 / 9
  },
  video: {
    type: String,
    default: ''
  },
  coachingId: {
    type: String
  },
  videoId: {
    type: Number
  },
  liked: {
    type: Function,
    default: () => {}
  },
  visible: {
    type: Boolean,
    default: true
  }
})

const caption = typeof props.caption === 'number' ? Math.round(props.caption * 10) / 10 : props.caption

const setVideo = () => {
  getMainVideo(
    props.coachingId,
    props.videoId,
    (success) => {
      Swal.fire({
        icon: 'success',
        title: '대표 영상으로 등록되었습니다.'
      })
    },
    (fail) => {
      Swal.fire({
        icon: 'error',
        title: '대표 영상 등록 실패',
        text: '대표 영상 등록이 실패하였습니다.'
      })
    }
  )
}

const editToAI = () => {
  const urlKeys = props.video.split('/')
  const urlKey = urlKeys[urlKeys.length - 1]
  const noExtend = urlKey.split('.')[0]
  postEditToAI(
    { key: noExtend },
    (success) => console.log(success),
    (fail) => console.error(fail)
  )
}
</script>

<template>
  <q-card class="my-card">
    <!-- 상단 섹션 -->
    <q-item>
      <!-- 영상 -->
      <q-item-section>
        <q-video :ratio="`${props.ratio}`" :src="`${props.video}`"></q-video>
      </q-item-section>
    </q-item>
    <q-separator></q-separator>
    <!-- 하단 섹션 -->
    <q-item>
      <!-- 캡션과 라벨 -->
      <q-item-section>
        <labels class="coaching-name" :label="`${props.label}`"></labels>
        <labels caption :label="caption"></labels>
      </q-item-section>
      <!-- 대표 영상 설정 -->
    </q-item>
    <q-separator v-if="!visible"></q-separator>
    <q-item v-if="!visible">
      <q-item-section>
        <div class="buttons">
          <q-btn padding="xs" color="amber-7" icon="check" @click="setVideo">
            <q-tooltip class="bg-blue">대표 영상으로 설정하기</q-tooltip>
          </q-btn>
          <q-btn padding="xs" icon="download" color="amber-7">
            <q-tooltip class="bg-blue">다운로드</q-tooltip>
          </q-btn>
          <q-btn padding="xs" color="blue-10" @click="editToAI">
            <span class="material-symbols-outlined"> smart_toy </span>
            <q-tooltip class="bg-blue">AI에게 편집 요청하기</q-tooltip>
          </q-btn>
        </div>
      </q-item-section>
    </q-item>
  </q-card>
</template>

<style scoped>
.my-card {
  min-width: 15vw;
  max-width: 30vw;
}

.coaching-name {
  min-width: 150px;
}

.likebtn {
  display: flex;
  justify-content: right;
}

.buttons {
  display: flex;
  align-items: center;
  justify-content: space-around;
}

.material-symbols-outlined {
  font-size: 20px;
}
</style>
