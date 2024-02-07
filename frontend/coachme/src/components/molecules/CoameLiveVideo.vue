<!-- 코미와 진실의 방으로 가는 버튼 -->

<script setup>
const props = defineProps({
  name : { // 코미 이름
    type : String
  }, 
  isCoach : {
    type : Boolean
  }
}); 

</script>

<template>
  <div style="width: 14vw; height: 16.5vh; background-color: gray;">
    <q-btn v-if="props.isCoach" flat style="margin-left: 9vw; margin-top: 1.0vh;">
      <span class="material-symbols-outlined" style="font-size: 32px;">
        meeting_room
      </span>
      <q-tooltip anchor="top middle" self="bottom middle" :offset="[10, 10]">
        <Strong>{{ props.name }} 님과 진실의 방으로 가기</Strong>
      </q-tooltip>
    </q-btn>
  </div>
</template>

<style scoped></style>