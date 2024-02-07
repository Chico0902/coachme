<!-- 캐러셀 버튼 컴포넌트
필요한 정보 : 이미지 링크
list : 이미지 링크 객체. Object로. 기본값 : quasar 사이트 예시 사진
-->
<script setup>
import { ref } from 'vue'

const props = defineProps({
  list: {
    // 이미지 링크 객체
    type: Object,
    default: () => {
      return [
        'https://velog.velcdn.com/images/lee02g29/post/469a9596-3c04-4d38-b0f4-53d929b40deb/image.png',
        'https://velog.velcdn.com/images/lee02g29/post/9fb3c705-655d-471e-b277-46cbb4e8a629/image.png',
        'https://velog.velcdn.com/images/lee02g29/post/0d9763f9-32c7-4c6e-ad99-65db4cec46c5/image.png',
        'https://velog.velcdn.com/images/lee02g29/post/4e161483-2f52-4953-933d-0f1d512f0017/image.png',
        'https://velog.velcdn.com/images/lee02g29/post/3d55bb1f-598d-4f7d-a03b-625f78bd3930/image.png',
        'https://velog.velcdn.com/images/lee02g29/post/9d867b97-a790-41ef-8962-2b748306b39d/image.png',
      ]
    }
  }
})

const slide = ref(0)
const autoplay = ref(true)
</script>

<template>
  <q-carousel
    animated
    v-model="slide"
    navigation
    infinite
    :autoplay="autoplay"
    arrows
    transition-prev="slide-right"
    transition-next="slide-left"
    @mouseenter="autoplay = false"
    @mouseleave="autoplay = true"
    class="rounded-borders"
    
  >
    <!-- 캐러셀 이미지 공간 -->
    <q-carousel-slide
      v-for="(slide, index) in props.list"
      :key="slide"
      :name="index"
      :img-src="slide"
      style="background-size: contain; background-repeat: no-repeat;"
    ></q-carousel-slide>
  </q-carousel>
</template>

<style scoped>
</style>
