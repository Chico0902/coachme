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
import { ref, onMounted, watch } from 'vue'

const props = defineProps({
  name: {
    type: String,
    default: ''
  }, 
  category: {
    type: String,
    default: ''
  },
  rating: {
    type: String, 
    default: ''
  },
  desc: {
    type: String,
    default: ''
  }, 
  img: {
    type: String,
    default: ''
  }
})

const stars = ref(0)

onMounted(() => {
  stars.value = props.rating
})

watch(() => stars.value, (newState) => {
  stars.value = newState;
});

</script>

<template>
  <!-- 프로필 사진 영역 -->
  <q-item>
    <q-item-section avatar style="padding-right: 0px;">
      <profile :img="props.img" size="150px"></profile>
    </q-item-section>
  </q-item>

  <!-- 코치 이름 영역 -->
  <q-item>
    <q-item-section>
      <labels :label="`${props.name}`" class="text-black text-bold"></labels>
    </q-item-section>
  </q-item>

  <!-- 제공 코칭과 별점 영역-->
  <q-item class="justify-evenly">
    <!-- 제공 코칭 -->
    <q-item-section style="min-width: fit-content;">
      <labels :label="`${props.category}`" class="text-black text-bold"></labels>
    </q-item-section>
    <!-- 공간 분리-->
    <q-item-section>
      <q-space></q-space>
    </q-item-section>
    <!-- 별점 영역 -->
    <q-item-section style="min-width: fit-content;">
      <q-rating size="18px" v-model="stars" :max="5" color="primary" readonly></q-rating>
      <labels :label="`${props.rating}`" class="text-black text-bold"></labels>
    </q-item-section>
  </q-item>

  <!-- 코치 소개 영역 -->
  <q-item>
    <q-item-section>
      <labels caption :label="`${props.desc}`" style="max-height: 5vh;" class="text-black"></labels>
    </q-item-section>
  </q-item>

  <!-- 하단 공간 확보 -->
  <q-item>
    <q-item-section>
      <q-space></q-space>
    </q-item-section>
  </q-item>
</template>

<style scoped>
</style>