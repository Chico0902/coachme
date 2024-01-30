<script setup>
import InputForm from './InputForm.vue';
import Reviews from './ReviewCard.vue';
import Labels from '../atoms/CardLabel.vue';
import { ref } from 'vue'

const props = defineProps({
  reviews : {  
    type : Object,
  },
  ratingModel : {
    type : Number
  },  
  reviewCount : {
    type : Number
  }
})

const coachRating = ref(props.ratingModel)
const reviewRating = ref()

</script>

<template>
  <q-card flat class="my-card">
    <q-item style="margin-left: 0;">
      <q-item-section vertical>
        <!-- 코치 리뷰 요약 -->
        <q-item-section class="card-margin">
          <div class="row no-wrap items-center" style="margin-top: 2vh;">
            <q-rating v-model="coachRating" size="2em" color="orange" readonly></q-rating>
            <span style="font-size: 18px; margin-left: 1.2vw;">{{ ratingModel }}</span>
          </div>
          <div style="text-align: left; margin-top: 2vh; margin-bottom: 3vh; font-size: 18px;">{{ reviewCount }}개의 리뷰</div>
        </q-item-section>

        <q-separator></q-separator>

        <!-- 리뷰 남기는 섹션 -->
        <q-item-section class="card-margin">
          <Labels style="font-size: 24px; text-align: left; margin-top: 5vh" label="리뷰 남기기"></Labels>
        </q-item-section>
        <!-- 별점, 리뷰 입력폼 -->
        <q-item-section style="margin-top: 4vh; margin-bottom: 10vh; margin-left: 0;">
          <q-rating v-model="reviewRating" size="3em" color="orange"></q-rating>
          <InputForm style="margin-top: 3vh; width: 50vw;"></InputForm>
        </q-item-section>

        <!-- 리뷰 목록 섹션 -->
        <q-item-section class="card-margin">
          <Labels style="font-size: 24px; text-align: left;" label="리뷰 목록"></Labels>
        </q-item-section>

        <!-- 리뷰가 보여지는 곳 -->
        <q-item-section class="card-margin">
          <div v-for="(review, index) in reviews" :key="review">
            <Reviews :reviews="review"></Reviews>
            <!-- 마지막 리뷰 이외에는 구분선-->
            <div v-if="index != reviews.length - 1"><q-separator spaced></q-separator></div>
          </div>
        </q-item-section>

      </q-item-section>
    </q-item>
  </q-card>
</template>

<style scoped>
.card-margin {
  margin-left: 0;
}

</style>