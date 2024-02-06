<script setup>
import { ref } from 'vue'
const category = ref([
  { style: 'grid_view', label: 'ALL', css: 'button-clicked' },
  { style: 'home', label: 'Life' },
  { style: 'palette', label: 'Creations' },
  { style: 'sports_and_outdoors', label: 'Sports' },
  { style: 'terminal', label: 'Develop' },
  { style: 'favorite', label: 'Health' }
])

const emit = defineEmits(['clickCategory'])

const clickCategory = (index, name) => {
  category.value.forEach((element) => (element.css = ''))
  category.value[index].css = 'button-clicked'
  emit('clickCategory', index, name)
}
</script>

<template>
  <q-toolbar class="text-black rounded-borders category justify-evenly" style="max-width: 100%">
    <!-- 카테고리에 표시될 메뉴 -->
    <q-tabs v-model="tab" shrink>
      <div v-for="(menu, index) in category" :key="menu">
        <q-tab flat class="button" @click="clickCategory(index, menu.label)">
          <span class="material-symbols-outlined" :class="menu.css">{{ menu.style }}</span>
          <div class="menu-text" :class="menu.css">{{ menu.label }}</div>
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
.button-clicked {
  color: #034c8c;
}

.category {
  justify-content: center;
}
</style>
