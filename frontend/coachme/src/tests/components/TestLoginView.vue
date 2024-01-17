<script setup>
import { ref } from 'vue'
import { postLoginRequest } from '../../utils/api/member-api'
import { LoginRequestDto } from '../../utils/api/dto/member-dto'
import { validateLogin } from '../../utils/functions/login'
import { useMemberStore } from '../../stores/member-info'
import { storeToRefs } from 'pinia'

// variables
const id = ref('')
const pw = ref('')
const memberStore = useMemberStore()
const { memberId, privilege, accessToken, refreshToken } = storeToRefs(memberStore)

// methods
const login = async (id, pw) => {
  // validation
  if (!validateLogin(id, pw)) {
    alert('유효하지 않은 로그인 시도입니다.')
    return
  }

  const dto = new LoginRequestDto(id, pw)
  postLoginRequest(
    dto,
    (success) => {
      memberId.value = success.data.memberId
      privilege.value = success.data.privilege
      accessToken.value = success.data.accessToken
      refreshToken.value = success.data.refreshToken
      alert('로그인 성공')
    },
    (fail) => console.log(fail)
  )
}
</script>
<template>
  <div>
    <div class="q-pa-md">
      <form @submit.prevent="login(id, pw)">
        <div class="q-gutter-md" style="max-width: 500px">
          <q-input standout="bg-teal text-white" v-model="id" label="아이디" />
          <q-input standout="bg-teal text-white" type="password" v-model="pw" label="비밀번호" />
          <q-btn type="submit" color="secondary" label="로그인" style="justify-self: center" />
        </div>
      </form>
    </div>
  </div>
</template>
