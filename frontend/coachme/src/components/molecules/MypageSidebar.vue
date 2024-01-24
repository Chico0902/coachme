<script setup>
import router from '@/router'
import { reactive, watch } from 'vue'

const props = defineProps({ buttonList: Object })
const buttonList = props.buttonList

// 클릭하면 Button List에서 해당 버튼 색 변경하고 라우터링크 이동하는 로직
const changeView = (link, event) => {
  const clickedButton = event.target.innerText
  buttonList.forEach((button, index) => {
    if (index === 0) return
    else if (button.name === clickedButton) button.cssClass = 'selected-button'
    else button.cssClass = ''
  })
  router.push(link)
}
</script>

<template>
  <div class="sidebar">
    <div
      v-for="button in props.buttonList"
      :key="button.name"
      class="sidebar-button shadow-3"
      :class="button.cssClass"
      @click="changeView(button.link, $event)"
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
