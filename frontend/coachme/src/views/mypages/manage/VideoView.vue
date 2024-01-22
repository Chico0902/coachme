<script setup>
import MypageTopBar from '@/components/molecules/MypageTopBar.vue'

const TopButtonList = [
  { name: '포트폴리오', link: '/mypage/coach/manage/portfolio' },
  { name: '코칭관리', link: '/mypage/coach/manage/coaching' },
  { name: '영상관리', link: '/mypage/coach/manage/video', cssClass: 'selected-button' }
]
</script>
<template>
  <MypageTopBar :button-list="TopButtonList" />
</template>

<style scoped></style>
