<!-- 코치 포트폴리오의 코치 프로필 카드
필요한 정보 : 코치 이름, 코치 프로필 사진, 코치 리뷰 평점, 코치 리뷰 수, 포트폴리오 최종 수정일
coach : 코치 이름. 문자열
img : 코치 프로필 사진. 문자열. 이미지 주소
ratingModel : 코치 리뷰 평점. 숫자. 유리수여도 처리 가능
reviewCount : 코치 리뷰 수. 숫자
lastEditDate : 포트폴리오 최종 수정일. 문자열
-->

<script setup>
import ProfileImage from '../atoms/ProfileImage.vue'
import Labels from '../atoms/CardLabel.vue'
import CustomBreadCrumbs from '../atoms/CustomBreadCrumbs.vue'
import CustomButton from '../atoms/CustomButton.vue'
import VideoModal from './VideoModal.vue'
import { ref, computed } from 'vue'
import router from '@/router'

const props = defineProps({
  title: {
    // 코칭 제목
    type: String
  },
  breadCrumbs: {
    // 코칭 대분류, 소분류
    type: Object
  },
  previewVideoSrc: {
    // 코칭 미리보기 영상 주소
    type: String
  },
  coach: {
    // 코치 이름
    type: String
  },
  img: {
    // 프로필 사진 주소
    type: String
  },
  ratingModel: {
    // 평점
    type: Number
  },
  reviewCount: {
    // 리뷰 수
    type: Number
  }
})

const ratingScore = computed(() => Math.round(props.ratingModel * 10) / 10)
// 코치의 평점

const videoPreviews = ref(false)
// 미리보기 영상 모달 띄우기 여부

const goSearchPage = (menu, index) => {
  if (index === 0) router.push(`/search/coaching/list/${menu}/all/all`)
  else router.push(`/search/coaching/list/all/${menu}/all`)
}
</script>

<template>
  <q-card flat class="my-card">
    <q-item style="padding-left: 0">
      <q-item-section vertical>
        <!-- 코칭 대분류와 소분류-->
        <q-item-section style="margin-left: 0.5vw">
          <CustomBreadCrumbs :coaching-category="props.breadCrumbs" @goSearch="goSearchPage"></CustomBreadCrumbs>
        </q-item-section>

        <!-- 코칭 제목 -->
        <q-item-section>
          <Labels style="font-size: 24px; margin-top: 3vh; width: fit-content" :label="props.title"></Labels>
        </q-item-section>

        <!-- 코칭 평점 섹션 -->
        <q-item-section>
          <div class="row no-wrap items-center" style="margin-top: 4vh">
            <q-rating v-model="ratingScore" size="2em" color="orange" readonly></q-rating>
            <span style="font-size: 18px; margin-left: 1.2vw">{{ ratingScore }} ({{ reviewCount }})</span>
          </div>
        </q-item-section>

        <!-- 코치 프로필 사진과 코치 이름 -->
        <q-item-section>
          <div class="row no-wrap items-center" style="margin-top: 1vh">
            <ProfileImage style="margin-top: 4vh" :img="props.img" size="70px"></ProfileImage>
            <Labels
              style="font-size: 20px; margin-top: 6vh; margin-left: 2.5vw; width: fit-content"
              :label="props.coach"
            ></Labels>
          </div>
        </q-item-section>

        <!-- 코칭 미리보기 영역 -->
        <q-item-section style="margin-left: 10vw; margin-top: 2vh; margin-bottom: 2vh; width: 8rem">
          <CustomButton
            rounded
            label="미리보기"
            icon="play_circle_outline"
            style="background-color: #fcbf17"
            @click="videoPreviews = true"
          ></CustomButton>
          <VideoModal v-model="videoPreviews" :video="props.previewVideoSrc"></VideoModal>
        </q-item-section>
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
