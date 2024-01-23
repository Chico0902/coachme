<script setup>
import { is } from 'quasar';
import DmList from '.././molecules/DmList.vue'
import DmWindow from '.././molecules/DmWindow.vue';

import { ref } from 'vue'

const isChatList = ref(true)

const myId = "coame"
const otherId = "coach"

const chat = [{
  "id": "coame",
  "chat": ["hey, how are you?", "I'm so so these day"]
}, {
  "id": "coach",
  "chat": ["doing fine, how r you?"]
}, {
  "id": "coach",
  "chat": ["I just feel like typing a really, really, REALLY long message to annoy you..."]
}]


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

const clickDm = (data) => {
  isChatList.value = !isChatList.value
} 

const closeDm = () => {
  isChatList.value = !isChatList.value
}

const reset = () => {
  isChatList.value = true
}

</script>

<template>
  <q-btn round size="30px" color="amber-7" icon="chat" @click="reset">
    <q-menu>
      <div class="row no-wrap q-pa-md">
        <DmList v-if="isChatList" :dm-list="dm" @click-dm="clickDm"></DmList>
        <DmWindow v-else :chatList="chat" :myId="myId" :otherId="otherId" @close-dm="closeDm"></DmWindow>
      </div>
    </q-menu>
  </q-btn>
</template>

<style scoped></style>