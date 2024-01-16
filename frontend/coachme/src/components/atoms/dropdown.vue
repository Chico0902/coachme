<!-- dropdown 컴포넌트 
필요한 정보 : 라벨, 색상, 크기, 메뉴 리스트
label : 드릅다운 메뉴 맨 위에 표시되는 문구. 문자열. 초기값 "선택"
color : 드롭다운 메뉴 색상. 문자열. 초기값 : "blue-10". 그 외에 추천 값 "amber-7" 프로젝트 색상 기준
그 외에는 색상표 참고 : https://quasar.dev/style/color-palette/
size : 드롭다운의 글자 크기. 문자열. 기본값 없음. 추천값 : px, rem, (xs|sm|md|lg|xl) 
list : 드롭다운 클릭시 들어갈 메뉴들. 리스트. 기본값 ["A","B","C"]
-->

<script setup>
const props = defineProps({
  label : {  
    type : String,
    default : "선택"
  },
  color : {
    type : String,
    default : "blue-10"
  },
  size : {
    type : String,
    default : ""
  },
  list: {
    type : Object,   
    default: () => {
      return ["A", "B", "C"]
    }
  }
});

const emit = defineEmits(['clickMenu'])

const clickEvent = (menu) => {
  emit('clickMenu', menu)
}

</script>

<template>
    <q-btn-dropdown to="/start/pick-quasar-flavour" :color="`${props.color}`" :label="`${props.label}`" :size="`${props.size}`">
      <q-list>
        <div v-for="menu in props.list" :key="menu">
          <q-item clickable v-close-popup>
            <q-item-section @click=clickEvent(menu)>
              <q-item-label>{{ menu }}</q-item-label>
            </q-item-section>
          </q-item>
        </div>
      </q-list>
    </q-btn-dropdown>
</template>

<style scoped></style>