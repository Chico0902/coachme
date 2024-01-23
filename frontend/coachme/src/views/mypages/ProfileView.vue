<script setup>
import CustomButton from '@/components/atoms/CustomButton.vue';
import CustomInput from '@/components/atoms/CustomInput.vue';
import ProfileImage from '@/components/atoms/ProfileImage.vue';

const color = '#fcbf17'
const label = '수정 완료'
const textcolor = 'black'
const size = '100px'
</script>
<template>
  <div>
  <div class="main-profile">
    <div class="profile-img">
      <div><ProfileImage :size="size" /></div>
      <div class="profile-img-icon">
        아이콘 2개
      </div>
    </div>
    <div class="profile-detail">
      <CustomInput type="textarea" placeholder="소개글을 입력해주세요." />
    </div>
  </div>
  <div class="detail-profile">
    <div class="detail-div">
      <div class="detail-title">
        이름
      </div>
      <div class="detail-input">
        <q-input v-model="text" :dense="dense" />
      </div>
    </div>
    <div class="detail-div">
      <div class="detail-title">
        닉네임
      </div>
      <div class="detail-input">
        <q-input v-model="text" :dense="dense" />
      </div>
    </div>
    <div class="detail-div">
      <div class="detail-title">
        이메일
      </div>
      <div class="detail-input">
        <q-input v-model="text" :dense="dense" />
      </div>
    </div>
    <div class="detail-div">
      <div class="detail-title">
        비밀번호 변경
      </div>
      <div class="detail-input">
        <q-input v-model="text" :dense="dense" />
      </div>
    </div>
    <div class="detail-div">
      <div class="detail-title">
        비밀번호 변경확인
      </div>
      <div class="detail-input">
        <q-input v-model="text" :dense="dense" />
      </div>
    </div>
  </div>
</div>
  <div class="button-div">
    <div>
        <CustomButton
          style="height: 56px"
          :label="label"
          :background="color"
          :color="textcolor"
          @click="searchData"
        ></CustomButton>
      </div>
    </div>
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
  
}

.detail-div {
  display: flex;
  justify-content: left;
  margin: 10px 0 10px 100px;
  align-items: center;
}

.profile-detail {
  display: inline-block;
  width: 60%;
  margin-top: 20px;
}
.detail-title {
  display: inline-block;
  width: 100px;
  margin: 0 30px;
}

.detail-input{
  display: inline-block;
  margin: 30px;
}

.button-div {
  display: flex;
  justify-content: center;
  margin: 50px;
}
</style>