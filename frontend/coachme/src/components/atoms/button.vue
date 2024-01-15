<!-- 버튼 컴포넌트 
필요한 정보 : 라벨, 크기, 버튼 색, 폰트색
label : 버튼위에 작성될 라벨. 문자열. 기본값 "버튼"
size : 버튼의 크기. 숫자만. 기본값 20(px)
background : 색상코드를 문자열로. 기본값 #FCBF17
color : 색상코드를 문자열로. 기본값 #FFFFFF
-->

<script setup>
import { reactive } from "vue";

const props = defineProps({
    label: {  // 버튼 라벨
        type: String,
        default: "버튼"
    },

    size: { // 버튼 크기로 vh단위이며, 버튼은 높이에 비례하는 크기를 가짐
        type: Number,
        default: 20
    }, 
    background: {
        type : String,
        default : "#FCBF17"
    },
    color: {
        type: String,
        default : "#FFFFFF"
    },
});

const styleObject = reactive({
    background: props.background,
    color: props.color
}) // 인라인 스타일문, 버튼 스타일에 적용

</script>

<template>
    <div class="q-pa-md q-gutter-sm">
        <q-btn :size="`${props.size}px`" :style="styleObject" :label="`${props.label}`">
        </q-btn>
    </div>
</template>

<style scoped></style>