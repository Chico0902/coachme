<script setup>
const props = defineProps({
  name : { // 코치 이름
    type : String
  }, 
  isCoach : {
    type : Boolean
  }
}); 
</script>

<template>
  <div style="width: 60vw; height: 70vh; background-color: gray;">
    <q-btn v-if="!props.isCoach" flat style="margin-left: 55vw; margin-top: 1.3vh;">
      <span class="material-symbols-outlined" style="font-size: 32px;">
        meeting_room
      </span>
      <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <Strong> {{ name }} 님에게 진실의 방 요청하기</Strong>
      </q-tooltip>
    </q-btn>
  </div>
</template>

<style scoped></style>