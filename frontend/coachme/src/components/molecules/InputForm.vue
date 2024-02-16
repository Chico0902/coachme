<!-- 일반 입력폼 컴포넌트, 드롭다운 없는 버전
필요한 내용 : 버튼 라벨, 버튼 색
label : 버튼 위에 붙을 문구. 문자열. 기본값 없음
bColor : 버튼 색. 문자열. 기본값 없음
-->
<script setup>
import buttons from '../atoms/CustomButton.vue'
import inputs from '../atoms/CustomInput.vue'

import { ref } from 'vue'

const props = defineProps({
  color: {
    // 버튼 색
    type: String,
    default: ''
  },
  background: {
    type: String,
    default: ''
  }
})

const emit = defineEmits(['inputData'])

const input = ref('')

const updateData = (data) => {
  input.value = data.value
} // input 컴포넌트에서 받아온 입력 값

const inputData = () => {
  const data = { input: input.value }
  input.value = ''
  emit('inputData', data)
} // input에서 입력한 입력 데이터
</script>

<template>
  <div id="input">
    <inputs v-model="input" style="width: 100%" @update-data="updateData"></inputs>
    <buttons
      type="submit"
      style="height: 56px"
      icon="send"
      :background="props.background"
      :color="props.color"
      @click="inputData"
    ></buttons>
  </div>
</template>
<!-- input form의 높이는 56px에서 바뀌지 않는 것으로 보임 -->

<style scoped>
#input {
  display: flex;
  width: 340px;
  padding-bottom: 0px;
}
</style>
