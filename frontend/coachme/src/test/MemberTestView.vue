<script setup>
import { ref } from 'vue'
import { postLoginRequest } from './member-api'
import { LoginRequestDto } from './dto'

// variables
const id = ref('')
const pw = ref('')

// methods
const login = () => {
  console.log('login')
  const dto = new LoginRequestDto(id.value, pw.value)
  postLoginRequest(
    dto,
    (success) => console.log(success.data),
    (fail) => console.log(fail)
  )
}
</script>
<template>
  <div>
    <div class="q-pa-md">
      <div class="q-gutter-md" style="max-width: 500px">
        <form @submit.prevent="login">
          <q-input standout="bg-teal text-white" v-model="id" label="아이디" />
          <q-input standout="bg-teal text-white" type="password" v-model="pw" label="비밀번호" />
          <q-btn type="submit" color="secondary" label="로그인" style="justify-self: center" />
        </form>
      </div>
    </div>
  </div>
</template>
