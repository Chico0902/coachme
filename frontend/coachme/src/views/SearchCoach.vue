<script setup>
import chatButton from '../components/materialIcon/ChatButton.vue'
import searchCoach from '../components/molecules/DmList.vue'
import DmWindow from '../components/molecules/DmWindow.vue';
import SelectCoach from '../components/molecules/SelectCoach.vue'

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

const myId = "coame"
const otherId = "coach"

</script>

<template>
  <chatButton></chatButton>
  <searchCoach :dm-list="dm"></searchCoach>
  <DmWindow :chatList="chat" :myId="myId" :otherId="otherId"></DmWindow>
  <SelectCoach></SelectCoach>
</template>

<style scoped>
</style> 