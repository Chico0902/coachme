<!-- 로그인 후 nav 컴포넌트 
아직 분리 덜 됐음
-->

<script setup>
import profile from '../atoms/profile_image.vue'
import { ref } from 'vue';

const sidebarOpen = ref(false);

const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value;
};

const closeSidebar = () => {
  sidebarOpen.value = false;
};
</script>

<template>
  <div class="menu-container">
    <q-toolbar class="text-black rounded-borders" style="width: 80vw;">
      <q-btn flat class="burger_button" @click="toggleSidebar">
        <div class="navbar_burger">
          <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" class="bi bi-list"
            viewBox="0 0 16 16">
            <path fill-rule="evenodd"
              d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5z" />
          </svg>
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
    <div class="sidebar" :class="{ 'sidebar-open': sidebarOpen }">
      <button @click="closeSidebar">
        <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-x-lg"
          viewBox="0 0 16 16">
          <path
            d="M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8 2.146 2.854Z" />
        </svg>
      </button>
      <div class="menu-item">Home</div>
      <div class="menu-item">About</div>
      <div class="menu-item">Services</div>
      <div class="menu-item">Contact</div>
    </div>
  </div>
</template>

<style scoped>
body {
  margin: 0;
  padding: 0;
  font-family: Arial, sans-serif;
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
