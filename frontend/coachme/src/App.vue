<script setup>
import { RouterLink, RouterView } from 'vue-router'
import dropdown from '../src/components/atoms/dropdown.vue'
import buttons from '../src/components/atoms/button.vue'
import inputs from '../src/components/atoms/input.vue'
import { ref } from "vue";

const label1 = "button1"
const color = "#004C98"
const font = "#FFFFFF"
/* 첫번째 버튼 예시
라벨, 버튼색, 글자색 순 */

const label2 = "input name"
const hint = "please input your name"
/* 첫번째 input 폼 예시문
라벨, 힌트 */

const color2 = "#FCBF17"
const font2 = "#00000"
/* 두번째 버튼 예시
버튼색, 글자색 순
라벨은 input 폼의 예시문을 사용 */


const input = ref('')

const updateData = (data) => {
  input.value = data
} /* input 갱신시 부모 컴포넌트에서도 갱신하도록 
각 form 마다 따로 만들어야 동시에 갱신되는 일이 없음
*/
</script>

<template>
  <div>
    <dropdown></dropdown>
    <inputs :label="label2" :hint="hint" :width=300 @update-data="updateData"></inputs> {{ input }}
    <buttons :label="label1" :size=20 :background="color" :color="font"></buttons>
    <buttons :label="label2" :size=30 :background="color2" :color="font2"></buttons>
  </div>
</template>

<style scoped>
</style>
