<script setup>
import DmList from '.././molecules/DmList.vue'
import { ref } from 'vue'

const onDmList = ref(false)

const clickedChatButton = () => {
  onDmList.value = !onDmList.value
}

const dm = [{
  img: "https://cdn.quasar.dev/img/avatar2.jpg",
  name: "Mary",
  lastDm: "Hello",
},
{
  img: "https://cdn.quasar.dev/img/avatar4.jpg",
  name: "John",
  lastDm: "Your suggestion is considered.",
},
{
  img: "https://src.hidoc.co.kr/image/lib/2022/11/15/1668491763670_0.jpg",
  name: "White Cat",
  lastDm: "meow",
}]

</script>

<template>
  <q-btn round size="30px" color="amber-7" icon="chat">
    <q-menu style="min-height: fit-content;">
      <div class="row no-wrap q-pa-md">
        <DmList :dm-list="dm"></DmList>
      </div>
    </q-menu>
  </q-btn>
</template>

<style scoped></style>