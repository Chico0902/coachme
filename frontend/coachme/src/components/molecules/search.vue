<script setup>
import dropdown from '../atoms/dropdown.vue'
import buttons from '../atoms/button.vue'
import inputs from '../atoms/input.vue'

import { ref } from "vue";

const input = ref('')
const menu = ref('')

const updateData = (data) => {
  input.value = data.value
} 

const clickMenu = (data) => {
  menu.value = data
} 

/* 
각 form 마다 따로 만들어야 동시에 갱신되는 일이 없음
*/

const bColor = "#FCBF17"
const dColor = "blue-10"
const label = "검색하기"
</script>

<template>
  <div id="search">
    <dropdown style="height:56px" :color="dColor" @click-menu="clickMenu"> </dropdown>
    <inputs style="width:500px" @update-data="updateData"></inputs>
    <buttons style="height:56px" :label="label" :background="bColor"></buttons>   
  </div>

</template>

<style scoped>
#search {
  display: flex;

  margin-top: 3vh;
  margin-bottom: 5vh;
}
</style>