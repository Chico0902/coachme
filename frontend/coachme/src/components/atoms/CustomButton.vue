<!-- button 컴포넌트 
필요한 정보 : 이름, 라벨, 크기, 버튼 색, 글자 색상
name : 버튼에 붙일 이름, 클래스 이름과 비슷함. 문자열. 기본값 name
label : 버튼위에 작성될 라벨. 문자열. 기본값 없음
size : 버튼의 라벨 글자 크기. 문자열. 기본값 없음. 추천값 : px, rem, (xs|sm|md|lg|xl) 
background : 버튼 색상. 색상코드를 문자열로. 기본값 없음
color : 글자 색상. 색상코드를 문자열로. 기본값 #000000
-->

<script setup>
import { reactive } from 'vue'

const props = defineProps({
  name: {
    // 버튼에 붙일 이름
    type: String,
    default: 'name'
  },
  label: {
    // 버튼 라벨
    type: String,
    default: ''
  },
  size: {
    // 버튼 크기
    type: String,
    default: ''
  },
  background: {
    // 버튼 색상
    type: String,
    default: ''
  },
  color: {
    // 글자 색상
    type: String,
    default: '#000000'
  }
})

const styleObject = reactive({
  'background-color': props.background,
  color: props.color
}) // 인라인 스타일문, 버튼 스타일에 적용
</script>

<template>
  <q-btn :size="`${props.size}`" :name="`${props.name}`" :style="styleObject" :label="`${props.label}`">
    <slot name:append></slot>
  </q-btn>
</template>

<style scoped></style>
