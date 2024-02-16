<!-- 메인 페이지 검색 컴포넌트
필요한 내용 : 버튼 라벨, 버튼 색, 드롭다운 색, 드롭다운 메뉴 리스트
label : 버튼 위에 붙을 문구. 문자열. 기본값 없음
bColor : 버튼 색. 문자열. 기본값 없음
dColor : 드롭다운 색. 문자열. 기본값 없음. 색상 코드가 아니라, quasar의 컬러파레트 색상 이름 사용.
list : 드롭다운 메뉴 리스트. object. 기본값 없음
그 외에는 고정. 그 요소들은 최상단 컴포넌트가 아니라 여기서 바꿀 것
-->
<script setup>
import dropdown from '../atoms/CustomDropdown.vue'
import buttons from '../atoms/CustomButton.vue'
import inputs from '../atoms/CustomInput.vue'

import { ref } from 'vue'

const props = defineProps({
  label: {
    // 버튼 라벨
    type: String,
    default: ''
  },
  bColor: {
    // 버튼 색
    type: String,
    default: ''
  },
  dColor: {
    // 드롭다운 색
    type: String,
    default: ''
  },
  list: {
    // 드롭다운 메뉴 리스트
    type: Object,
    default: () => {}
  }
})

const emit = defineEmits(['searchData'])

const input = ref('')
const menu = ref('')
// input에 입력된 값, 드롭다운에서 선택한 메뉴

const updateData = (data) => {
  input.value = data.value
} // input 컴포넌트에서 받아온 입력 값

const clickMenu = (data) => {
  menu.value = data
} // 드롭다운 컴포넌트에서 받아온 선택 메뉴

const searchData = () => {
  const data = { menu: menu.value, input: input.value }
  input.value = ''
  emit('searchData', data)
} // 드롭다운에서 선택한 메뉴와 input에서 입력한 입력 데이터

const color = '#FFFFFF'
</script>

<template>
  <div id="search">
    <!-- 드롭다운, 입력폼, 버튼 -->
    <dropdown
      style="height: 56px; min-width: 125px"
      :color="`${props.dColor}`"
      :list="props.list"
      @click-menu="clickMenu"
    >
    </dropdown>
    <inputs v-model="input" style="width: 45vw" @update-data="updateData" @keyup.enter="searchData"></inputs>
    <buttons
      style="height: 56px"
      :label="`${props.label}`"
      :background="`${props.bColor}`"
      :color="color"
      @click="searchData"
    ></buttons>
  </div>
</template>
<!-- input form의 높이는 56px에서 바뀌지 않는 것으로 보임 -->

<style scoped>
#search {
  display: flex;
}


</style>
