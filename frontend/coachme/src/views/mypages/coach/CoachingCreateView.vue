<script setup>
import CustomInput from '@/components/atoms/CustomInput.vue'
import QuillEditor from '@/components/molecules/QuillEditor.vue'
import { ref, computed } from 'vue'
import { CreateCoachingRequestDto } from '@/utils/api/dto/coach-dto'
import { postNewCoaching } from '@/utils/api/coach-api'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import Swal from 'sweetalert2'
import router from '@/router'

/**
 * VARIABLES
 */

// 대분류와 소분류 데이터 정의
const mainCategories = ref(['Life', 'Creations', 'Sports', 'Develop', 'Health', 'ETC'])
const subCategories = computed(() => {
  switch (selectedCategory.value) {
    case 'Life':
      return ['House', 'Furniture', 'Lifestyle', 'Design']
    case 'Creations':
      return ['Cooking', 'Knitting', 'Art', 'Beauty']
    case 'Sports':
      return ['Soccer', 'Basketball', 'Tennis', 'Golf']
    case 'Develop':
      return ['Frontend', 'Backend', 'Database', 'Devops']
    case 'Health':
      return ['Yoga', 'Weight', 'Running', 'Crossfit']
    case 'ETC':
      return ['ETC']
    default:
      return ''
  }
})

// 선택된 대분류와 소분류 상태
const selectedCategory = ref(null)
const selectedSubCategory = ref(null)

// 기타 변수
const coachingName = ref('')
const coachingSummary = ref('')
const contentHTML = ref('')

/**
 * METHODS
 */
const createNewCoaching = () => {
  if (
    selectedCategory.value === null ||
    selectedSubCategory.value === null ||
    coachingName.value === '' ||
    coachingSummary.value === '' ||
    contentHTML.value === ''
  ) {
    Swal.fire({
      title: '생성 실패',
      text: '작성하지 않은 항목이 있습니다.',
      icon: 'info'
    })
    return
  }
  const longId = decodeToken(getAccessToken()).longId
  const dto = new CreateCoachingRequestDto(
    selectedCategory.value,
    selectedSubCategory.value,
    coachingName.value,
    coachingSummary.value,
    contentHTML.value
  )
  postNewCoaching(
    longId,
    dto,
    () => {
      Swal.fire({
        icon: 'success',
        title: '생성 완료!'
      })
      router.push({ name: 'Desktop-5-3' })
    },
    (error) => {
      Swal.fire({
        icon: 'fail',
        title: '등록 실패!'
      })
    }
  )
}
</script>
<template>
  <div class="create-coaching-outside">
    <div class="main-title">
      코칭 생성하기
      <div class="editor-detail">코칭의 카테고리를 선택하고, 원하는 코칭을 생성하세요!</div>
    </div>
    <div class="create-coaching-box">
      <div class="category-box">
        <div class="big-category">
          <label class="title" for="category">대분류</label>
          <q-select
            filled
            v-model="selectedCategory"
            :options="mainCategories"
            label="생성하실 코칭의 대분류를 선택하세요."
          />
        </div>
        <div class="small-category">
          <label class="title" for="subCategory">소분류</label>
          <q-select
            filled
            v-model="selectedSubCategory"
            :options="subCategories"
            label="생성하실 코칭의 소분류를 선택하세요."
          />
        </div>
      </div>
      <div class="title">코칭 이름</div>
      <div class="input">
        <CustomInput placeholder='이준학의 "야 너도 농구 할 수 있어".' v-model="coachingName" />
      </div>
      <div class="title">코칭 한줄 요약</div>
      <div class="input">
        <CustomInput placeholder="전혀 성장하지 않은 당신을 위한 맞춤 농구교실." v-model="coachingSummary" />
      </div>
      <div class="title">코칭 상세설명</div>

      <div class="quill-input" style="margin-bottom: 0.5rem">
        <div class="editor-detail" style="margin-bottom: 1rem">
          코미와 방문자들이 확인하는 양식입니다. 본인의 멋진 코칭을 마음껏 꾸며주세요!
        </div>
        <QuillEditor theme="snow" v-model:content="contentHTML" contentType="html" style="min-height: 20vh" />
      </div>

      <div>
        <div class="menu SMN_effect-42">
          <a href="#" @click.prevent="createNewCoaching">
            <span data-hover="코칭생성" style="font-size: 1.2rem">코칭생성</span>
          </a>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
.title {
  font-size: 1.1rem;
}
.main-title {
  display: inline-block;
  font-size: 2rem;
  margin-top: 1.5rem;
  margin-left: 3rem;
  margin-bottom: 1rem;
}
.editor-detail {
  margin-bottom: 1rem;
  margin-left: 0.5rem;
  color: #034c8c;
  font-size: 0.9rem;
  margin-bottom: 0.5rem;
}
.create-coaching-box {
  width: 80%;
  margin: 5vh auto;
}
.category-box {
  display: flex;
  justify-content: space-between;
  margin-bottom: 2rem;
}

.big-category {
  width: 90%;
  margin-right: 2rem;
}
.small-category {
  width: 90%;
  margin-right: 2rem;
}
.quill-input {
  min-height: 25vh;
  margin-bottom: 2rem;
}
.input {
  margin-bottom: 1rem;
}

.menu {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 2rem;
}
.menu a {
  color: #034c8c;
  font-size: 10pt;
  font-weight: 400;
  padding: 15px 25px;
  /**/
  position: relative;
  display: inline-blockk;
  text-decoration: none;
  text-transform: uppercase;
}
.SMN_effect-42 a {
  position: relative;
}

.SMN_effect-42 a:before {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 1.5rem;
  background-color: #034c8c;
  transform-origin: 100% 50%;
  transform: scale(0, 1);
  top: 0;
  left: 0;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1);
}

.SMN_effect-42 a:after {
  content: '';
  position: absolute;
  width: 100%;
  height: 2px;
  background-color: rgb(0, 0, 0);
  left: 0;
  bottom: 0;
  transform-origin: 0% 50%;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0.2s;
}

.SMN_effect-42 a span {
  position: relative;
}

.SMN_effect-42 a span:after {
  content: attr(data-hover);
  position: absolute;
  left: 0;
  width: 100%;
  height: 100%;
  transition: opacity 0.2s cubic-bezier(0.33, 0.91, 0.42, 1) 0s;
}

.SMN_effect-42 a:hover:before {
  transform: scale(1);
  transform-origin: 0 50%;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0.2s;
}

.SMN_effect-42 a:hover:after {
  transform: scale(0, 1);
  transform-origin: 100% 50%;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0s;
}

.SMN_effect-42 a:hover span:after {
  color: #ffffff;
  transition: color 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0.2s;
}
</style>
