<!-- 프로필 사진 컴포넌트 
필요한 정보 : 라벨, 크기, 버튼 색, 폰트색
size : 버튼의 크기. 문자열. 기본값 없음. 추천값 : px, rem, (xs|sm|md|lg|xl) 등
src : 이미지 링크. 기본값 없음
-->
<script setup>
const props = defineProps({
  size: {
    type: String,
    default: "50px"
  },
});
</script>

<template>
  <q-avatar :size="`${props.size}`">
    <img src="https://src.hidoc.co.kr/image/lib/2022/11/15/1668491763670_0.jpg">
  </q-avatar>
</template>

<style scoped></style>