<!-- 마이페이지 왼쪽 사이드 메뉴 컴포넌트 
필요한 정보 : 메뉴리스트
sideMenu : 왼쪽 사이드 메뉴 리스트. Object. 기본값 없음
-->
<script setup>
import { ref } from 'vue'

const props = defineProps({
  sideMenu : {
    type : Object
  }
})

const tab = ref(props.sideMenu[0])
// 현재 선택한 메뉴

const splitterModel = ref(20)
// vertical 구역 구분

</script>

<template>
  <!-- 구분자 -->
  <q-splitter v-model="splitterModel" style="height:auto; max-width: 35vw;" limits="[35,75]">
    <template v-slot:before>
      <q-tabs v-model="tab" vertical indicator-color="blue-10" class="text-black" active-bg-color="blue-10" active-color="white">
        <!-- 사이드 메뉴 리스트 -->
        <div v-for="menu in props.sideMenu" :key="menu">
          <q-tab :name="`${menu}`" :label="`${menu}`"></q-tab>
        </div>
      </q-tabs>
    </template>
  </q-splitter>
</template>

<style scoped></style>