<!-- 코치 포트폴리오의 코치 프로필 카드
필요한 정보 : 코치 이름, 코치 프로필 사진, 코치 리뷰 평점, 코치 리뷰 수, 포트폴리오 최종 수정일
coach : 코치 이름. 문자열
img : 코치 프로필 사진. 문자열. 이미지 주소
ratingModel : 코치 리뷰 평점. 숫자. 유리수여도 처리 가능
reviewCount : 코치 리뷰 수. 숫자
-->

<script setup>
import ProfileImage from '../atoms/ProfileImage.vue';
import Labels from '../atoms/CardLabel.vue';
import { computed } from 'vue'

const props = defineProps({
  coach : {  // 코치 이름
    type : String,
  },
  img : { // 프로필 사진 주소
    type : String
  },
  ratingModel : { // 평점
    type : Number
  }, 
  reviewCount : { // 리뷰 수
    type : Number
  }
})

const ratingScore = computed(() => Math.round(props.ratingModel * 10) / 10);


</script>

<template>
  <q-card flat class="my-card">
    <q-item style="padding-left: 0;">
      <q-item-section vertical>

        <!-- 프로필 사진 -->
        <q-item-section>
          <ProfileImage :img="props.img" size="120px"></ProfileImage>
        </q-item-section>

        <!-- 코치 이름 -->
        <q-item-section>
          <Labels style="font-size: 24px; margin-top: 6vh; width: fit-content;" :label="props.coach"></Labels>
        </q-item-section>

        <!-- 코치 평점 섹션 -->
        <q-item-section>
          <div class="row no-wrap items-center" style="margin-top: 2vh; margin-bottom: 2vh;">
            <q-rating v-model="ratingScore" size="2em" color="orange" readonly></q-rating>
            <span style="font-size: 18px; margin-left: 1.2vw;">{{ ratingScore || 0 }} ({{ reviewCount }})</span>
          </div>
        </q-item-section>

        <!-- 최종 수정일 -->
        <!-- <q-item-section>
          <Labels style="font-size: 18px; margin-top: 8vh; margin-bottom: 2vh;" :label="lastDate"></Labels>
        </q-item-section> -->

      </q-item-section>
    </q-item>
  </q-card>
</template>

<style scoped>
.my-card {
  width: 100%;
  max-width: max-content;
}

</style>