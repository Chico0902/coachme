<!-- 검색 컴포넌트


-->
<script setup>
import dropdown from '../atoms/dropdown.vue'
import buttons from '../atoms/button.vue'
import inputs from '../atoms/input.vue'

import { ref } from "vue";

const props = defineProps({
  label : {
    type: String,
    default: ""
  },
  bColor : {
    type: String,
    default: ""
  },
  dColor : {
    type: String,
    default: ""
  },
});

const input = ref('')
const menu = ref('')
// 입력된 값, 선택한 메뉴

const updateData = (data) => {
  input.value = data.value
} // 하위 컴포넌트에서 받아온 입력 값

const clickMenu = (data) => {
  menu.value = data
}  // 하위 컴포넌트에서 받아온 선택 메뉴


// 각 form 마다 따로 만들어야 동시에 갱신되는 일이 없음

</script>

<template>
  <div id="search">
    <dropdown style="height:56px; min-width: 125px" :color="`${props.dColor}`" @click-menu="clickMenu"> </dropdown>
    <inputs style="width:500px" @update-data="updateData"></inputs>
    <buttons style="height:56px" :label="`${props.label}`" :background="`${props.bColor}`"></buttons>   
  </div>
</template>
<!-- input form의 높이는 56px에서 바뀌지 않는 것으로 보임 -->

<style scoped>
#search {
  display: flex;

  margin-top: 3vh;
  margin-bottom: 5vh;
  max-width: 750px;
}
</style>