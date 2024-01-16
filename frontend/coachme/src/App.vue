<script setup>
import { RouterLink, RouterView } from 'vue-router'
import dropdown from '../src/components/atoms/dropdown.vue'
import buttons from '../src/components/atoms/button.vue'
import inputs from '../src/components/atoms/input.vue'
import like from '../src/components/atoms/like.vue'
import profile from '../src/components/atoms/profile_image.vue'
import card from '../src/components/atoms/card.vue'
import carousel from '../src/components/atoms/carousel.vue'

import search from '../src/components/molecules/search.vue'


import { ref } from "vue";

const label1 = "button1"
const color1 = "#004C98"
const font1 = "#FFFFFF"
const size1 = "20px"
/* 첫번째 버튼 예시
라벨, 버튼색, 글자색 순 */

const label2 = "input name"
const hint1 = "please input your name"
/* 첫번째 input 폼 예시문
라벨, 힌트 */

const color2 = "#FCBF17"
const font2 = "#00000"
const size2 = "30px"
/* 두번째 버튼 예시
버튼색, 글자색 순
라벨은 input 폼의 예시문을 사용 */

const label3 = "분류 선택"
const list1 = ["코치명", "코칭 제목"]
/* 드롭다운 예시
라벨, 색상, 리스트 순
*/

const liked = () => {
  console.log("Heart")
}
// 하트 버튼에 보낼 함수

const size3 = "100px"
// 프로필 사진 크기

const width = "100%"
const maxWidth = "300px"

</script>

<template>
  <search></search>
  <div>
    <like :clicked="liked"></like>
</div>
<div>
  <profile :size="size3"></profile>
</div>
<div>
  <card :width="width"></card>
</div>
<div>
  <carousel></carousel>
</div></template>

<style scoped></style>
