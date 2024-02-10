<!-- 코칭 카드 컴포넌트
필요한 정보 : 카드 라벨, 카드 캡션, 영상 비율, 영상 링크, 찜콩버튼 함수
label : 카드에 있는 라벨으로 짙은 색 글자. 문자열. 기본값 없음
caption : 카드에 있는 문자로 옅은 색 글자, 설명과 같은 기능. 문자열. 기본값 없음
ratio : 카드에 포함될 영상의 비율. 숫자. 기본값 16/9
video : 카드에 포함될 영상의 주소. 문자열. 기본값 없음
liked : 찜콩버튼 클릭시 발생할 함수. function. 기본값 없음
-->

<script setup>
import labels from '../atoms/CardLabel.vue'
import like from '../atoms/CustomLike.vue'
import { getMainVideo } from '@/utils/api/coaching-api'

const props = defineProps({
  label: {
    type: String,
    default: ''
  },
  caption: {
    type: String,
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

const setVideo = () => {
  getMainVideo(
    props.coachingId,
    props.videoId,
    (success) => {
      console.log(success.data)
      alert('대표 영상으로 등록되었습니다.')
    },
    (fail) => {
      console.log(fail.data)
      alert('대표 영상 등록이 실패하였습니다.')
    }
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
        <labels caption :label="`${props.caption}`"></labels>
      </q-item-section>
      <!-- 찜콩 버튼-->
      <q-item-section v-if="visible" class="likebtn">
        <like class="likebtn" :clicked="liked"></like>
      </q-item-section>
      <!-- 대표 영상 설정 -->
      <q-item-section v-if="!visible">
        <q-btn padding="xs" color="amber-7" icon="check" @click="setVideo">
          <q-tooltip class="bg-blue">대표 영상으로 설정하기</q-tooltip>
        </q-btn>
      </q-item-section>
    </q-item>
  </q-card>
</template>

<style scoped>
.my-card {
  width: 100%;
  min-width: 15vw;
}
.coaching-name {
  min-width: 150px;
}
.likebtn {
  display: flex;
  justify-content: right;
}
</style>
