<script setup>
import { onBeforeMount, ref } from 'vue'
import { getMyCoaching } from '@/utils/api/coach-api'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import router from '@/router'

/**
 * VARIABLES
 */

const coachings = ref([])

// 코칭 상세보기
const show = ref(false)
const innerHtml = ref('')

/**
 * METHODS
 */

const showPortfolio = (htmlDocs) => {
  innerHtml.value = htmlDocs
  show.value = true
}

onBeforeMount(() => {
  const longId = decodeToken(getAccessToken()).longId
  // 본인 아이디로 본인의 코칭 조회
  getMyCoaching(
    longId,
    (success) => {
      coachings.value = success.data.list
    },
    (fail) => {
      console.error(fail)
    }
  )
})
</script>
<template>
  <div class="coaching-list-top">
    <div class="main-title">
      나의 코칭관리
      <div class="editor-detail">
        <template v-if="coachings != undefined && coachings.length > 0">
          이름을 누르면 상세페이지로 이동하며, 상세 버튼으로 세부정보를 확인할 수 있습니다.
        </template>
        <template v-else>
          <span style="font-size: 1.3rem; margin-right: 0.5rem">코칭 만들기</span> 버튼을 통해 지금 나만의 코칭을
          등록하세요!
        </template>
      </div>
    </div>
    <div class="menu SMN_effect-42">
      <RouterLink :to="{ name: 'Desktop-5-4' }"><span data-hover="코칭만들기">코칭만들기</span></RouterLink>
    </div>
  </div>
  <div class="coaching-list-main">
    <template v-if="coachings != undefined && coachings.length > 0">
      <q-list class="list" v-ripple v-for="(coaching, index) in coachings" :key="index">
        <q-item>
          <q-item-section>
            <a href="#" @click.prevent="router.push(`/search/coaching/detail/${coaching.id}`)">
              <q-item-label class="coaching-name">{{ coaching.name }}</q-item-label>
              <q-item-label caption class="coaching-caption">{{ coaching.summary }}</q-item-label>
            </a>
          </q-item-section>

          <q-item-section side top>
            <q-item-label caption class="coaching-caption">{{
              coaching.main.replace(/^[a-z]/, (char) => char.toUpperCase())
            }}</q-item-label>
            <q-item-label class="category">{{
              coaching.sub.replace(/^[a-z]/, (char) => char.toUpperCase())
            }}</q-item-label>
          </q-item-section>

          <q-item-section side>
            <button class="custom-btn btn-7" @click.prevent="showPortfolio(coaching.htmlDocs)">코칭 상세</button>
          </q-item-section>
        </q-item>
      </q-list>
    </template>
  </div>
  <q-dialog v-model="show">
    <q-card>
      <q-card-section>
        <div class="text-h6">코칭 상세보기</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <div v-html="innerHtml"></div>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="OK" color="primary" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
@import url(https://fonts.googleapis.com/css?family=Lato:400,300,700,900);
a {
  text-decoration: none;
  color: #034c8c;
}
.editor-detail {
  margin-bottom: 1rem;
  margin-left: 0.5rem;
  color: #034c8c;
  font-size: 0.9rem;
  margin-bottom: 0.5rem;
}

.main-title {
  display: inline-block;
  font-size: 2rem;
  margin-top: 1.5rem;
  margin-left: 3rem;
  margin-bottom: 1rem;
}
.intro-box {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
.intro-img {
  margin: 20px;
  display: inline-block;
}

.intro-detail {
  display: inline-block;
  align-items: center;
}
.coaching-list-top {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.coaching-list-main {
  width: 100%;
  margin: 2vh 0;
  padding-left: 2rem;
}
.coaching-name {
  margin: 0;
  margin-bottom: 0.3rem;
  font-size: 1.3rem;
}
.category {
  color: black;
}
.list {
  padding: 1rem;
  border-radius: 0.5rem;
  align-items: center;
}
.list:hover {
  background-color: #034c8c;
  color: #fff;
}
.list:hover .category {
  color: #fff;
  transition: all 0.2s;
}
.list:hover a {
  color: #fff;
  transition: all 0.2s;
}
.menu {
  display: flex;
  align-items: center;
  margin: 1rem 2rem;
  min-width: 10rem;
}
.menu a {
  color: rgba(0, 0, 0, 0.8);
  font-size: 1.2rem;
  font-weight: 400;
  padding: 15px 25px;
  /**/
  position: relative;
  display: inline-blockk;
  text-decoration: none;
}
.SMN_effect-42 a {
  position: relative;
}

.SMN_effect-42 a:before {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 1.5rem;
  background-color: #034c8c;
  transform-origin: 100% 50%;
  transform: scale(0, 1);
  top: 0;
  left: 0;
  transition: transform 0.1s cubic-bezier(0.33, 0.91, 0.42, 1);
}

.SMN_effect-42 a:after {
  content: '';
  position: absolute;
  width: 100%;
  height: 2px;
  background-color: #034c8c;
  left: 0;
  bottom: 0;
  transform-origin: 0% 50%;
  transition: transform 0.1s cubic-bezier(0.33, 0.91, 0.42, 1) 0s;
}

.SMN_effect-42 a span {
  position: relative;
}

.SMN_effect-42 a span:after {
  content: attr(data-hover);
  position: absolute;
  left: 0;
  width: 100%;
  height: 100%;
  transition: opacity 0.2s cubic-bezier(0.33, 0.91, 0.42, 1) 0s;
}

.SMN_effect-42 a:hover:before {
  transform: scale(1);
  transform-origin: 0 50%;
  background-color: #034c8c;
}

.SMN_effect-42 a:hover:after {
  transform: scale(0, 1);
  transform-origin: 100% 50%;
  transition: transform 0.1s cubic-bezier(0.33, 0.91, 0.42, 1) 0s;
  background-color: #034c8c;
}

.SMN_effect-42 a:hover span:after {
  color: #ffffff;
  transition: color 0.1s cubic-bezier(0.33, 0.91, 0.42, 1) 0.1s;
}
.custom-btn {
  width: 6rem;
  height: 3rem;
  padding: 1rem;
  margin-left: 1rem;
  border-radius: 1rem;
  background: transparent;
  cursor: pointer;
  transition: all 0.1s ease;
  position: relative;
  display: inline-block;
}

.btn-7 {
  background: #fff;
  color: #034c8c;
  line-height: 2rem;
  border-radius: 1rem;
  padding: 0;
  border: none;
  z-index: 1;
  -webkit-transition: all 0.3s linear;
  transition: all 0.3s linear;
}
.btn-7:hover {
  background: transparent;
  border-radius: 1rem;
  color: #fff;
}
.btn-7:before,
.btn-7:after {
  position: absolute;
  content: '';
  left: 0;
  width: 100%;
  border-radius: 1rem;
  height: 50%;
  right: 0;
  z-index: -1;
  background: #fff;
  transition: all 0.3s ease;
}
.btn-7:before {
  top: 0;
}
.btn-7:after {
  bottom: 0;
}
.btn-7:hover:before,
.btn-7:hover:after {
  height: 0;
  background-color: #034c8c;
}
</style>
