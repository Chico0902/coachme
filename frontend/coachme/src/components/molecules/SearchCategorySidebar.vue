<script setup>
import router from '@/router'

const props = defineProps({ buttonList: Object })
// const buttonList = props.buttonList

</script>
<template>
  <div class="sidebar">
    <div
      v-for="button in props.buttonList"
      :key="button.name"
      class="sidebar-button shadow-3"
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
.sidebar {
  width: 12%;
  height: 70vh;
  margin: auto;
}
.sidebar-button {
  width: 100%;
  height: 8vh;
  border-radius: 1rem;
  margin-top: 0;
  margin-bottom: 4.4vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.selected-button {
  background-color: #034c8c;
  opacity: 90%;
  color: #fff;
}
.sidebar-button:hover {
  background-color: #034c8c;
  opacity: 100%;
  color: #fff;
  cursor: pointer;
}
.manage-button {
  background-color: #bd58d9;
  opacity: 90%;
  color: #fff;
}
.manage-button:hover {
  background-color: #bd58d9;
  opacity: 100%;
  color: #fff;
}
</style>
