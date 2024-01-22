<!-- dropdown 컴포넌트 
필요한 정보 : 라벨, 색상, 크기, 메뉴 리스트
label : 드롭다운 메뉴 상단에 표시되는 문구. 문자열. 초기값 "분류 선택"
color : 드롭다운 메뉴 색상. 문자열. 초기값 : "blue-10". 그 외에 추천 값 "amber-7" 프로젝트 색상 기준
그 외에는 색상표 참고 : https://quasar.dev/style/color-palette/
size : 드롭다운의 글자 크기. 문자열. 기본값 없음. 추천값 : px, rem, (xs|sm|md|lg|xl) 
list : 드롭다운 클릭시 들어갈 메뉴들. 리스트. 기본값 ["A","B","C"]
-->

<script setup>
import { ref, onMounted } from 'vue'

const props = defineProps({
  label: {
    // 드롭다운 상단 문구
    type: String,
    default: '분류 선택'
  },
  color: {
    // 드롭다운 메뉴 색상
    type: String,
    default: 'blue-10'
  },
  size: {
    // 드롭다운 글자 크기
    type: String,
    default: ''
  },
  list: {
    // 드롭다운 메뉴 리스트
    type: Object,
    default: () => {
      return ['A', 'B', 'C']
    }
  }
})

const emit = defineEmits(['clickMenu'])
const topLabel = ref('') // 상단 문구

const clickEvent = (menu) => {
  emit('clickMenu', menu)
  topLabel.value = menu
}
/* 드롭다운 선택한 메뉴를 상위 컴포넌트로 emit
그와 동시에 상단 문구를 선택한 메뉴로 변경
*/

onMounted(() => {
  topLabel.value = props.label
}) // 최초로 마운트시, 상단 문구를 따로 저장하기
</script>

<template>
  <q-btn-dropdown :color="`${props.color}`" :label="topLabel" :size="`${props.size}`">
    <q-list>
      <!-- 드롭다운 메뉴 -->
      <div v-for="menu in props.list" :key="menu">
        <q-item clickable v-close-popup>
          <q-item-section @click="clickEvent(menu)">
            <q-item-label>{{ menu }}</q-item-label>
          </q-item-section>
        </q-item>
      </div>
    </q-list>
  </q-btn-dropdown>
</template>

<style scoped></style>
