<script setup>
import { ref } from 'vue'
import { postLoginRequest } from '../../utils/api/member-api'
import { LoginRequestDto } from '../../utils/api/dto/member-dto'
import { validateChangePassword } from '../../utils/functions/member'
import { useRouter } from 'vue-router'

// variables
const id = ref('')
const email = ref('')
const router = useRouter()

// methods
const changePassword = (id, email) => {
  try {
    // validation
    validateChangePassword(id, email)

    // 검증 끝나면 dto 생성 후 API 호출
    const dto = new LoginRequestDto(id, email)
    postLoginRequest(
      dto,
      () => {
        alert('비밀번호 변경이 완료되었습니다. 임시 비밀번호는 이메일로 전송됩니다.')
        router.push({ name: 'test-login' })
      },
      // API 호출 실패 시 오류메시지 콘솔에 출력
      (fail) => console.log(fail)
    )
    // 검증 실패 시 오류메시지 출력
  } catch (e) {
    alert(e.message)
  }
}
</script>
<template>
  <div class="q-pa-md">
    <form @submit.prevent="login(id, pw)">
      <div class="q-gutter-md" style="max-width: 500px">
        <q-input standout="bg-teal text-white" v-model="id" label="아이디" maxlength="20" />
        <q-input
          standout="bg-teal text-white"
          type="email"
          v-model="email"
          label="이메일"
          maxlength="50"
        />
        <q-btn type="submit" color="secondary" label="비밀번호 찾기" style="justify-self: center" />
      </div>
    </form>
  </div>
</template>
