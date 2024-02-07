<script setup>
const props = defineProps({
  name : { // 코치 이름
    type : String
  }, 
  isCoach : { // 코치 여부
    type : Boolean
  }
}); 
</script>

<template>
  <div class="coach-video">
    <q-btn v-if="!props.isCoach" flat class="btn" size="xl" style="margin-top: 0.9vh; margin-right: 0.5vw;">
      <span class="material-symbols-outlined" style="font-size: 32px;">
        meeting_room
      </span>
      <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <Strong> {{ name }} 님에게 진실의 방 요청하기</Strong>
      </q-tooltip>
    </q-btn>
  </div>
</template>

<style scoped>
.coach-video {
  width: 60vw; 
  height: 70vh; 
  background-color: gray; 
  display: flex; 
  justify-content: flex-end; 
  align-items: flex-start;
}
.material-symbols-outlined {
  font-size: 40px;
}
</style>