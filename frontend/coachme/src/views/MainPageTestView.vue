<script setup>
import { RouterLink, RouterView } from 'vue-router'
import card from '../components/molecules/CoachCard.vue'
import coaching from '../components/molecules/CoachingCard.vue'
import carousel from '../components/atoms/Carousel.vue'
import search from '../components/molecules/MainSearch.vue'
import navbar from '../components/molecules/NavBar.vue'
import loginNavbar from '../components/molecules/LoginNavBar.vue'
import footerBar from '../components/molecules/Footer.vue'
import category from '../components/molecules/Category.vue'

import { reactive } from "vue";

const input = reactive({})
// 선택한 드롭다운 메뉴와 입력값

const searchData = (data) => {
  input.value = data
  // console.log(input.value.menu)
  // console.log(input.value.input)
} // 검색 컴포넌트에서 받아온 입력 값. 카테고리, 검색어 순

const liked1 = () => {
  console.log("Heart1")
}
// 찜콩 버튼에 보낼 함수, 클릭시 발생시킬 이벤트 작성

const bColor = "#FCBF17"
const dColor = "blue-10"
const label0 = "검색하기"
const list = ["코치명", "코칭제목"]
// 검색 컴포넌트의 버튼 색상, 드롭다운 색상, 버튼 라벨, 드롭다운 메뉴 순

const label = "whiteCat"
const caption = "Cat is white"
const image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSq75DiUEnXV_lTKsYK7oLxdoj0cyTeSp6329bGs93wHQ&s"
// 코치(+코칭) 카드 라벨, 카드 캡션, 카드 이미지

const ratio = 16 / 9
const video = "https://www.youtube.com/embed/k3_tw44QsZQ?rel=0"
// 코칭 카드 영상 비율, 주소

</script>

<template>
  <!-- 로그인 후 nav -->
  <loginNavbar></loginNavbar>
  <div>
    <!-- 로그인 이전 nav -->
    <navbar></navbar>

    <!-- 검색창 -->
    <search :label="label0" :dColor="dColor" :bColor="bColor" :list="list" @search-data="searchData"></search>

    <!-- 카테고리 -->
    <div class="row q-gutter-md">
      <category></category>
    </div>

    <!-- 인기코치 카드 -->
    <div class="row q-gutter-md">
      <card :label="label" :caption="caption" :img="image" :liked="liked1"></card>
      <card :label="label" :caption="caption" :img="image" :liked="liked1"></card>
    </div>

    <!-- 인기코칭 카드-->
    <div class="row q-gutter-md">
      <coaching :label="label" :caption="caption" :ratio="ratio" :video="video" :liked="liked1"></coaching>
      <coaching :label="label" :caption="caption" :ratio="ratio" :video="video" :liked="liked1"></coaching>
    </div>

    <!-- 캐러셀 -->
    <div>
      <carousel></carousel>
    </div>

    <!-- footer -->
    <div>
      <footerBar></footerBar>
    </div>
  </div>
</template>

<style scoped>
</style>
