<script setup>
import CustomButton from '@/components/atoms/CustomButton.vue'
import CustomInput from '@/components/atoms/CustomInput.vue'
import ProfileImage from '@/components/atoms/ProfileImage.vue'
import EditButton from '@/components/materialIcon/EditButton.vue'
import ProfileImageUploader from '@/components/molecules/ProfileImageUploader.vue'
import Swal from 'sweetalert2'
import { validatePassword, validateNickName } from '@/utils/functions/member'
import {
  getMemberInfo,
  postProfileImage,
  postProfileText,
  patchMemberInfo,
  deleteProfileImage
} from '@/utils/api/member-api'
import { MemberInfoChangeRequestDto, ProfileTextRequestDto } from '@/utils/api/dto/member-dto'
import { computed, onBeforeMount, ref } from 'vue'
import { decodeToken } from '@/utils/functions/auth'
import { useAuthStore } from '@/stores/auth'
import { useMemberStore } from '@/stores/member'
import { storeToRefs } from 'pinia'
import { validateProfileImage } from '@/utils/functions/member'

/**
 * VARIABLES
 */

// store에서 받아옴
const authStore = useAuthStore()
const memberStore = useMemberStore()
const { accessToken } = storeToRefs(authStore)
const { profileImageUrl, profileText } = storeToRefs(memberStore)
const tokenValue = decodeToken(accessToken.value)
const longId = tokenValue.longId

// input values
const openModal = ref(false)
const changeImageModal = ref(false)
const pw = ref('')
const newNick = ref('')
const newEmail = ref('')
const newProfileText = ref(profileText.value)

// API에서 받아옴
const stringId = ref('')
const name = ref('')
const nick = ref('')
const email = ref('')

// 비밀번호 검증
const isValidPassword = computed(() => {
  if (pw.value === '') return true
  return validatePassword(pw.value)
})

// 닉네임 검증
const isValidNickName = computed(() => {
  if (newNick.value === '') return true
  return validateNickName(newNick.value)
})

/**
 * methods
 */

// 최초 멤버 정보 받아오기
onBeforeMount(() => {
  getMemberInfo(
    longId,
    (success) => {
      stringId.value = success.data.stringId
      name.value = success.data.name
      nick.value = success.data.nick
      email.value = success.data.email
    },
    (fail) => console.error(fail)
  )
})

// 프로필 이미지 수정
const changeProfileImage = (newImage) => {
  const newFile = new FormData()
  newFile.append('newFile', newImage)
  if (validateProfileImage(newImage)) {
    postProfileImage(
      longId,
      newFile,
      (success) => {
        Swal.fire({
          title: '이미지 업로드 완료!',
          icon: 'success'
        })
        // alert('이미지 업로드 완료')
        profileImageUrl.value = success.data.profileImageUrl
        changeImageModal.value = false
      },
      (fail) => console.error(fail)
    )
  }
}

const deleteProfileImg = () => {
  Swal.fire({
    title: '이미지 삭제',
    text: '이미지를 삭제하시겠습니까?',
    icon: 'question',
    showCancelButton: true,
    confirmButtonText: '네',
    cancelButtonText: '아니오'
  }).then((result) => {
    if (result.isConfirmed) {
      deleteProfileImage(
        longId,
        () => {
          Swal.fire('프로필 이미지 삭제 완료', '', 'success')
          profileImageUrl.value = '/assets/icons/coame.png'
        },
        (fail) => console.error(fail)
      )
    }
  })
}

function changeProfileText(newProfileText) {
  Swal.fire({
    title: '프로필 변경',
    text: '프로필을 변경하시겠습니까?',
    icon: 'question',
    showCancelButton: true,
    confirmButtonText: '네',
    cancelButtonText: '아니오'
  }).then((result) => {
    if (result.isConfirmed) {
      const dto = new ProfileTextRequestDto(newProfileText)
      postProfileText(
        longId,
        dto,
        () => {
          Swal.fire('프로필 변경 완료', '', 'success')
          profileText.value = newProfileText
        },
        (fail) => console.error(fail)
      )
    }
  })
}

