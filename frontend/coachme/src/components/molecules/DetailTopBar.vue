<!-- 중간 메뉴 컴포넌트 
스타일 : 탭 형식
필요한 정보 : 메뉴 리스트
menus : 메뉴 리스트. Object.
-->

<script setup>
import { ref } from 'vue'

const props = defineProps({
  menus : {  
    type : Object,
  },
})

// 중단 메뉴 리스트
const tab = ref('')
// 현재 누른 탭이 어디인지

</script>

<template>
  <q-tabs align="left" v-model="tab" class="bg-white text-black" flat>
    <div v-for="menu in props.menus" :key="menu">
        <q-tab :name="menu" :label="menu">
        </q-tab>
      </div>
  </q-tabs>
</template>

<style scoped></style>