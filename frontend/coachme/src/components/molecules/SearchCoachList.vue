<script setup>
import profileImage from '../atoms/ProfileImage.vue'
import labels from '../atoms/CardLabel.vue'
import buttons from '../atoms/CustomButton.vue'
import { ref, computed } from 'vue'
import { useChatStore } from '../../stores/chat-status.js'
import { useCoachStore } from '@/stores/coach'
import { storeToRefs } from 'pinia'

/**
 * VARIABLES
 */

// pinia 사용

// var
const chatStore = useChatStore()
const coachStore = useCoachStore()
const { coaches } = storeToRefs(coachStore)

// function
const { openChatByMemberId } = chatStore

const currentPage = ref(1)
const cardPerPage = 3
// 현재 페이지, 페이지 당 보여줄 코칭 개수

// 현재 페이지 데이터만 가져오기
const getData = computed(() => {
  if (coaches != undefined && coaches.value != undefined && Array.isArray(coaches.value)) {
    return coaches.value.slice(
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
        v-for="(coach, index) in getData"
        :key="index"
        style="margin-bottom: 3vh; min-width: 50vw; min-height: 15vh"
        rounded
      >
        <q-item>
          <!-- 프로필 사진 -->
          <q-item-section horizontal avatar style="margin-left: 2vw; margin-top: 0.6vh; margin-right: 2vw">
            <profileImage :img="`${coach.profileImg}`" size="80px"></profileImage>
          </q-item-section>

          <!-- 구분선 -->
          <q-separator vertical></q-separator>

          <!-- 오른쪽 세션. 수직으로 배치 -->
          <q-item-section vertical>
            <!-- 코치 이름 -->
            <q-item-section>
              <labels :label="coach.memberName" style="font-size: 25px"></labels>
            </q-item-section>

            <!-- 별점, 버튼 섹션. 수평으로 배치-->
            <q-item-section horizontal>
              <q-item-section class="card">
                <!-- 별점 아이콘과 별점-->
                <div class="ratingForm" style="width: fit-content">
                  <q-btn flat round color="amber-7" icon="star" disable></q-btn>
                  <span class="rating"
                    >{{ coach.avgScore ? Math.round(coach.avgScore * 10) / 10 : 0 }} ({{ coach.reviewCount }})</span
                  >
                </div>
                <!-- 공간 분리 -->
                <q-space></q-space>

                <!-- 문의하기 버튼 섹션-->
                <div>
                  <buttons
                    label="문의하기"
                    style="background-color: #fcbf17"
                    @click="openChatByMemberId(coach.coachId, coach.memberName, coach.profileImg)"
                  ></buttons>
                  <buttons
                    label="상세보기"
                    style="margin-right: 1vw; background-color: #004c98; color: white"
                    @click="$router.push(`/search/coach/detail/${coach.coachId}`)"
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
      <q-card style="min-width: 50vw; min-height: 15vh" :rounded="true" :color="cardColor">
        <q-item-section class="q-mb-md">
          <q-item-label class="text-h6" style="margin-top: 5.7vh">검색 결과가 없습니다.</q-item-label>
        </q-item-section>
      </q-card>
    </template>

    <!-- 페이지네이션 -->
    <!-- 페이지의 개수 =  총 데이터 개수 / 3 -->
    <div class="q-pa-lg flex flex-center">
      <q-pagination
        v-model="currentPage"
        color="blue-10"
        :min="1"
        :max="coaches ? Math.ceil(coaches.length / cardPerPage) : 0"
        :max-pages="6"
        boundary-numbers
      ></q-pagination>
    </div>
  </div>
</template>

<style scoped>
.card-outside {
  width: 756px;
}
.card {
  display: flex;
  flex-wrap: wrap;
  flex-direction: row;
  /*수직 정렬*/
  align-items: center;
  justify-content: center;
}

.material-symbols-outlined {
  font-size: 38px;
  color: #fcbf17;
  margin-right: 0.3vw;
}

.rating {
  font-size: 18px;
}

.ratingForm {
  display: flex;
  vertical-align: center;
  align-items: center;
}
</style>
