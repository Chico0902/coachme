<script setup>

import { reactive } from "vue";
const props = defineProps({
  width: { // 버튼 크기
    type: String,
    default: "100%"
  },
  maxWidth: {
    type: String,
    default: "250px"
  }
});

const styleObject = reactive({
  width: props.width,
  maxWidth: props.maxWidth
}) // 인라인 스타일문, 버튼 스타일에 적용
</script>

<template>
  <q-card class="my-card" :style="styleObject">
  </q-card>
</template>

<style scoped></style>

