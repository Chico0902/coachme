<!-- 로그인 안한 상태의 nav 
필요한 정보 없음
name : 버튼 이름(클래스 이름 같은 것)
label : nav 버튼에 표시될 이름
-->

<script setup>
import buttons from '../atoms/Button.vue'
const name = ["coach", "coaching", "live", "login", "logout"]
const label = ["코치찾기", "코칭찾기", "강의장", "로그인", "로그아웃"]

</script>

<template>
  <q-toolbar class="text-black rounded-borders" style="width: 80vw;">
    <!-- 로고 -->
    <q-btn flat>
      <img src="../icons/5.png" width="150px">
    </q-btn>
    <!-- 코치찾기, 코칭찾기, 강의장 -->
    <buttons flat :name="`${name[0]}`" :label="`${label[0]}`"></buttons>
    <buttons flat :name="`${name[1]}`" :label="`${label[1]}`"></buttons>
    <buttons flat :name="`${name[2]}`" :label="`${label[2]}`"></buttons>
    <q-space></q-space>

    <!-- 로그인, 로그아웃 -->
    <buttons flat :name="`${name[3]}`" :label="`${label[3]}`"></buttons>
    <buttons flat :name="`${name[4]}`" :label="`${label[4]}`"></buttons>
  </q-toolbar>
</template>

<style scoped></style>