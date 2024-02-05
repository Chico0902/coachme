<script setup>
import axios from 'axios'
import { inject } from 'vue'
import { getAccessToken, getRefreshToken } from '@/utils/functions/auth'

const dafaultAxios = backendAxios()
const $cookies = inject('$cookies')

function backendAxios() {
  const instance = axios.create({
    baseURL: `http://localhost`,
    headers: {
      'Content-Type': 'application/json;charset=utf-8'
    }
  })
  return instance
}

const getToken = () => {
  // 토큰 요청 보내기
  dafaultAxios
    .post('/auth/login', { stringId: 'coach', pw: 'coach' })
    .then(() => {
      console.log($cookies.get('accessToken'))
    })
    .catch((fail) => console.log(fail))
}

const postWithToken = () => {
  // 토큰 달고 요청 보내기
  dafaultAxios
    .post(
      '/auth/login',
      { stringId: 'coach', pw: 'coach' },
      { headers: { Authorization: 'Bearer ' + getRefreshToken() } }
    )
    .then(() => {
      console.log($cookies.get('refreshToken'))
    })
    .catch((fail) => console.log(fail))
}
</script>
<template>
  <h3>토큰 테스트</h3>
  <q-btn round dense flat icon="send" @click="getToken" />
  <q-btn round dense flat icon="send" @click="postWithToken" />
</template>
