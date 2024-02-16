<!-- input 컴포넌트 
필요한 정보 : 라벨, 힌트, 문자 크기, input 규칙
label : input 위에 작성될 문자열. 기본값 빈값
hint : input 아래에 작성될 문자열. 기본값 빈값
size : input에 입력되는 문자의 크기
rule : input validation 규칙. Object값 받음. 기본값 없음
-->

<script setup>
import { ref, onMounted } from 'vue'

const props = defineProps({
  label: {
    // input 위에 표시될 문구
    type: String,
    default: ''
  },
  hint: {
    // input 아래에 표시될 문구
    type: String,
    default: ''
  },
  rule: {
    // validation 규칙
    type: Object
  }
})

const data = ref('') // 갱신되는 값
const nameRef = ref('') // input ref 대상

const emit = defineEmits(['updateData'])
// input 창에서 입력된 내용을 부모 컴포넌트로 보내기 위한 emit

onMounted(() => {
  const el = nameRef.value.getNativeElement()
  el.addEventListener('input', (e) => {
    data.value = e.target.value
    emit('updateData', data) // 상위 컴포넌트로 입력값 emit
  })
}) // 한글 갱신을 위한 이벤트리스너

const paddingTop = ref("24px")
const paddingBottom = ref("8px")
// 기본 padding 세팅

const paddingSetting = () => {
  if(props.label.length == 0) {
    paddingTop.value = "0px"
    paddingBottom.value = "0px"
  }
}
/* 라벨이 없을 때 padding을 조절하여 
input에 입력되는 텍스트의 위치를 조정 */

onMounted(() => {
  paddingSetting()
})

</script>

<template>
  <q-input
    ref="nameRef"
    filled
    stack-label="false"
    :label="`${props.label}`"
    :rules="[rule]"
    :hint="`${props.hint}`"
    :input-style="{ fontSize: '1rem', paddingTop, paddingBottom }"
  ></q-input>
</template>
<style scoped>

</style>
