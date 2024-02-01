<!-- 브레드크럼브 컴포넌트 = 대분류 소분류 표시 컴포넌트
필요한 정보 : 대분류, 소분류를 배열로 받기
coachingCategory : 대분류, 소분류 카테고리 배열
-->

<script setup>
const props = defineProps({
  coachingCategory : {
    type: Object
  }
})

</script>

<template>
  <q-breadcrumbs class="text-brown">
    <template v-slot:separator>
      <q-icon size="1.5em" name="chevron_right" color="primary"></q-icon>
    </template>

    <q-breadcrumbs-el v-for="menu in props.coachingCategory" :key="menu" :label="menu"></q-breadcrumbs-el>
  </q-breadcrumbs>
</template>

<style scoped></style>