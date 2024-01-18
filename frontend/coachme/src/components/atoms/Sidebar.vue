<script setup>
import { onMounted, ref, watch } from 'vue';

const props = defineProps({
  open: {
    type: Boolean,
  },
  sideMenu: {
    type: Object
  }
});

const emit = defineEmits(['closeSidebar'])

const sidebarOpen = ref(false);

const closeSidebar = () => {
  sidebarOpen.value = false;
  emit('closeSidebar', '')
};

watch(() => props.open, (newState) => {
  sidebarOpen.value = newState;
});

</script>

<template>
  <div class="sidebar" :class="{ 'sidebar-open': sidebarOpen }">
    <button @click="closeSidebar">
      <span class="material-symbols-outlined">
        close
      </span>
    </button>
    <div class="menu-item" v-for="menu in props.sideMenu" :key="menu">{{ menu }}</div>
  </div>
</template>

<style scoped>
button {
  font-size: 20px;
  cursor: pointer;
}

.sidebar {
  position: fixed;
  top: 0;
  left: -250px;
  /* initially hide the sidebar */
  width: 250px;
  height: 100%;
  background-color: #333;
  color: #fff;
  padding: 20px;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.2);
  transition: left 0.3s ease;
}

.sidebar button {
  font-size: 18px;
  cursor: pointer;
  background: none;
  border: none;
  color: #fff;
}

.sidebar-open {
  left: 0;
}

.main-content {
  margin-left: 250px;
  /* Adjust the margin to fit the sidebar width */
  padding: 20px;
  transition: margin-left 0.3s ease;
}

.sidebar {
  padding: 10px 0;
  font-size: 22px;
}

.menu-item {
  margin-top: 1vh;
  margin-left: 2vw;
}
</style>