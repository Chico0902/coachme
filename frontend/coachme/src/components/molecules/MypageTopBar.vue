<script setup>
import router from '@/router'

const props = defineProps({ buttonList: Object })
const buttonList = props.buttonList
</script>
<template>
  <div class="topbar">
    <div
      v-for="button in buttonList"
      :key="button.name"
      class="top-button"
      :class="button.cssClass"
      @click="router.push(button.link)"
    >
      <p>{{ button.name }}</p>
    </div>
  </div>
</template>
<style scoped>
p {
  margin: 0;
  font-size: 1rem;
  letter-spacing: 0.1rem;
}
.topbar {
  width: 100%;
  height: 10vh;
  display: flex;
}
.top-button {
  width: 100%;
  height: 8vh;
  border-radius: 1rem;
  margin-top: 0;
  margin-bottom: 4.4vh;
  display: flex;
  justify-content: center;
  align-items: center;
}
.top-button:hover {
  background-color: #fcbf17;
  cursor: pointer;
}
.selected-button {
  background-color: #fcbf17;
}
.selected-button:hover {
  opacity: 90%;
}
</style>
