<script setup>
import navbar from '@/components/molecules/LoginNavBar.vue'
import footerBar from '@/components/molecules/CustomShortFooter.vue'
<<<<<<< HEAD
import router from '@/router'
=======
import MypageSidebar from '@/components/molecules/MypageSidebar.vue'
import { getAllMemberInfo } from '@/utils/api/admin-api'
import { ref, onBeforeMount } from 'vue'
const SideButtonList = [
  { name: '회원목록', link: '/mypage/admin/list', cssClass: 'manage-button' },
  { name: '권한요청', link: '/mypage/admin/elevations', cssClass: 'manage-button' }
]
const columns = [
  {
    name: 'id',
    required: true,
    label: '아이디',
    align: 'left',
    field: (row) => row.id,
    format: (val) => `${val}`,
    sortable: true
  },
  { name: 'priv', align: 'left', label: '권한', field: 'priv', sortable: true },
  { name: 'name', align: 'left', label: '이름', field: 'name', sortable: true },
  { name: 'nick', align: 'left', label: '닉네임', field: 'nick', sortable: true },
  { name: 'email', align: 'left', label: '이메일', field: 'email', sortable: true },
  { name: 'cdate', align: 'left', label: '가입일', field: 'cdate', sortable: true },
  { name: 'elev', align: 'center', label: '권한요청', field: 'elev', sortable: true }
]
const rows = ref([])

onBeforeMount(() => {
  getAllMemberInfo(
    (success) => {
      rows.value = success.data.data
    },
    (fail) => {
      console.log(fail)
    }
  )
})
>>>>>>> feature/Mypage-중복코드-제거
</script>
<template>
  <div class="nav-bar">
    <navbar />
  </div>
  <div class="all">
    <div class="main-layout">
      <div class="mypage-outside">
<<<<<<< HEAD
        <div class="sidebar">
          <div class="sidebar-button shadow-3"><p>회원목록</p></div>
          <div class="sidebar-button shadow-3" @click="router.push('/mypage/admin/elevations')"><p>권한요청</p></div>
        </div>
        <div class="mainpage shadow-3">메인</div>
=======
        <MypageSidebar :button-list="SideButtonList" />
        <div class="mainpage shadow-3">
          <h1>전체 회원목록</h1>
          <q-table
            class="my-sticky-header-table"
            flat
            bordered
            title="회원정보"
            dense
            :rows="rows"
            :columns="columns"
            row-key="name"
          />
        </div>
>>>>>>> feature/Mypage-중복코드-제거
      </div>
    </div>
  </div>
  <div class="footer"><footerBar /></div>
</template>

<style scoped>
<<<<<<< HEAD
p {
  margin: 0;
=======
h1 {
  font-size: 1.5rem;
  margin: 0;
  margin-bottom: 1rem;
  padding: 0;
  line-height: normal;
>>>>>>> feature/Mypage-중복코드-제거
}
.all {
  display: flex;
  justify-content: center;
}
.main-layout {
  width: 1280px;
  min-height: calc(90vh - 70px);
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
}
<<<<<<< HEAD

=======
>>>>>>> feature/Mypage-중복코드-제거
.nav-bar {
  width: 100%;
  height: 70px;
  justify-content: center;
  text-align: center;
  border-bottom: #034c8c 1px solid;
  display: flex;
  justify-content: center;
}

.mypage-outside {
  display: flex;
  justify-content: space-between;
}
<<<<<<< HEAD
.sidebar {
  width: 15%;
  height: 70vh;
  margin: auto;
}

.sidebar-button {
  width: 100%;
  height: 8vh;
  border-radius: 1rem;
  margin-top: 0;
  margin-bottom: 1.5rem;
  display: flex;
  justify-content: center;
  align-items: center;
}
.sidebar-button p {
  font-size: 1rem;
  letter-spacing: 0.1rem;
}
.sidebar-button:first-child {
  background-color: #bd58d9;
  opacity: 90%;
  color: #fff;
}
.sidebar-button:hover {
  background-color: #bd58d9;
  opacity: 100%;
  color: #fff;
  cursor: pointer;
}
=======
>>>>>>> feature/Mypage-중복코드-제거
.mainpage {
  background-color: white;
  width: 80%;
  height: 70vh;
  margin: auto;
  margin-top: 5vh;
  margin-bottom: 5vh;
<<<<<<< HEAD
  border-radius: 1.5rem;
  display: flex;
  /* justify-content: center; */
  flex-direction: column;
  overflow: scroll;
=======
  padding: 3rem;
  text-align: center;
  border-radius: 1.5rem;
>>>>>>> feature/Mypage-중복코드-제거
}
.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}
</style>
