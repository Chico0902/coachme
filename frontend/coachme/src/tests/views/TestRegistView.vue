<script setup>
import { MemberRegistRequestDto } from '@/utils/api/dto/member-dto'
import { postMember } from '@/utils/api/member-api'
import {
  validateId,
  validatePassword,
  validatePasswordConfirm,
  validateName,
  validateNickName,
  validateRegist
} from '@/utils/functions/member'
import { useRouter } from 'vue-router'
import { computed, ref } from 'vue'

// variables
const openModal = ref(false)
const router = useRouter()
const id = ref('')
const pw = ref('')
const pwConfirm = ref('')
const name = ref('')
const nick = ref('')
const email = ref('')

// methods

// 아이디 검증
const isValidId = computed(() => {
  // 글씨 쓰기전에 작동 안하게 하기(이하 동일)
  if (id.value === '') return true
  return validateId(id.value)
})

// 비밀번호 검증
const isValidPassword = computed(() => {
  if (pw.value === '') return true
  return validatePassword(pw.value)
})

// 비밀번호 확인 검증
const isValidPasswordConfirm = computed(() => {
  if (pw.value === '') return true
  return validatePasswordConfirm(pw.value, pwConfirm.value)
})

// 이름 검증
const isValidName = computed(() => {
  if (name.value === '') return true
  return validateName(name.value)
})

// 닉네임 검증
const isValidNickName = computed(() => {
  if (nick.value === '') return true
  return validateNickName(nick.value)
})

const regist = (id, pw, pwConfirm, name, nick, email) => {
  try {
    // validation
    validateRegist(id, pw, pwConfirm, name, nick, email)

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
    <form @submit.prevent="regist(id, pw, pwConfirm, name, nick, email)">
      <div class="q-gutter-lg" style="max-width: 1000px">
        <q-input
          standout="bg-teal text-white"
          v-model="id"
          label="아이디"
          hint="영어와 숫자로 4글자 이상 입력하세요."
          error-message="잘못된 입력입니다."
          :error="!isValidId"
          maxlength="20"
        />
        <q-input
          standout="bg-teal text-white"
          type="password"
          v-model="pw"
          label="비밀번호"
          error-message="비밀번호는 9글자 이상으로, 대문자와 특수문자를 포함해야 합니다."
          :error="!isValidPassword"
          maxlength="30"
        />
        <q-input
          standout="bg-teal text-white"
          type="password"
          v-model="pwConfirm"
          label="비밀번호"
          error-message="비밀번호와 일치하지 않습니다."
          :error="!isValidPasswordConfirm"
          maxlength="30"
        />
        <q-input
          standout="bg-teal text-white"
          v-model="name"
          label="이름"
          hint="한글만 가능합니다."
          error-message="한글을 제외한 문자는 입력할 수 없습니다."
          :error="!isValidName"
          maxlength="10"
        />
        <q-input
          standout="bg-teal text-white"
          v-model="nick"
          label="닉네임"
          hint="영어, 숫자, 한글만 가능합니다."
          error-message="닉네임은 특수문자를 포함할 수 없습니다."
          :error="!isValidNickName"
          maxlength="10"
        />
        <q-input
          standout="bg-teal text-white"
          type="email"
          v-model="email"
          label="이메일"
          hint="이메일 주소를 입력하세요."
          maxlength="50"
        />
        <q-btn label="회원정보 수정" color="primary" @click="openModal" />
      </div>
    </form>
  </div>
  <q-dialog v-model="openModal">
    <q-card>
      <q-card-section>
        <div class="text-h6">Alert</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        Lorem ipsum dolor sit amet consectetur adipisicing elit. Rerum repellendus sit voluptate voluptas eveniet porro.
        Rerum blanditiis perferendis totam, ea at omnis vel numquam exercitationem aut, natus minima, porro labore.
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="OK" color="primary" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>
