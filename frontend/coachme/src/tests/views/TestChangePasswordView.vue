<script setup>
import { ref, computed } from 'vue'
import { postLoginRequest } from '../../utils/api/auth-api'
import { LoginRequestDto } from '../../utils/api/dto/auth-dto'
import { validateId, validateChangePassword } from '../../utils/functions/member'
import { useRouter } from 'vue-router'

// variables
const id = ref('')
const email = ref('')
const router = useRouter()

// methods

// 아이디 검증
const isValidId = computed(() => {
  // 글씨 쓰기전에 작동 안하게 하기
  if (id.value === '') return true
  return validateId(id.value)
})

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
    <form @submit.prevent="changePassword(id, email)">
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
          type="email"
          v-model="email"
          label="이메일"
          hint="이메일 주소를 입력하세요."
          maxlength="50"
        />
        <q-btn type="submit" color="secondary" label="비밀번호 찾기" style="justify-self: center" />
      </div>
    </form>
  </div>
</template>
