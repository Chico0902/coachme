<!-- 이미지 컴포넌트
경로 연결 이슈 있음
필요한 정보 : 이미지 경로 또는 주소
src : 이미지 주소 또는 경로. 문자열. 기본값 없음
-->

<script setup>
const props = defineProps({
  src : {  // 이미지 경로 또는 주소
    type : String,
    default : ""
  }
});
</script>

<template>
  <img :src="`${props.src}`">
</template>

<style scoped>

</style>