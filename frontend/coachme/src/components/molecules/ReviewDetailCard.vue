<!-- 리뷰 섹션 전체 모음 카드
필요한 정보 : 리뷰 리스트, 총 별점, \리뷰수
reviews : 리뷰 목록에 보여줄 리뷰들. Object. 
ratingModel : 별점. 숫자. 정수가 아니어도 ok
reviewCount : 리뷰수. 숫자. 코치가 받은 리뷰 수
-->

<script setup>
import InputForm from './InputForm.vue';
import Reviews from './ReviewCard.vue';
import Labels from '../atoms/CardLabel.vue';
import router from '@/router'
import { ref, computed } from 'vue'


const props = defineProps({
  reviews: {
    type: Object,
  },
  ratingModel: {
    type: Number
  },
  reviewCount: {
    type: Number
  }, 
})

const emit = defineEmits(['reviewData'], ['deleteReview'], ['updateReview'])

const coachRating = computed(() => Math.round(props.ratingModel * 10) / 10);

const reviewRating = ref(5)
// 리뷰 입력 구역의 별점 -> 이 값이 리뷰에 포함됨

const updateData = (data) => {
  const reviewData = { review: data.input, rating: reviewRating.value }
  emit('reviewData', reviewData)
}
// 입력폼에서 입력받은 내용과 별점을 상위 컴포넌트로 보내는 함수

const deleteReview = (reviewId) => {
  emit('deleteReview', reviewId)
}

const updateReview = (data) => {
  emit('updateReview', data)
}

</script>

<template>
  <q-card flat class="my-card">
    <q-item style="margin-left: 0;">
      <q-item-section vertical>
        <!-- 리뷰 요약 -->
        <q-item-section class="card-margin">
          <div class="row no-wrap items-center" style="margin-top: 2vh;">
            <q-rating v-model="coachRating" size="2em" color="orange" readonly></q-rating>
            <span style="font-size: 18px; margin-left: 1.2vw;">{{ coachRating }}</span>
          </div>
          <div style="text-align: left; margin-top: 2vh; margin-bottom: 3vh; font-size: 18px;">{{ reviewCount }}개의 리뷰
          </div>
        </q-item-section>

        <q-separator></q-separator>

        <!-- 리뷰 남기는 섹션 -->
        <q-item-section class="card-margin">
          <Labels style="font-size: 24px; text-align: left; margin-top: 5vh" label="리뷰 남기기"></Labels>
        </q-item-section>
        <!-- 별점, 리뷰 입력폼 -->
        <q-item-section style="margin-top: 4vh; margin-bottom: 10vh; margin-left: 0;">
          <q-rating v-model="reviewRating" size="3em" color="orange"></q-rating>
          <InputForm style="margin-top: 3vh; width: 50vw;" @input-data="updateData"></InputForm>
        </q-item-section>

        <!-- 리뷰 목록 섹션 -->
        <q-item-section class="card-margin">
          <Labels style="font-size: 24px; text-align: left;" label="리뷰 목록"></Labels>
        </q-item-section>

        <!-- 리뷰가 보여지는 곳 -->
        <q-item-section v-if="reviews && reviews.length > 0" class="card-margin">         
          <div v-for="(review, index) in reviews" :key="review">
            <Reviews :reviews="review" @delete-review="deleteReview" @update-review="updateReview"></Reviews>
            <!-- 마지막 리뷰 이외에는 구분선-->
            <div v-if="index != reviews.length - 1"><q-separator spaced></q-separator></div>
          </div>
        </q-item-section>
        <q-item-section v-else class="card-margin">         
          <div style="font-size: 16px; margin: 5vh 0vh 5vh;">
            리뷰가 없습니다.
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