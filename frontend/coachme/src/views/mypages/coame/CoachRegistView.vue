<script setup>
import CustomButton from '@/components/atoms/CustomButton.vue'
import QuillEditor from '@/components/molecules/QuillEditor.vue'
import { postRequestElevation } from '@/utils/api/member-api'
import { ElevationRequestDto } from '@/utils/api/dto/member-dto'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'
import { ref, computed } from 'vue'
import { decodeToken } from '@/utils/functions/auth'

/**
 * VARIABLES
 */

const color = '#fcbf17'
const label = '등록하기'
const textcolor = 'black'
const authStore = useAuthStore()
const { accessToken } = storeToRefs(authStore)
const contentHTML = ref('')
const longId = computed(() => {
  return decodeToken(accessToken.value).longId
})

/**
 * TODO 이미지/영상 파일 업로드 시 해당 업로드파일 저장하는 로직도 추가
 */
const regist = () => {
  const dto = new ElevationRequestDto(longId.value, contentHTML.value)
  postRequestElevation(
    accessToken.value,
    dto,
    (success) => {
      console.log(success)
      alert('등록 완료!')
      window.location.reload()
    },
    (fail) => {
      console.log(fail)
      alert('등록 실패!')
    }
  )
}
</script>
<template>
  <div class="main-font-container">
    <div class="main-title">코치 등록</div>
    <div class="title-detail">코치 정보를 등록하고 나만의 코칭을 시작하세요!</div>
  </div>
  <div class="editor-container">
    <div class="editor">
      <div class="editor-detail">
        아래 양식에 본인의 포트폴리오를 작성해서 제출하세요. 관리자의 승인 후 코칭 활동이 시작됩니다.
      </div>
      <QuillEditor theme="snow" v-model:content="contentHTML" contentType="html" />
    </div>
  </div>
  <div class="btn-container">
    <CustomButton
      style="font-size: 1.1rem; padding: 0.5rem 1rem"
      :label="label"
      :background="color"
      :color="textcolor"
      @click="regist"
    ></CustomButton>
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
