<script setup>

const category = [
  { style: "home", label: "Life" },
  { style: "sports_and_outdoors", label: "Sport" },
  { style: "terminal", label: "Develop" },
  { style: "favorite", label: "Health" },
  { style: "grid_view", label: "ETC" },]

</script>

<template>
  <q-toolbar class="text-black rounded-borders" style="max-width: 40vw;">
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
</style>
