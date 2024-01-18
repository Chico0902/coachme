<!-- 로그인 후 nav 컴포넌트 
아직 분리 덜 됐음
-->

<script setup>
import profile from '../atoms/ProfileImage.vue'
import sidebar from '../atoms/Sidebar.vue'
import { ref } from 'vue';

const sidebarOpen = ref(false);

const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value;
};

const closeSidebar = () => {
  sidebarOpen.value = false
}

const sideMenu = ['Home', 'About', 'Service', 'Contact']

</script>

<template>
  <div class="menu-container">
    <q-toolbar class="text-black rounded-borders" style="width: 80vw;">
      <q-btn flat class="burger_button" @click="toggleSidebar">
        <div class="navbar_burger">
          <span class="material-symbols-outlined">
            menu
          </span>
        </div>
      </q-btn>
      <q-space></q-space>

      <q-btn flat>
        <img src="../icons/4.png" width="150px">
      </q-btn>
      <q-space></q-space>

      <q-btn flat>
        <profile></profile>
      </q-btn>
    </q-toolbar>

    <!-- 사이드바 -->
    <sidebar :open="sidebarOpen" :sideMenu="sideMenu" @close-sidebar="closeSidebar"></sidebar>
  </div>
</template>

<style scoped>
body {
  margin: 0;
  padding: 0;
}

.menu-container {
  position: relative;
  z-index: 1;
  /* Ensure menu is above other content */
}

button {
  font-size: 20px;
  cursor: pointer;
}
</style>
