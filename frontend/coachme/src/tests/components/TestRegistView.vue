<script setup>
import { MemberRegistRequestDto } from '@/utils/api/dto/member-dto'
import { postMember } from '@/utils/api/member-api'
import {
  validateId,
  validatePassword,
  validateName,
  validateNickName,
  validateRegist
} from '@/utils/functions/member'
import { useRouter } from 'vue-router'
import { computed, ref } from 'vue'

// variables
const router = useRouter()
const id = ref('')
const pw = ref('')
const name = ref('')
const nick = ref('')
const email = ref('')

// methods

// 아이디 검증
const isValidId = computed(() => {
  // 글씨 쓰기전에 작동 안하게 하기
  if (id.value === '') {
    return true
  }

  // 글씨 쓰면 유효성 검증하기
  return validateId(id.value)
})

// 비밀번호 검증
const isValidPassword = computed(() => {
  // 글씨 쓰기전에 작동 안하게 하기
  if (pw.value === '') {
    return true
  }

  // 글씨 쓰면 유효성 검증하기
  return validatePassword(pw.value)
})

// 이름 검증
const isValidName = computed(() => {
  // 글씨 쓰기전에 작동 안하게 하기
  if (name.value === '') {
    return true
  }

  // 글씨 쓰면 유효성 검증하기
  return validateName(name.value)
})

// 닉네임 검증
const isValidNickName = computed(() => {
  // 글씨 쓰기전에 작동 안하게 하기
  if (nick.value === '') {
    return true
  }

  // 글씨 쓰면 유효성 검증하기
  return validateNickName(nick.value)
})

const regist = (id, pw, name, nick, email) => {
  try {
    // validation
    validateRegist(id, pw)

    // dto 생성 및 호출
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
        <q-input
          standout="bg-teal text-white"
          v-model="id"
          label="아이디"
          maxlength="20"
          hint="영어와 숫자로 된 ID를 입력하세요"
          error-message="영어와 숫자만 입력 가능합니다."
          :error="!isValidId"
        />
        <q-input
          standout="bg-teal text-white"
          type="password"
          v-model="pw"
          label="비밀번호"
          maxlength="30"
          hint="비밀번호를 입력하세요."
          error-message="비밀번호는 9글자 이상으로, 대문자와 특수문자를 포함해야 합니다."
          :error="!isValidPassword"
        />
        <q-input
          standout="bg-teal text-white"
          v-model="name"
          label="이름"
          maxlength="10"
          hint="이름은 한글로 입력하세요."
          error-message="한글을 제외한 문자는 입력할 수 없습니다."
          :error="!isValidPassword"
        />
        <q-input
          standout="bg-teal text-white"
          v-model="nick"
          label="닉네임"
          maxlength="10"
          hint="닉네임을 입력하세요."
          error-message="닉네임은 특수문자를 포함할 수 없습니다."
          :error="!isValidPassword"
        />
        <q-input
          standout="bg-teal text-white"
          type="email"
          v-model="email"
          label="이메일"
          maxlength="50"
          hint="이메일 주소를 입력하세요."
        />
        <q-btn
          type="submit"
          color="secondary"
          label="회원가입"
          style="justify-self: center"
          hint="비밀번호를 입력하세요."
          error-message="비밀번호는 9글자 이상으로, 대문자와 특수문자를 포함해야 합니다."
          :error="!isValidPassword"
        />
      </div>
    </form>
  </div>
</template>
