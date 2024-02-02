<script setup>
import CustomButton from '@/components/atoms/CustomButton.vue'
import CustomInput from '@/components/atoms/CustomInput.vue'
import ProfileImage from '@/components/atoms/ProfileImage.vue'
import { validatePassword, validateNickName } from '@/utils/functions/member'
import { patchMemberInfo } from '@/utils/api/member-api'
import { MemberInfoChangeRequestDto } from '@/utils/api/dto/member-dto'
import { computed, ref } from 'vue'
import { decodeToken } from '@/utils/functions/auth'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'

/**
 * VARIABLES
 */

// variables
const openModal = ref(false)
const pw = ref('')
const nick = ref('')
const email = ref('')

// token에서 받아옴
const authStore = useAuthStore()
const { accessToken } = storeToRefs(authStore)
const tokenValue = decodeToken(accessToken.value)
const name = tokenValue.name
const longId = tokenValue.id

// methods

// 비밀번호 검증
const isValidPassword = computed(() => {
  if (pw.value === '') return true
  return validatePassword(pw.value)
})

// 닉네임 검증
const isValidNickName = computed(() => {
  if (nick.value === '') return true
  return validateNickName(nick.value)
})

const changeMemberInfo = (pw, nick, email) => {
  // dto 생성 및 호출
  const dto = new MemberInfoChangeRequestDto(pw, nick, email)
  patchMemberInfo(
    longId,
    dto,
    (success) => {
      alert(success.data.message)
      window.location.reload()
    },
    (fail) => {
      alert(fail.data.message)
    }
  )
}
</script>
<template>
  <div>
    <div class="main-profile">
      <div class="profile-img">
        <div><ProfileImage size="100px" /></div>
        <div class="profile-img-icon">
          <q-btn flat>
            <span class="material-symbols-outlined"> add_photo_alternate </span>
          </q-btn>
          <q-btn flat>
            <span class="material-symbols-outlined"> delete_forever </span>
          </q-btn>
        </div>
      </div>
      <div class="profile-detail">
        <CustomInput type="textarea" placeholder="소개글을 입력해주세요." />
      </div>
    </div>
    <div class="detail-member-info">
      <h6 class="detail-member-info-id">{{ name }}</h6>

      <div class="detail-member-info-input">
        <q-input
          standout="bg-teal text-white"
          v-model="nick"
          label="닉네임"
          hint="영어, 숫자, 한글만 가능합니다."
          error-message="닉네임은 특수문자를 포함할 수 없습니다."
          :error="!isValidNickName"
          maxlength="10"
        />
      </div>
      <div class="detail-member-info-input">
        <q-input
          standout="bg-teal text-white"
          type="email"
          v-model="email"
          label="이메일"
          hint="이메일 주소를 입력하세요."
          maxlength="50"
        />
      </div>
      <div class="btn-container">
        <CustomButton
          style="height: 56px"
          label="회원정보 수정"
          background="#fcbf17"
          color="black"
          @click="openModal = true"
        ></CustomButton>
      </div>
    </div>
  </div>

  <q-dialog v-model="openModal">
    <q-card>
      <q-card-section>
        <div class="text-h6">회원정보 수정</div>
      </q-card-section>
      <div class="editor-detail">회원정보를 변경하기 위해 비밀번호를 올바르게 입력하세요.</div>

      <q-card-section class="q-pt-none">
        <q-input
          standout="bg-teal text-white"
          type="password"
          v-model="pw"
          label="비밀번호"
          error-message="비밀번호는 9글자 이상으로, 대문자와 특수문자를 포함해야 합니다."
          :error="!isValidPassword"
          maxlength="30"
        />
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="수정하기" color="primary" @click="changeMemberInfo(pw, nick, email)" />
        <q-btn flat label="취소" color="primary" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
.main-profile {
  width: 80%;
  background-color: aliceblue;
  margin: 30px auto;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.profile-img {
  display: inline-block;
  margin: 1rem 0;
}
.profile-detail {
  margin-top: 1.5rem;
}
.editor-detail {
  color: #034c8c;
  margin: 0.5rem 1rem;
}
.detail-div {
  display: flex;
  justify-content: left;
  margin: 10px 0 10px 100px;
  align-items: center;
}

.detail-member-info {
  width: 60%;
  margin: auto;
}

.detail-member-info-id {
  font-size: 1.2rem;
  margin: 1rem 0.5rem;
}
.detail-member-info-input {
  margin-bottom: 1rem;
}
.btn-container {
  display: flex;
  justify-content: center;
  margin: 50px;
}
.material-symbols-outlined {
  font-size: 25px;
}
</style>
