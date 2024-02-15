<script setup>
import navbar from '../components/molecules/LoginNavBar.vue'
import CustomButton from '../components/atoms/CustomButton.vue'
import CustomShortFooter from '@/components/molecules/CustomShortFooter.vue'
import { MemberDuplicateRequestDto, MemberRegistRequestDto } from '@/utils/api/dto/member-dto'
import { postMember, validateDuplicateMember } from '@/utils/api/member-api'
import {
  validateId,
  validatePassword,
  validatePasswordConfirm,
  validateName,
  validateNickName,
  validateRegist
} from '@/utils/functions/member'
import { useRouter } from 'vue-router'
import { watch, computed, ref } from 'vue'

// variables
const router = useRouter()
const id = ref('')
const idCheck = ref(false)
const pw = ref('')
const pwConfirm = ref('')
const name = ref('')
const nick = ref('')
const email = ref('')
const alert = ref(false)
const alertIcon = ref('')
const alertMessage = ref('')

// methods

// 아이디 검증
const isValidId = computed(() => {
  // 글씨 쓰기전에 작동 안하게 하기(이하 동일)
  if (id.value === '') return true

  // 글씨가 변경되면 아이디 검사 다시 수행해야 함
  // eslint-disable-next-line vue/no-side-effects-in-computed-properties
  idCheck.value = false

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
    // 아이디 유효성 검사
    if (idCheck.value == false) {
      alert.value = true
      alertIcon.value = 'warning'
      alertMessage.value = '아이디 중복검사를 먼저 진행해주세요.'
      return
    }

    // validation
    validateRegist(id, pw, pwConfirm, name, nick, email)

    // dto 생성 및 호출
    const dto = new MemberRegistRequestDto(id, pw, name, nick, email)
    postMember(
      dto,
      (success) => {
        alert.value = true
        alertIcon.value = 'check'
        alertMessage.value = '회원가입 완료! 로그인 페이지로 이동합니다.'
        watch(
          () => alert.value,
          () => {
            router.push('/login')
          }
        )
      },
      // API 호출 실패 시 오류메시지 출력
      (fail) => {
        alert.value = true
        alertIcon.value = 'warning'
        alertMessage.value = '잘못된 요청입니다.'
      }
    )
    // 검증 실패 시 오류메시지 출력
  } catch (e) {
    alert.value = true
    alertIcon.value = 'warning'
    alertMessage.value = e.message
  }
}
const confirm = (id) => {
  try {
    if (!validateId(id)) {
      alert.value = true
      alertIcon.value = 'warning'
      alertMessage.value = '유효하지 않은 아이디입니다.'
      return
    }

    // dto 생성 및 호출
    const dto = new MemberDuplicateRequestDto(id)
    validateDuplicateMember(
      dto,
      (success) => {
        idCheck.value = true
        alert.value = true
        alertIcon.value = 'check'
        alertMessage.value = '사용 가능한 아이디입니다.'
      },
      // API 호출 실패 시 오류메시지 콘솔에 출력
      (fail) => {
        if (fail.response.status === 409) {
          alert.value = true
          alertIcon.value = 'warning'
          alertMessage.value = '이미 사용중인 아이디입니다. 다른 아이디를 사용하세요.'
        } else {
          alert.value = true
          alertIcon.value = 'warning'
          alertMessage.value = '잘못된 요청입니다.'
        }
      }
    )
    // 검증 실패 시 오류메시지 출력
  } catch (e) {
    alert.value = true
    alertMessage.value = e.message
  }
}
</script>

<template>
  <div class="nav-bar">
    <navbar />
  </div>
  <div class="all">
    <div class="main-layout">
      <div class="regist-container shadow-3">
        <form @submit.prevent="regist(id, pw, pwConfirm, name, nick, email)">
          <div class="regist-input-container">
            <div class="regist-title"><h1>회원가입</h1></div>
            <div class="regist-input">
              <q-input
                v-model="id"
                label="아이디"
                error-message="영어와 숫자로 4글자 이상 입력하세요."
                :error="!isValidId"
                maxlength="20"
              >
                <template v-slot:append>
                  <CustomButton
                    :name="`아이디 확인`"
                    :label="`확인`"
                    :background="`#034c8c`"
                    :color="`#fcbf17`"
                    @click="confirm(id)"
                  />
                </template>
              </q-input>
            </div>
            <div class="regist-input">
              <q-input
                type="password"
                v-model="pw"
                label="비밀번호"
                error-message="9글자 이상으로, 대문자와 특수문자를 포함해야 합니다."
                :error="!isValidPassword"
                maxlength="30"
              />
            </div>
            <div class="regist-input">
              <q-input
                type="password"
                v-model="pwConfirm"
                label="비밀번호 확인"
                error-message="비밀번호와 일치하지 않습니다."
                :error="!isValidPasswordConfirm"
                maxlength="30"
              />
            </div>
            <div class="regist-input">
              <q-input
                v-model="name"
                label="이름"
                hint="한글만 가능합니다."
                error-message="한글을 제외한 문자는 입력할 수 없습니다."
                :error="!isValidName"
                maxlength="10"
              />
            </div>
            <div class="regist-input">
              <q-input
                v-model="nick"
                label="닉네임"
                hint="영어, 숫자, 한글만 가능합니다."
                error-message="닉네임은 특수문자를 포함할 수 없습니다."
                :error="!isValidNickName"
                maxlength="10"
              />
            </div>
            <div class="regist-input">
              <q-input type="email" v-model="email" label="이메일" hint="이메일 주소를 입력하세요." maxlength="50" />
            </div>
          </div>
          <div class="regist-btn-container">
            <div class="regist-button">
              <CustomButton
                type="submit"
                :name="`회원가입 버튼`"
                :label="`회원가입`"
                :background="`#fcbf17`"
                :color="`#034c8c`"
              />
            </div>
            <div class="regist-button">
              <CustomButton
                :name="`돌아가기 버튼`"
                :label="`돌아가기`"
                :background="`#034c8c`"
                :color="`#fcbf17`"
                @click="router.push('/')"
              />
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div class="footer"><CustomShortFooter /></div>
  <q-dialog v-model="alert">
    <q-card>
      <q-card-section>
        <div class="text-h6">
          <q-icon :name="alertIcon"></q-icon>
        </div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        {{ alertMessage }}
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="OK" color="primary" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
h1 {
  font-size: 1.8rem;
  margin: 0;
  margin-bottom: 1rem;
}
.all {
  display: flex;
  justify-content: center;
}
.main-layout {
  width: 1280px;
  min-height: calc(90vh - 70px);
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

.regist-title {
  text-align: center;
}
.regist-container {
  background-color: white;
  width: 30vw;
  height: 70vh;
  margin: auto;
  margin-top: 5vh;
  margin-bottom: 5vh;
  border-radius: 5%;
  display: flex;
  flex-direction: column;
  overflow: scroll;
}
.regist-input-container {
  width: 50%;
  margin: auto;
  margin-top: 5vh;
  display: flex;
  flex-direction: column;
}
.regist-input {
  margin-bottom: 0.5rem;
}
.regist-input:last-child {
  margin-bottom: 2rem;
}

.regist-btn-container {
  width: 50%;
  margin: auto;
  display: flex;
  justify-content: center;
}
.regist-button {
  width: 50%;
  margin: auto;
  margin-bottom: 2vh;
  display: flex;
  justify-content: center;
}

.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}
</style>
