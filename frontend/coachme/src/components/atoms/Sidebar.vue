<!-- 사이드바 컴포넌트 
  필요한 정보 : 사이드바 오픈 여부, 사이드바에 배치할 메뉴
  open : 사이드바 오픈 여부. boolean. 기본값 없음
  sideMenu : 사이드바 배치 메뉴. Object. 기본값 없음
-->
<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  open: {
    // 사이드바 오픈 여부
    type: Boolean
  }, // 사이드바에 배치할 메뉴
  sideMenu: {
    type: Object
  }
})

const emit = defineEmits(['closeSidebar'])

const sidebarOpen = ref(false)
// 현재 컴포넌트 오픈 여부

const closeSidebar = () => {
  sidebarOpen.value = false
  emit('closeSidebar', '')
} // 사이드바 닫기 버튼 클릭시, 오픈 여부를 상위 컴포넌트로 보냄

watch(
  () => props.open,
  (newState) => {
    sidebarOpen.value = newState
  }
) // 상위 컴포넌트에서 보낸 오픈 여부 변경시, 현재 컴포넌트의 오픈 여부를 갱신
</script>

<template>
  <div class="sidebar" :class="{ 'sidebar-open': sidebarOpen }" style="max-width: 400px">
    <!-- 사이드바 닫기 버튼-->
    <button @click="closeSidebar">
      <span class="material-symbols-outlined"> close </span>
    </button>
    <!-- 사이드바에 배치되는 메뉴 -->
    <div class="menu-item" v-for="menu in props.sideMenu" :key="menu" @click="$router.push(menu.link)">
      {{ menu.name }}
    </div>
  </div>
</template>

<style scoped>
.sidebar {
  position: fixed;
  top: 0;
  left: -250px;
  width: 250px;
  height: 100%;
  background-color: #034c8c;
  color: #fff;
  padding: 20px;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.2);
  transition: left 0.3s ease;
}

.sidebar button {
  font-size: 20px;
  cursor: pointer;
  background: none;
  border: none;
  color: #fff;
  margin-left: 75%;
}

.sidebar-open {
  left: 0;
}

.sidebar {
  padding: 2rem 0;
  font-size: 1.5rem;
}

.menu-item {
  margin-top: 1vh;
  margin-left: 2vw;
  padding: 0.7rem;
  padding-left: 3rem;
  text-align: left;
}
.menu-item:hover {
  cursor: pointer;
}

.material-symbols-outlined {
  font-size: 2rem;
}
</style>
