<script setup>
import CustomInput from '@/components/atoms/CustomInput.vue'
import QuillEditor from '@/components/molecules/QuillEditor.vue'
import { ref, computed } from 'vue'
import { CreateCoachingRequestDto } from '@/utils/api/dto/coaching-dto'
import { postNewCoaching } from '@/utils/api/coaching-api'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'
import router from '@/router'

/**
 * VARIABLES
 */

// 대분류와 소분류 데이터 정의
const mainCategories = ref(['Life', 'Creation', 'Sport', 'Develop', 'Health', 'ETC'])
const subCategories = computed(() => {
  switch (selectedCategory.value) {
    case 'Life':
      return ['House', 'Furniture', 'Lifestyle', 'Design']
    case 'Creation':
      return ['Cooking', 'Knitting', 'Art', 'Beauty']
    case 'Sport':
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
    alert('작성하지 않은 항목이 있습니다.')
    return
  }
  const token = getAccessToken()
  const longId = decodeToken(token).longId
  const dto = new CreateCoachingRequestDto(
    selectedCategory.value,
    selectedSubCategory.value,
    coachingName.value,
    coachingSummary.value,
    contentHTML.value
  )
  console.log(dto)
  postNewCoaching(
    token,
    longId,
    dto,
    () => {
      alert('등록 완료')
      router.push({ name: 'Desktop-5-3' })
    },
    (error) => {
      alert('등록 실패')
      console.log(error)
    }
  )
}
</script>
<template>
  <div class="create-coaching-outside">
    <div class="create-coaching-box">
      <div class="category-box">
        <div class="big-category">
          <label for="category">대분류</label>
          <q-select
            filled
            v-model="selectedCategory"
            :options="mainCategories"
            label="생성하실 코칭의 대분류를 선택하세요."
          />
        </div>
        <div class="small-category">
          <label for="subCategory">소분류</label>
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

      <div class="quill-input">
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
