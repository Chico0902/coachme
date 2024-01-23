<script setup>
import SearchCoachCard from './SearchCoachCard.vue';
import TinderLikeCoach from './TinderLikeCoach.vue';

const test = () => {
  console.log("test");
};

const img = "https://src.hidoc.co.kr/image/lib/2022/11/15/1668491763670_0.jpg"
const label = "김코치"

</script>

<template>
  <div class="coach-card">
    <SearchCoachCard class="my-card" style="width: 360px; height: 450px;" :img="img" :label="label"></SearchCoachCard>
  </div>
  <TinderLikeCoach></TinderLikeCoach>
</template>

<style scoped>
.material-symbols-outlined {
  font-size: 50px;
}

.category-imoji {
  margin: auto;
  margin-top: 5vh;
  display: flex;
  justify-content: center;
  width: 100%;
}
</style>
