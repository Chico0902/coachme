<!-- 카테고리 컴포넌트 
필요한 정보 없음
style은 아이콘을 결정하는 이름 -> 가능하면 수정 x
label 아이콘 밑에 메뉴 이름
-->

<script setup>
import { ref } from 'vue'

const category = [
  { style: "home", label: "Life" },
  { style: "palette", label: "Creation" },
  { style: "sports_and_outdoors", label: "Sport" },
  { style: "terminal", label: "Develop" },
  { style: "favorite", label: "Health" },
  { style: "grid_view", label: "ETC" },
]

const emit = defineEmits(['clickCategory'])

const clickCategory = (index) => {
  emit('clickCategory', index)
}

</script>

<template>
  <q-toolbar class="text-black rounded-borders category justify-evenly" style="max-width: 100%;">
    <!-- 카테고리에 표시될 메뉴 -->
    <q-tabs v-model="tab" shrink>
      <div v-for="(menu, index) in category" :key="menu">
        <q-tab flat class="button" @click="clickCategory(index)">
          <span class="material-symbols-outlined">{{ menu.style }}</span>
          <div class="menu-text">{{ menu.label }}</div>
        </q-tab>
      </div>
    </q-tabs>
  </q-toolbar>
</template>

<style scoped>
.material-symbols-outlined {
  font-size: 36px;
}

.menu-text {
  margin: 5px;
  text-align: center;
}

.button {
  margin-right: 2.8rem;
}

.category {
  justify-content: center;
}
</style>
