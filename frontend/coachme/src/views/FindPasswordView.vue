<script setup>
import { ref, computed } from 'vue'
import { postLoginRequest } from '../utils/api/member-api'
import { LoginRequestDto } from '../utils/api/dto/member-dto'
import { validateId, validateChangePassword } from '../utils/functions/member'
import { useRouter } from 'vue-router'
import navbar from '../components/molecules/LoginNavBar.vue'
import CustomInput from '../components/atoms/CustomInput.vue'
import CustomButton from '../components/atoms/CustomButton.vue'
import footerBar from '../components/molecules/CustomFooter.vue'

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
    // 변경여부 확인
    if (!confirm('비밀번호를 변경하시겠습니까?')) return
    // validation
    validateChangePassword(id, email)

    // 검증 끝나면 dto 생성 후 API 호출
    const dto = new LoginRequestDto(id, email)
    postLoginRequest(
      dto,
      () => {
        alert('비밀번호 변경이 완료되었습니다. 임시 비밀번호는 이메일로 전송됩니다.')
        router.push('/login')
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
  <div class="nav-bar">
    <navbar />
  </div>
  <div class="all">
    <div class="main-layout">
      <div class="find-outside shadow-3">
        <form @submit.prevent="changePassword(id, email)">
          <div class="find-title">
            <q-btn flat @click="router.push('/')">
              <img src="../assets/img/logo.png" width="150px" />
            </q-btn>
          </div>
          <div class="find-input">
            <div class="id-input">
              <div class="id-input-title">
                <CustomInput
                  v-model="id"
                  label="아이디"
                  error-message="영어와 숫자로 4글자 이상 입력하세요."
                  :error="!isValidId"
                  maxlength="20"
                />
              </div>
            </div>
            <div class="email-input">
              <div class="email-input-title">
                <CustomInput
                  type="email"
                  v-model="email"
                  label="이메일"
                  hint="가입하신 이메일 주소를 입력하세요."
                  maxlength="50"
                />
              </div>
            </div>
          </div>
          <div class="find-btn-container">
            <div class="find-btn-wrapper">
              <CustomButton
                type="submit"
                :name="`비밀번호 변경`"
                :label="`비밀번호 변경`"
                :background="`#fcbf17`"
                :color="`#034c8c`"
              />
            </div>
            <div class="find-btn-wrapper">
              <CustomButton
                :name="`돌아가기`"
                :label="`돌아가기`"
                :background="`#034c8c`"
                :color="`#fcbf17`"
                @click="$router.push('/login')"
              />
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div class="footer"><footerBar /></div>
</template>
<style scoped>
.all {
  display: flex;
  justify-content: center;
}
.main-layout {
  width: 1280px;
  min-height: calc(80vh - 70px);
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
}

.nav-bar {
  width: 100%;
  height: 70px;
  justify-content: center;
  text-align: center;
  border-bottom: #034c8c 1px solid;
  display: flex;
  justify-content: center;
}

.find-outside {
  background-color: white;
  width: 30vw;
  height: 60vh;
  margin: auto;
  margin-top: 5vh;
  padding-top: 2vh;
  border-radius: 5%;
  display: flex;
  flex-direction: column;
}

.find-title {
  display: flex;
  justify-content: center;
}
.find-input {
  width: 50%;
  margin: auto;
  margin-top: 1rem;
  margin-bottom: 1rem;
  display: flex;
  flex-direction: column;
}
.find-btn-container {
  margin: auto;
  width: 70%;
  margin-top: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.find-btn-wrapper {
  margin-top: 1rem;
}

.footer {
  height: 20vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}
</style>
