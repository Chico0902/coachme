<script setup>
import CustomButton from '@/components/atoms/CustomButton.vue'
import QuillEditor from '@/components/molecules/QuillEditor.vue'
import { postRequestElevation } from '@/utils/api/member-api'
import { ElevationRequestDto } from '@/utils/api/dto/member-dto'
import { ref, onBeforeMount } from 'vue'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import { getMyPortfolio } from '@/utils/api/coach-api'
import Swal from 'sweetalert2'

/**
 * VARIABLES
 */

const color = '#fcbf17'
const label = '등록하기'
const textcolor = 'black'
const contentHTML = ref('')
const isAlreadyRegistered = ref(true)
const longId = decodeToken(getAccessToken()).longId

const regist = () => {
  const dto = new ElevationRequestDto(longId, contentHTML.value)

  postRequestElevation(
    dto,
    (success) => {
      Swal.fire({
        icon: 'success',
        title: '등록 완료!',
        showConfirmButton: false,
        timer: 1500,
        allowOutsideClick: false
      }).then(() => {
        window.location.reload()
      })
    },
    (fail) => {
      Swal.fire({
        icon: 'error',
        title: '등록 실패!',
        showConfirmButton: true
      })
    }
  )
}

onBeforeMount(() => {
  getMyPortfolio(
    longId,
    (success) => {
      if (success.data === '') isAlreadyRegistered.value = false
      else contentHTML.value = success.data.htmlDocs
    },
    (fail) => console.error(fail)
  )
})
</script>
<template>
  <div class="main-font-container">
    <div class="main-title">코치 등록</div>
    <div class="title-detail">코치 정보를 등록하고 나만의 코칭을 시작하세요!</div>
  </div>
  <div class="editor-container">
    <div class="editor">
      <template v-if="isAlreadyRegistered">
        <div class="editor-detail">관리자의 승인을 대기중입니다.</div>
      </template>
      <template v-else>
        <div class="editor-detail">
          아래 양식에 본인의 포트폴리오를 작성해서 제출하세요. 관리자의 승인 후 코칭 활동이 시작됩니다.
        </div>
      </template>
      <QuillEditor theme="snow" v-model:content="contentHTML" contentType="html" />
    </div>
  </div>
  <div class="btn-container">
    <template v-if="isAlreadyRegistered">
      <CustomButton
        style="font-size: 1.1rem; padding: 0.5rem 1rem"
        label="수정하기"
        :background="color"
        :color="textcolor"
        @click="regist"
      ></CustomButton>
    </template>
    <template v-else>
      <CustomButton
        style="font-size: 1.1rem; padding: 0.5rem 1rem"
        :label="label"
        :background="color"
        :color="textcolor"
        @click="regist"
      ></CustomButton>
    </template>
  </div>
</template>

<style scoped>
.main-font-container {
  margin-top: 1.5rem;
  margin-left: 3rem;
  margin-bottom: 1rem;
}
.main-title {
  display: inline-block;
  font-size: 2rem;
}
.title-detail {
  font-size: 1.2rem;
}
.editor-container {
  display: flex;
  justify-content: center;
  margin-bottom: 1rem;
  height: 45vh;
}
.editor-detail {
  color: #034c8c;
  margin-bottom: 0.5rem;
}
.editor {
  width: 90%;
  height: 32vh;
  max-height: 32vh;
}
.btn-container {
  margin: auto;
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
