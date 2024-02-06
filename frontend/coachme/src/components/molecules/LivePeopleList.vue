<!-- 코칭 참가자 목록 컴포넌트
필요한 정보 : 코칭 참가자 리스트
people : 코칭 참가자 리스트
-->

<script setup>

const props = defineProps({
  people: {
    type: Object,
  }
})
</script>

<template>
  <q-list>
    <q-item-label header style="color: black; font-size: 25px;">참가자 목록</q-item-label>
    <div v-for="person in props.people" :key="person">
      <q-item clickable v-ripple>
        <q-item-section style="font-size: 20px;">{{ person }}</q-item-section>
      </q-item>
    </div>
  </q-list>
</template>

<style scoped></style>