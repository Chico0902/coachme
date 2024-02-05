<script setup>
import CustomButton from '@/components/atoms/CustomButton.vue'
import QuillEditor from '@/components/molecules/QuillEditor.vue'
import { postRequestElevation } from '@/utils/api/member-api'
import { ElevationRequestDto } from '@/utils/api/dto/member-dto'
import { useMemberStore } from '@/stores/member'
import { storeToRefs } from 'pinia'
import { ref } from 'vue'

const color = '#fcbf17'
const label = '수정하기'
const textcolor = 'black'
const memberStore = useMemberStore()
const { longId } = storeToRefs(memberStore)
const contentHTML = ref('')

/**
 * TODO 이미지/영상 파일 업로드 시 해당 업로드파일 저장하는 로직도 추가
 */
const regist = () => {
  longId.value = 1
  const dto = new ElevationRequestDto(longId.value, contentHTML.value)
  console.log(dto)
  postRequestElevation(
    dto,
    (success) => {
      console.log(success)
      alert('등록 완료!')
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
    <div class="main-title">나의 포트폴리오</div>
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
