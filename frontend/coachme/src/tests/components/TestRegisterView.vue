<script setup>
import { ref } from 'vue'

// variables
const id = ref('')
const pw = ref('')
const name = ref('')
const nick = ref('')
const email = ref('')

// methods
const regist = async (id, pw, name, nick, email) => {}
</script>
<template>
  <div class="q-pa-md">
    <form @submit.prevent="regist(id, pw, name, nick, email)">
      <div class="q-gutter-md" style="max-width: 500px">
        <q-input standout="bg-teal text-white" v-model="id" label="아이디" maxlength="20" />
        <q-input
          standout="bg-teal text-white"
          type="password"
          v-model="pw"
          label="비밀번호"
          maxlength="30"
        />
        <q-input standout="bg-teal text-white" v-model="name" label="이름" maxlength="10" />
        <q-input standout="bg-teal text-white" v-model="nick" label="닉네임" maxlength="10" />
        <q-input
          standout="bg-teal text-white"
          type="email"
          v-model="email"
          label="이메일"
          maxlength="50"
        />
      </div>
    </form>
  </div>
</template>
