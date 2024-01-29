<script setup>
import { ref } from 'vue'

const menus = ref(['코치 소개', '제공 코칭', '리뷰'])

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