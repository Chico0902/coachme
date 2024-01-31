<script setup>
import MypageTopBar from '@/components/molecules/MypageTopBar.vue'
import ProfileImage from '@/components/atoms/ProfileImage.vue'
import CustomInput from '@/components/atoms/CustomInput.vue'
import QuillEditor from '@/components/molecules/QuillEditor.vue'

const TopButtonList = [
  { name: '포트폴리오', link: '/mypage/coach/manage/portfolio' },
  { name: '코칭관리', link: '/mypage/coach/manage/coaching', cssClass: 'selected-button' },
  { name: '영상관리', link: '/mypage/coach/manage/video' }
]

// 대분류와 소분류 데이터 정의
const categories = [
  { label: 'Life', subCategories: ['House', 'Furniture', 'Lifestyle', 'Design'] },
  { label: 'Creation', subCategories: ['Cooking', 'Knitting', 'Art', 'Beauty'] },
  { label: 'Sport', subCategories: ['Soccer', 'Basketball', 'Tennis', 'Golf'] },
  { label: 'Develop', subCategories: ['Frontend', 'Backend', 'Database', 'Devops'] },
  { label: 'Health', subCategories: ['Yoga', 'Weight', 'Running', 'Crossfit'] },
  { label: 'ETC', subCategories: ['ETC'] }
]

// 선택된 대분류와 소분류 상태
let selectedCategory = null
let selectedSubCategory = null

// 대분류가 변경되면 소분류를 초기화
function handleCategoryChange() {
  console.log(selectedCategory)
  selectedSubCategory = null
}

</script>
<template>
  <MypageTopBar :button-list="TopButtonList" />
  <div class="create-coaching-outside">    
    <div class="create-coaching-box">
      <div class="category-box">
        <div class="big-category">
          <label for="category">대분류</label>
          <select v-model="selectedCategory" id="category" @change="handleCategoryChange">
            <option v-for="category in categories" :key="category.label" :value="category.label">{{ category.label }}</option>
          </select>
        </div>
        <div class="small-category">
          <label for="subCategory">소분류</label>
          <select v-model="selectedSubCategory" id="subCategory">
            <option v-for="subCategory in selectedCategory ? categories.find(cat => cat.label === selectedCategory).subCategories : []" :key="subCategory" :value="subCategory">{{ subCategory }}</option>
          </select>
        </div>
      </div>
      <div class="title"> 코칭 이름
      </div>
      <div class="input">
        <CustomInput  placeholder='ex)이준학의 "야 너도 농구 할 수 있어".'  />
      </div>
      <div class="title"> 코칭 한줄 요약
      </div>
      <div class="input">
        <CustomInput placeholder="전혀 성장하지 않은 당신을 위한 맞춤 농구교실." />
      </div>
      <div class="title"> 코칭 상세설명
      </div>
      
      <div class=" quill input">
        <QuillEditor theme="snow" />
      </div>

      <div>
      <div class="menu SMN_effect-42">
        <RouterLink :to="{name:'Desktop-5-3'}"><span data-hover="코칭만들기">코칭만들기</span></RouterLink>
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
}

.big-category {
  width: 100%;
}
.small-category {
  width: 100%;
}
.input{
  margin-bottom: 2rem;
}
.quill-container{
  min-height: 25vh;
}
.quill {
  height: 100%;
}

.menu{
  display: flex;
  align-items: center;
  justify-content: right;
  margin: 0 2rem;
}
.menu a {
  color: rgba(0, 0, 0, 0.8);
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
  background-color: #6e6e6e;
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
