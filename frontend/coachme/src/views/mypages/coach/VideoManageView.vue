<script setup>
import Coaching from '@/components/molecules/CoachingCard.vue'
</script>
<template>
  <div class="video-title">
    <div>영상보기</div>
  </div>
  <div class="coaching-outside">
    <div class="coaching-card">
      <coaching :label="label" :caption="caption" :ratio="ratio" :video="video"></coaching>
    </div>
    <div class="coaching-card">
      <coaching :label="label" :caption="caption" :ratio="ratio" :video="video"></coaching>
    </div>
    <div class="coaching-card">
      <coaching :label="label" :caption="caption" :ratio="ratio" :video="video"></coaching>
    </div>
  </div>
  <div class="pagenation">
    <div>페네자리</div>
  </div>
</template>

<style scoped>
.video-title {
  width: 80%;
  margin: 5vh auto;
  font-weight: bolder;
}

.coaching-outside {
  width: 80%;
  height: 30vh;
  margin: 5vh auto;
  display: flex;
  justify-content: center;
}
.coaching-card {
  width: 200px;
  height: 200px;
  margin: 30px;
}
.pagenation {
  margin: 5vh;
}
</style>
