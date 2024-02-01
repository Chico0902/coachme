<script setup>
import MypageTopBar from '@/components/molecules/MypageTopBar.vue'


const TopButtonList = [
  { name: '포트폴리오', link: '/mypage/coach/manage/portfolio', cssClass: 'selected-button' },
  { name: '코칭관리', link: '/mypage/coach/manage/coaching' },
  { name: '영상관리', link: '/mypage/coach/manage/video' }
]
</script>
<template>
  <MypageTopBar :button-list="TopButtonList" />
  포트
</template>

<style scoped>

</style>
