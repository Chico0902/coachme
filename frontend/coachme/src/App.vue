<script setup>
import { RouterLink, RouterView } from 'vue-router'
import card from './components/molecules/coach-card.vue'
import coaching from './components/molecules/coaching-card.vue'
import carousel from '../src/components/atoms/carousel.vue'
import search from '../src/components/molecules/search.vue'
import navbar from '../src/components/molecules/nav-bar.vue'
import loginNavbar from '../src/components/molecules/login-nav-bar.vue'
import footerBar from '../src/components/molecules/footer.vue'
import category from '../src/components/molecules/category.vue'

import { ref } from "vue";

const liked1 = () => {
  console.log("Heart1")
}
// 하트 버튼에 보낼 함수

const bColor = "#FCBF17"
const dColor = "blue-10"
const label0 = "검색하기"
// 검색 컴포넌트의 버튼 색상, 드롭다운 색상, 버튼 라벨 순

const label = "whiteCat"
const caption = "Cat is white"
const image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSq75DiUEnXV_lTKsYK7oLxdoj0cyTeSp6329bGs93wHQ&s"
// 카드 라벨, 카드 캡션, 카드 이미지

const ratio = 16 / 9
const video = "https://www.youtube.com/embed/k3_tw44QsZQ?rel=0"
</script>

<template>
  <loginNavbar></loginNavbar>
  <div>
    <navbar></navbar>
    <search :label="label0" :dColor="dColor" :bColor="bColor"></search>
    <category></category>
    <div class="row q-gutter-md">
      <card :label="label" :caption="caption" :img="image" :liked="liked1"></card>
      <card :label="label" :caption="caption" :img="image" :liked="liked1"></card>
    </div>
    <div>
      <coaching :label="label" :caption="caption" :ratio="ratio" :video="video" :liked="liked1"></coaching>
    </div>
    <div>
      <carousel></carousel>
    </div>
    <div>
      <footerBar></footerBar>
    </div>
  </div>
</template>

<style scoped></style>
