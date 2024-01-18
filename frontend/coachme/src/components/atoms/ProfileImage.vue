<!-- 프로필 사진 컴포넌트 
필요한 정보 : 프로필 사진 크기, 이미지 링크
size : 프로필 사진 크기. 문자열. 기본값 50px. 추천값 : px, rem, (xs|sm|md|lg|xl) 등
src : 이미지 링크. 기본값 고양이 사진
-->
<script setup>
const props = defineProps({
  size : { // 프로필 사진 크기
    type : String,
    default : "50px"
  },
  img : {
    type : String, // 프로필 사진 링크
    default : "https://src.hidoc.co.kr/image/lib/2022/11/15/1668491763670_0.jpg"
  }
});
</script>

<template>
  <q-avatar :size="`${props.size}`">
    <img :src="`${props.img}`" alt="profile">
  </q-avatar>
</template>

<style scoped></style>