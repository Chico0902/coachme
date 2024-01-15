<!-- input 컴포넌트 
필요한 정보 : 라벨, 힌트, input 너비, input 규칙
label : input 위에 작성될 문자열. 기본값 빈값
hint : input 아래에 작성될 문자열. 기본값 빈값
width : input 폼의 (최대)너비. 숫자만. 기본값은 300(px)
rule : input validation 규칙. Object값 받음. 기본값 없음
-->

<script setup>
import { ref, reactive } from "vue";

const props = defineProps({
    label: {  // input 위에 표시될 문구
        type: String,
        default: ""
    },
    hint: {  // input 아래에 표시될 문구
        type: String,
        default: ""
    },
    width: { // input의 너비
        type: Number,
        default: 300
    },
    rule: { // validation 규칙
        type:Object,
    }
});

const data = ref('')

const emit = defineEmits(['updateData'])

const styleObject = reactive({
    maxWidth : props.width + 'px'
}) // 인라인 스타일문, input 너비에 적용

</script>

<template>
    <div class="q-pa-md" :style="styleObject">
        <q-input ref="nameRef" filled :model = "data" :label="`${props.label}`" :hint="`${props.hint}`" :rules="[ rule ]"
        @update:model-value="data => emit('updateData', data)"></q-input>
        <!-- 퀘이사 input의 갱신 방법 @update:model-value="새로운 값 => ('데이터를 실어보낼 함수 이름', 새로운 값)" -->
    </div>
</template>

<style scoped></style>