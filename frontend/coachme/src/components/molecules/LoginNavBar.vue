<!-- 로그인 후 nav 컴포넌트 
필요한 정보 없음

-->

<script setup>
import profile from '../atoms/ProfileImage.vue'
import sidebar from '../atoms/Sidebar.vue'
import buttons from '@/components/atoms/CustomButton.vue'
import { ref } from 'vue'
import { useMemberStore } from '@/stores/member'
import { storeToRefs } from 'pinia'
import { useAuthStore } from '@/stores/auth'

const memberStore = useMemberStore()
const authStore = useAuthStore()
const { profileImageUrl } = storeToRefs(memberStore)
const { isLogin } = storeToRefs(authStore)

const sidebarOpen = ref(false)
// 사이드바 오픈 여부

const toggleSidebar = () => {
  sidebarOpen.value = !sidebarOpen.value
} // 햄버거 버튼 클릭시 오픈 여부 변경

const closeSidebar = () => {
  sidebarOpen.value = false
}
// 사이드바에서 X키를 눌렀다면 오픈 여부 변경

const sideMenu = [{ name: '홈으로', link: '/' }]
if (isLogin.value === true) sideMenu.push({ name: '마이페이지', link: '/mypage' })

sideMenu.push(
  { name: '코치찾기', link: '/search/coach/list/all/all/all' },
  { name: '코칭찾기', link: '/search/coaching/list/all/all/all' }
)

if (isLogin.value === true) sideMenu.push({ name: '로그아웃', link: '/login' })
else sideMenu.push({ name: '로그인', link: '/login' })

// 사이드바 메뉴는 여기서 변경
</script>

<template>
  <div class="menu-container">
    <q-toolbar class="text-black rounded-borders" style="width: 80vw">
      <!-- 햄버거 버튼 -->
      <q-btn flat class="burger_button" @click="toggleSidebar">
        <div class="navbar_burger">
          <span class="material-symbols-outlined"> menu </span>
        </div>
      </q-btn>
      <q-space></q-space>

      <!-- 로고 -->
      <q-btn flat>
        <router-link to="/">
          <img src="../../assets/icons/6.png" width="150px" />
        </router-link>
      </q-btn>
      <q-space></q-space>

      <!-- 프로필 사진 -->
      <template v-if="isLogin">
        <q-btn flat>
          <router-link to="/mypage">
            <profile :img="profileImageUrl"></profile>
          </router-link>
        </q-btn>
      </template>
      <template v-else>
        <RouterLink to="/login">
          <buttons flat :name="`login`" :label="`로그인`"></buttons>
        </RouterLink>
      </template>
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

.material-symbols-outlined {
  font-size: 36px;
}
</style>
