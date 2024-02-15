<script setup>
import CustomButton from '@/components/atoms/CustomButton.vue'
import QuillEditor from '@/components/molecules/QuillEditor.vue'
import Swal from 'sweetalert2'
import { patchMyPortfolio } from '@/utils/api/coach-api'
import { changePortfolioRequsetDto } from '@/utils/api/dto/coach-dto'
import { onBeforeMount, ref } from 'vue'
import { getMyPortfolio } from '@/utils/api/coach-api'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'

/**
 * VARIABLES
 */

// for api
const token = getAccessToken()
const longId = decodeToken(token).longId

// for view
const contentHTML = ref('')
const color = '#fcbf17'
const label = '수정하기'
const textcolor = 'black'

// 코칭 수정하기
const changePortfolio = () => {
  const dto = new changePortfolioRequsetDto(longId, contentHTML.value)
  patchMyPortfolio(
    longId,
    dto,
    (success) => {
      Swal.fire({
        icon: 'success',
        title: '수정 완료'
      })
      // 기존코드
      // alert('코칭 수정완료')
    },
    (fail) => {
      Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Your work has been saved',
        showConfirmButton: false,
        timer: 1500
      })
      // alert('등록 실패!')
    }
  )
}

onBeforeMount(() => {
  getMyPortfolio(
    longId,
    (success) => {
      contentHTML.value = success.data.htmlDocs
    },
    (fail) => console.error(fail)
  )
})
</script>
<template>
  <div class="main-font-container">
    <div class="main-title">포트폴리오 관리</div>
    <div class="editor-detail">
      아래 포트폴리오는 코미나 방문자들이 확인 가능한 양식입니다. 멋진 포트폴리오로 전문성을 마음껏 어필해보세요!
    </div>
  </div>
  <div class="editor-container">
    <div class="editor">
      <QuillEditor theme="snow" v-model:content="contentHTML" contentType="html" />
    </div>
  </div>
  <div class="btn-container">
    <CustomButton
      style="font-size: 1.1rem; padding: 0.5rem 1rem"
      :label="label"
      :background="color"
      :color="textcolor"
      @click="changePortfolio"
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
