<!-- 로그인 안한 상태의 nav 
필요한 정보 없음
name : 버튼 이름(클래스 이름 같은 것)
label : nav 버튼에 표시될 이름
-->

<script setup></script>

<template>
  <q-toolbar class="text-black rounded-borders" style="width: 80vw">
    <!-- 로고 -->
    <q-btn flat>
      <img src="../../assets/icons/6.png" width="150px" />
    </q-btn>
    <!-- 코치찾기, 코칭찾기, 강의장 -->
    <slot name="search-coach"> </slot>
    <slot name="search-coaching"> </slot>
    <q-space></q-space>

    <!-- 로그인, 로그아웃, 회원가입, 환영인사 -->
    <slot name="welcome"> </slot>
    <slot name="login"> </slot>
    <slot name="logout"> </slot>
    <slot name="regist"> </slot>
  </q-toolbar>
</template>

<style scoped>
.text-black {
  min-width: 920px;
}
</style>
