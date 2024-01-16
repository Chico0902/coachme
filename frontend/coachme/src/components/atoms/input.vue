<!-- input 컴포넌트 
필요한 정보 : 라벨, 힌트, input 규칙
label : input 위에 작성될 문자열. 기본값 빈값
hint : input 아래에 작성될 문자열. 기본값 빈값
rule : input validation 규칙. Object값 받음. 기본값 없음
-->

<script setup>
import { ref, onMounted } from "vue";

const props = defineProps({
  label: {  // input 위에 표시될 문구
    type: String,
    default: ""
  },
  hint: {  // input 아래에 표시될 문구
    type: String,
    default: ""
  },
  rule: { // validation 규칙
    type: Object,
  }
});

const data = ref('')
const nameRef = ref('')

const changeInput = (e) => {
  this.data.value = e.target.value
}

const emit = defineEmits(['updateData'])
// input 창에서 입력된 내용을 부모 컴포넌트로 보내기 위한 emit

onMounted(() => {
  const el = nameRef.value.getNativeElement();
  el.addEventListener('input', e => {
    console.log('input', e.target.value);
    data.value = e.target.value;
    emit('updateData', data)
  });
}); // 한글 바로 갱신을 위한 이벤트리스너

</script>

<template>
  <q-input ref="nameRef" filled :model="data.value" @input="changeInput" :label="`${props.label}`" :rules="[rule]" :hint="`${props.hint}`"
  :input-style="{ fontSize: '35px' }" ></q-input>
</template>
<!-- 퀘이사 input의 갱신 방법 @update:model-value="새로운 값 => ('데이터를 실어보낼 함수 이름', 새로운 값)" -->
<!-- 위에 내용 한글 안되므로 바꿈-->
<style scoped></style>