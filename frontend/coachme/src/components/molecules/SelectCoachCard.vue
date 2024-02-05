<!-- 코치 리스트용 카드 
필요한 정보 : 코치 이름, 코치가 제공하는 코칭 종류, 별점, 코치 소개, 프로필 사진 링크
name : 코치 이름. 문자열. 기본값 없음
category : 코치가 제공하는 코칭 종류 등. 문자열. 기본값 없음. ex) 축구 코칭
rating : 별점. 문자열. 기본값 없음.
desc : 코치 자기소개. 문자열. 기본값 없음. 5줄 이내로 권장
img : 프로필 사진 링크. 문자열. 기본값 없음.
-->

<script setup>
import profile from '../atoms/ProfileImage.vue'
import labels from '../atoms/CardLabel.vue'
import Button from '@/components/atoms/CustomButton.vue'
import { ref, onMounted, watch } from 'vue'
import { useCounterStore } from "../../stores/chat-status";

const store = useCounterStore();

const { requestDm } = store
// 피니아에 저장된 채팅 활성화 함수

const props = defineProps({
  coachName: {
    type: String,
    default: ''
  }, // 코치 이름
  coaching: {
    type: String,
    default: ''
  }, // 제공 코칭
  rating: {
    type: String, 
    default: ''
  }, // 별점
  reviewCount: {
    type: String
  }, // 리뷰수
  img: {
    type: String,
    default: ''
  } // 코치 프로필 사진
})

const stars = ref(0)

onMounted(() => {
  stars.value = props.rating
}) // 별점을 props의 값으로 바꾸기

watch(() => stars.value, (newState) => {
  stars.value = newState;
}); // 별점 값이 바뀌면 갱신하기

</script>

<template>
  <!-- 프로필 사진 영역 -->
  <q-item>
    <q-item-section avatar style="padding-right: 0px;" class="flex flex-center">
      <profile :img="props.img" size="135px"></profile>
    </q-item-section>
  </q-item>

  <!-- 코치 이름 영역 -->
  <q-item style="margin-top: -1vh;">
    <q-item-section>
      <labels :label="`${props.coachName}`" class="text-black text-bold" style="padding: -2vw;"></labels>
    </q-item-section>
  </q-item>

  <!-- 제공 코칭과 별점 영역-->
  <q-item class="justify-evenly">
    <!-- 공간 분리-->
    <q-item-section>
      <q-space></q-space>
    </q-item-section>
    <!-- 별점 영역 -->
    <q-item-section style="min-width: fit-content;">
      <q-rating size="18px" v-model="stars" :max="5" color="orange" readonly></q-rating>
      <span style="font-size: 15px; color: black; font-weight: 600;">{{ rating }} ({{ reviewCount }}) </span>
    </q-item-section>
  </q-item>

    <!-- 대표 코칭 이름 -->
    <q-item class="justify-evenly">
      <!-- 코칭 이름 -->
      <q-item-section style="min-width: fit-content;">
        <labels :label="`${props.coaching}`" class="text-black text-bold"></labels>
      </q-item-section>
    </q-item>

  <!-- 코치 버튼 영역 -->
  <q-item class="coach-button">
    <q-item-section vertical>
      <Button label="포트폴리오 보기" style="background-color: #FCBF17; color: white"></Button>
    </q-item-section>
    <q-item-section>
      <Button label="채팅하기" style="background-color: #1a66da; color: white; height: 8vh;" 
      @click="requestDm()"></Button>
    </q-item-section>
  </q-item>
</template>

<style scoped>
</style>