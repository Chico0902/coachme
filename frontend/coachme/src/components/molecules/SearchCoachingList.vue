<script setup>
import profileImage from '../atoms/ProfileImage.vue'
import labels from '../atoms/CardLabel.vue'
import buttons from '../atoms/CustomButton.vue'
import { ref, computed } from 'vue'
import { useChatStore } from '../../stores/chat-status.js'
import { useCoachingStore } from '@/stores/coaching'
import { storeToRefs } from 'pinia'

/**
 * VARIABLES
 */

// pinia 사용

// var
const chatStore = useChatStore()
const coachingStore = useCoachingStore()
const { coachings } = storeToRefs(coachingStore)

// function
const { openChatByMemberId } = chatStore

const currentPage = ref(1)
const cardPerPage = 3
// 현재 페이지, 페이지 당 보여줄 코칭 개수

// 현재 페이지 데이터만 가져오기
const getData = computed(() => {
  if (coachings != undefined && coachings.value != undefined && Array.isArray(coachings.value)) {
    return coachings.value.slice(
      (currentPage.value - 1) * cardPerPage,
      (currentPage.value - 1) * cardPerPage + cardPerPage
    )
  } else {
    return []
  }
})
</script>

<template>
  <div>
    <template v-if="getData.length > 0">
      <q-card
        v-for="(coaching, index) in getData"
        :key="index"
        style="margin-bottom: 2vh; min-width: 50vw; min-height: 15vh"
        rounded
      >
        <q-item>
          <!-- 프로필 사진 -->
          <q-item-section horizontal avatar style="margin-left: 2vw; margin-top: 0.6vh; margin-right: 2vw">
            <profileImage :img="`${coaching.profileImg}`" size="80px"></profileImage>
            <q-item-label caption style="margin: auto; margin-top: 0.5rem">{{ coaching.memberName }}</q-item-label>
          </q-item-section>

          <!-- 구분선 -->
          <q-separator vertical></q-separator>

          <!-- 오른쪽 세션. 수직으로 배치 -->
          <q-item-section vertical>
            <!-- 코칭 이름 -->
            <q-item-section>
              <labels :label="coaching.coachingName" style="font-size: 25px"></labels>
            </q-item-section>

            <!-- 별점, 버튼 섹션. 수평으로 배치-->
            <q-item-section horizontal>
              <q-item-section class="card">
                <!-- 별점 아이콘과 별점-->
                <div class="ratingForm" style="width: fit-content">
                  <q-btn flat round color="amber-7" icon="star" disable></q-btn>
                  <span class="rating"
                    >{{ coaching.avgScore !== null ? Math.round(coaching.avgScore * 10) / 10 : 0 }} ({{
                      coaching.reviewCount
                    }})</span
                  >
                </div>
                <!-- 공간 분리 -->
                <q-space></q-space>

                <!-- 코칭 신청과 문의하기 버튼 섹션-->
                <div>
                  <buttons
                    label="문의하기"
                    style="background-color: #fcbf17"
                    @click="openChatByMemberId(coachId, coaching.memberName, coaching.profileImg)"
                  ></buttons>
                  <buttons
                    label="상세보기"
                    style="margin-right: 1vw; background-color: #004c98; color: white"
                    @click="$router.push(`/search/coaching/detail/${coaching.coachingId}`)"
                  ></buttons>
                </div>
              </q-item-section>
            </q-item-section>
          </q-item-section>
        </q-item>
      </q-card>
    </template>

    <!-- 검색 결과가 없을 때 -->
    <template v-else>
      <q-item style="margin: 3vh">
        <q-item-section>
          <q-card style="min-width: 50vw; min-height: 15vh" :rounded="true" :color="cardColor">
            <q-item-section class="q-mb-md">
              <q-item-label class="text-h6" style="margin-top: 5.7vh">검색 결과가 없습니다.</q-item-label>
            </q-item-section>
          </q-card>
        </q-item-section>
      </q-item>
    </template>

    <!-- 페이지네이션 -->
    <!-- 페이지의 개수 =  총 데이터 개수 / 3 -->
    <div class="q-pa-lg flex flex-center">
      <q-pagination
        v-model="currentPage"
        color="blue-10"
        :min="1"
        :max="coachings ? Math.ceil(coachings.length / cardPerPage) : 0"
        :max-pages="6"
        boundary-numbers
      ></q-pagination>
    </div>
  </div>
</template>

<style scoped>
.card {
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  /*수직 정렬*/
  align-items: center;
  justify-content: center;
}

.rating {
  font-size: 15px;
}

.ratingForm {
  display: flex;
  vertical-align: center;
  align-items: center;
}
</style>
