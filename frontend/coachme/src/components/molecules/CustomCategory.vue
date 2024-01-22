<!-- 카테고리 컴포넌트 
필요한 정보 없음
style은 아이콘을 결정하는 이름 -> 가능하면 수정 x
label 아이콘 밑에 메뉴 이름
-->

<script setup>

const category = [
  { style: "home", label: "Life" },
  { style: "palette", label: "Creation" },
  { style: "sports_and_outdoors", label: "Sport" },
  { style: "terminal", label: "Develop" },
  { style: "favorite", label: "Health" },
  { style: "grid_view", label: "ETC" },
]

</script>

<template>
  <q-toolbar class="text-black rounded-borders category" style="max-width: 100%;">
    <!-- 카테고리에 표시될 메뉴 -->
    <div v-for="menu in category" :key="menu">
      <q-btn flat class="button">
        <span class="material-symbols-outlined">{{ menu.style }}</span>
        <div class="menu-text">{{ menu.label }}</div>
      </q-btn>
    </div>
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
  max-width: 6vw;
  min-width: 6vw;
  margin-right: 2.8rem;
}

.category {
  justify-content: center;
}
</style>
