<!-- button 컴포넌트 
필요한 정보 : 라벨, 크기, 버튼 색, 폰트색
label : 버튼위에 작성될 라벨. 문자열. 기본값 "버튼"
size : 버튼의 라벨 글자 크기. 문자열. 기본값 없음. 추천값 : px, rem, (xs|sm|md|lg|xl) 
background : 버튼 색상. 색상코드를 문자열로. 기본값 #FCBF17
color : 글자 색상. 색상코드를 문자열로. 기본값 #FFFFFF
-->

<script setup>
import { reactive } from "vue";

const props = defineProps({
  label: {  // 버튼 라벨
    type: String,
    default: "버튼"
  },

  size: { // 버튼 크기
    type: String,
    default: ""
  },
  background: {
    type: String,
    default: "#FCBF17"
  },
  color: {
    type: String,
    default: "#FFFFFF"
  },
});

const styleObject = reactive({
  background: props.background,
  color: props.color
}) // 인라인 스타일문, 버튼 스타일에 적용

</script>

<template>
  <q-btn :size="`${props.size}`" :style="styleObject" :label="`${props.label}`">
  </q-btn>
</template>

<style scoped></style>