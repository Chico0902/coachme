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
    <div v-for="(menu, index) in menus" :key="menu">
        <q-tab :name="menu" :label="menu">
        </q-tab>
      </div>
  </q-tabs>
</template>

<style scoped></style>