<script setup>
import { MemberRegistRequestDto } from '@/utils/api/dto/member-dto'
import { postMember } from '@/utils/api/member-api'
import { validateRegist } from '@/utils/functions/member'
import { useRouter } from 'vue-router'
import { ref } from 'vue'

// variables
const router = useRouter()
const id = ref('')
const pw = ref('')
const name = ref('')
const nick = ref('')
const email = ref('')

// methods
const regist = async (id, pw, name, nick, email) => {
  try {
    // validation
    validateRegist(id, pw, name, nick, email)

    // 검증 끝나면 dto 생성 후 API 호출
    const dto = new MemberRegistRequestDto(id, pw, name, nick, email)
    postMember(
      dto,
      (success) => {
        alert(success.data.message)
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
        <q-btn type="submit" color="secondary" label="회원가입" style="justify-self: center" />
      </div>
    </form>
  </div>
</template>
