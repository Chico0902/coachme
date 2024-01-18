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
    </div>
  </div>
</template>
