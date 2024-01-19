<script setup>
import sidenav from '../components/molecules/MyPageSideNav.vue'
import inputs from '../components/atoms/Input.vue'
import deleteButton from '../components/molecules/DeleteButton.vue'
import uploadButton from '../components/molecules/UploadImageButton.vue'
import editButton from '../components/molecules/EditButton.vue'

const sideMenu = ["정보수정", "코칭일정", "관심강의", "영상보기", "탈퇴하기"]

</script>

<template>
  <!-- 마이페이지 사이드 메뉴 -->
  <sidenav :sideMenu="sideMenu"></sidenav>

  <!-- textarea -->
  <inputs filled type="textarea" style="max-width: 300px;"></inputs>
  <inputs style="width:150px"></inputs>

  <deleteButton></deleteButton>

  <uploadButton></uploadButton>

  <editButton></editButton>
</template>

<style scoped></style>