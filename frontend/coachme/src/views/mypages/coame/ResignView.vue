<script setup>
import CustomButton from '@/components/atoms/CustomButton.vue';
</script>
<template>
  <div class="resign">
    <div class="title">
      정말 탈퇴?
    </div>
  </div>
  <div class="btn">
    <div class="yes-btn">
    <CustomButton
          style="height: 56px"
          label="네 네"
          background="rgb(219, 219, 219)"
          color="black"
          @click="openModal = true"
        ></CustomButton>
  </div>
  <div class="no-btn">
    <CustomButton
          style="height: 56px"
          label="아니오 아니오"
          background="#fcbf17"
          color="black"
          @click="openModal = true"
        ></CustomButton>
  </div>
</div>
</template>
<style scoped>
.resign {
  margin: 20vh;
}
.title {
  text-align: center;
  font-size: xx-large;
}
.btn {
  display: flex;
  justify-content: space-evenly;
}
</style>
