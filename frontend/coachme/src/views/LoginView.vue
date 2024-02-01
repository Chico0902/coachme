<script setup>
import { ref, computed } from 'vue'
import { postLoginRequest } from '../utils/api/auth-api'
import { LoginRequestDto } from '../utils/api/dto/auth-dto'
import { validateId, validatePassword, validateLogin } from '../utils/functions/member'
import { useRouter } from 'vue-router'
import { useMemberStore } from '@/stores/member'
import { storeToRefs } from 'pinia'
import navbar from '../components/molecules/LoginNavBar.vue'
import CustomInput from '../components/atoms/CustomInput.vue'
import CustomButton from '../components/atoms/CustomButton.vue'
import footerBar from '../components/molecules/CustomFooter.vue'

// variables
const id = ref('')
const pw = ref('')
const router = useRouter()
const memberStore = useMemberStore()
const { longId, stringId, name, privilege, isLogin } = storeToRefs(memberStore)

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
  // validation
  validateLogin(id, pw)

  // 검증 끝나면 dto 생성 후 API 호출
  const dto = new LoginRequestDto(id, pw)
  postLoginRequest(
    dto,
    (success) => {
      isLogin.value = true
      longId.value = success.data.longId
      stringId.value = success.data.stringId
      name.value = success.data.name
      // privilege.value = success.data.privilege
      alert('로그인 성공')
      // router.push('/')
    },
    (error) => {
      alert('로그인 실패')
      console.log(error)
    }
  )
}
</script>

<template>
  <div class="nav-bar">
    <navbar />
  </div>
  <div class="all">
    <div class="main-layout">
      <div class="login-outside shadow-3">
        <div class="login-title">
          <q-btn flat @click="router.push('/')">
            <img src="../assets/img/logo.png" />
          </q-btn>
        </div>
        <form @submit.prevent="login(id, pw)">
          <div class="login-input">
            <div class="id-input">
              <CustomInput
                v-model="id"
                label="아이디"
                error-message="영어와 숫자로 4글자 이상 입력하세요."
                :error="!isValidId"
                maxlength="20"
              />
            </div>
            <div class="password-input">
              <CustomInput
                type="password"
                v-model="pw"
                label="비밀번호"
                error-message="9글자 이상으로, 대문자와 특수문자를 포함해야 합니다."
                :error="!isValidPassword"
                maxlength="30"
              />
            </div>
          </div>
          <div class="login-and-new">
            <div class="login-button-wrapper">
              <CustomButton
                type="submit"
                :name="`로그인 버튼`"
                :label="`로그인`"
                :background="`#fcbf17`"
                :color="`#034c8c`"
              />
            </div>
            <div class="new-and-find">
              <div class="new"><RouterLink to="/regist">회원가입</RouterLink></div>
              <span>&nbsp;|&nbsp;</span>
              <div class="find"><RouterLink to="/find-password">비밀번호 찾기</RouterLink></div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div class="footer"><footerBar /></div>
</template>

<style scoped>
img {
  width: 10vw;
  max-height: 15vh;
}
a {
  text-decoration: none;
  color: #034c8c;
}
h1 {
  font-size: 2.5rem;
  font-weight: 350;
  line-height: 1.5rem;
  letter-spacing: 3px;
  padding: 0;
  margin: 0;
}
h2 {
  font-size: 1rem;
  font-weight: 200;
  line-height: 1.5rem;
  padding: 0;
  margin: 0;
}
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
.login-outside {
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

.login-title {
  display: flex;
  justify-content: center;
}
.login-input {
  width: 50%;
  margin: auto;
  margin-top: 1rem;
  margin-bottom: 1.5rem;
  display: flex;
  flex-direction: column;
}
.id-input {
  margin-bottom: 1rem;
}
.login-and-new {
  width: 50%;
  margin: auto;
  margin-top: 2vh;
}
.login-button-wrapper {
  width: 50%;
  margin: auto;
  margin-bottom: 3vh;
  display: flex;
  justify-content: center;
}

.new-and-find {
  margin: auto;
  justify-content: center;
  display: flex;
  margin-bottom: 5vh;
}
.new {
  display: inline-block;
}

.find {
  display: inline-block;
}

.footer {
  height: 20vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}
</style>
