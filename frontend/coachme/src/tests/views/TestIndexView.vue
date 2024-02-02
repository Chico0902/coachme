<script setup></script>
<template>
  <div class="q-ma-lg">
    <h3 class="mt-5">테스트를 위한 Index Page</h3>
    <div class="q-pa-md q-gutter-sm">
      <RouterLink :to="{ name: 'test-login' }">
        <q-btn color="white" text-color="black" label="로그인" />
      </RouterLink>
      <RouterLink :to="{ name: 'test-regist' }">
        <q-btn color="primary" label="회원가입" />
      </RouterLink>
      <RouterLink :to="{ name: 'test-newpassword' }">
        <q-btn color="secondary" label="비밀번호 찾기" />
      </RouterLink>
      <RouterLink :to="{ name: 'test-profile' }">
        <q-btn color="warning" label="프로필 업로드" />
      </RouterLink>
      <RouterLink :to="{ name: 'test-token' }">
        <q-btn color="black" label="토큰 테스트" />
      </RouterLink>
    </div>
  </div>
</template>
