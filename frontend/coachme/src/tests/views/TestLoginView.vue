<script setup>
import { ref, computed } from 'vue'
import { postLoginRequest } from '../../utils/api/member-api'
import { LoginRequestDto } from '../../utils/api/dto/member-dto'
import { validateId, validatePassword, validateLogin } from '../../utils/functions/member'
import { useMemberStore } from '../../stores/member-info'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router'

// variables
const id = ref('')
const pw = ref('')
const memberStore = useMemberStore()
const { memberId, privilege, accessToken, refreshToken } = storeToRefs(memberStore)
const router = useRouter()

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

// methods
const login = (id, pw) => {
  try {
    // validation
    validateLogin(id, pw)

    // 검증 끝나면 dto 생성 후 API 호출
    const dto = new LoginRequestDto(id, pw)
    postLoginRequest(
      dto,
      (success) => {
        memberId.value = success.data.memberId
        privilege.value = success.data.privilege
        accessToken.value = success.data.accessToken
        refreshToken.value = success.data.refreshToken
        alert('로그인 성공')
        router.push({ name: 'test-index' })
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
        <q-btn type="submit" color="secondary" label="로그인" style="justify-self: center" />
      </div>
    </form>
  </div>
</template>
