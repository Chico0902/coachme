<!-- 카드에 붙을 라벨 컴포넌트
필요한 정보 : 라벨
label : 카드의 라벨. 문자열. 기본값은 "라벨"
-->

<script setup>
const props = defineProps({
  label : {  
    type : String,
    default : "라벨"
  },
})
</script>

<template>
  <q-item-label >{{label}}</q-item-label>
</template>

<style scoped>
div{
  flex-direction: row;
}

</style>