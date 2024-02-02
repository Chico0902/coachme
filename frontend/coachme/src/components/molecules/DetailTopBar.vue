<<<<<<< HEAD
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

=======
<script setup>
import { ref } from 'vue'

const menus = ref(['코치 소개', '제공 코칭', '리뷰'])
>>>>>>> 0d471417f0c784672912bf2c07b6a42a16cbee81
// 중단 메뉴 리스트
const tab = ref('')
// 현재 누른 탭이 어디인지

</script>

<template>
  <q-tabs align="left" v-model="tab" class="bg-white text-black" flat>
<<<<<<< HEAD
    <div v-for="menu in props.menus" :key="menu">
=======
    <div v-for="(menu, index) in menus" :key="menu">
>>>>>>> 0d471417f0c784672912bf2c07b6a42a16cbee81
        <q-tab :name="menu" :label="menu">
        </q-tab>
      </div>
  </q-tabs>
</template>

<style scoped></style>