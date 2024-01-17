<!-- 캐러셀 버튼 컴포넌트
필요한 정보 : 이미지 정보
list : 이미지 링크 객체. Object로. 기본값 : quasar 사이트 예시 사진
-->
<script setup>
import { ref } from "vue";

const props = defineProps({
  list : { // 이미지 링크 객체
    type : Object,
    default : () => {
      return ["https://cdn.quasar.dev/img/mountains.jpg", "https://cdn.quasar.dev/img/parallax1.jpg", "https://cdn.quasar.dev/img/parallax2.jpg", "https://cdn.quasar.dev/img/quasar.jpg"]
    }
  }
});

const slide = ref(1)
const autoplay = ref(true)
</script>

<template>
      <q-carousel animated v-model="slide" navigation infinite :autoplay="autoplay" arrows transition-prev="slide-right"
        transition-next="slide-left" @mouseenter="autoplay = false" @mouseleave="autoplay = true" class="rounded-borders">
          <q-carousel-slide v-for="(slide, index) in props.list" :key="slide" :name="index" :img-src="slide"></q-carousel-slide>
      </q-carousel>
</template>

<style scoped></style>