// 회원정보 수정
const changeMemberInfo = (pw, newNick, newEmail) => {
  // 정상요청 검증
  if (!(validatePassword(pw) && validateNickName(newNick))) {
    Swal.fire({
      position: 'top',
      title: '유효하지 않은 입력값이 있습니다.',
      icon: 'info'
    })
    // alert('유효하지 않은 입력값이 있습니다.')
    return
  }
  // dto 생성 및 호출
  const dto = new MemberInfoChangeRequestDto(pw, newNick, newEmail)
  patchMemberInfo(
    longId,
    dto,
    () => {
      Swal.fire({
        title: '회원정보 수정 완료!',
        icon: 'success'
      })
      // alert('회원정보 수정 완료')
      window.location.reload()
    },
    // API 호출 실패 시 오류메시지 콘솔에 출력
    (fail) => {
      console.error(fail)
      if (fail.response.status === 401)
        Swal.fire({
          title: '잘못된 비밀번호입니다!',
          icon: 'info'
        })
      // alert('잘못된 비밀번호입니다.')
      else
        Swal.fire({
          title: '잘못된 요청입니다!',
          icon: 'info'
        })
      // alert('잘못된 요청입니다.')
    }
  )
}
</script>
<template>
  <div>
    <div class="main-profile shadow-2">
      <div class="profile-img">
        <div class="image-container"><ProfileImage size="100px" :img="profileImageUrl" /></div>
        <div class="profile-img-icon">
          <q-btn flat @click="changeImageModal = true">
            <span class="material-symbols-outlined"> add_photo_alternate </span>
          </q-btn>
          <q-btn flat @click="deleteProfileImg">
            <span class="material-symbols-outlined"> delete_forever </span>
          </q-btn>
        </div>
      </div>
      <div class="profile-detail">
        <CustomInput
          type="textarea"
          :placeholder="profileText"
          v-model="newProfileText"
          style="min-width: 10vw; padding-top: 0; padding-bottom: 0"
        />
        <div class="profile-detail-edit">
          <EditButton @click="changeProfileText(newProfileText)" />
        </div>
      </div>
    </div>
    <div class="detail-member-info">
      <h6 class="detail-member-info-text">
        <span class="detail-member-info-tag">아이디 :</span>
        {{ stringId }}
      </h6>
      <h6 class="detail-member-info-text">
        <span class="detail-member-info-tag">이&nbsp;&nbsp;&nbsp;&nbsp;름 :</span>
        {{ name }}
      </h6>
      <h6 class="detail-member-info-text">
        <span class="detail-member-info-tag">닉네임 :</span>
        {{ nick }}
      </h6>
      <h6 class="detail-member-info-text">
        <span class="detail-member-info-tag">이메일 :</span>
        {{ email }}
      </h6>

      <div class="detail-member-info-input">
        <q-input
          standout="bg-teal text-white"
          v-model="newNick"
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
          type="newEmail"
          v-model="newEmail"
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
        <q-btn flat label="수정하기" color="primary" @click="changeMemberInfo(pw, newNick, newEmail)" />
        <q-btn flat label="취소" color="primary" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
  <q-dialog v-model="changeImageModal">
    <q-card>
      <q-card-section>
        <div class="text-h6">프로필 사진 변경</div>
      </q-card-section>
      <div class="editor-detail">프로필 사진은 10MB이내여야 하며, 이미지 파일(png, jpg, jpeg)만 가능합니다.</div>

      <q-card-section class="q-pt-none">
        <ProfileImageUploader @changeImageEmit="changeProfileImage" />
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="취소" color="primary" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
.main-profile {
  width: 80%;
  margin: 30px auto;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.image-container {
  margin: auto;
  text-align: center;
}
.profile-img {
  display: inline-block;
  margin: 1rem 0;
}
.profile-detail {
  display: flex;
}
.profile-detail-edit {
  justify-content: center;
  align-self: center;
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

.detail-member-info-text {
  font-size: 1.2rem;
  margin: 1rem 0.5rem;
}
.detail-member-info-tag {
  font-size: 0.8rem;
  color: #034c8c;
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
