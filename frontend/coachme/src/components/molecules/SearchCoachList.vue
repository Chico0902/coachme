<!-- 코칭 리스트 컴포넌트 
필요한 정보는 코칭 정보
coach : 코치 정보. object. 기본 값 없음

object에 필용한 정보 : 코치id, 코치 이름, 별점, 프로필 사진 주소
coachId : 코치의 id ->
coachName : 코치 이름
rating : 별점
img : 프로필 사진 주소
-->

<script setup>
import profileImage from '../atoms/ProfileImage.vue'
import labels from '../atoms/CardLabel.vue'
import buttons from '../atoms/CustomButton.vue'
import { ref, computed } from 'vue'
import { useCounterStore } from '../../stores/chat-status.js'

const store = useCounterStore()
const { requestDm } = store
// 피니아에 저장된 채팅 활성화 함수

const props = defineProps({
  coach: {
    type: []
  }
})

const currentPage = ref(1)
const cardPerPage = 3
// 현재 페이지, 페이지 당 보여줄 코칭 개수

// 현재 페이지 데이터만 가져오기
const getData = computed(() => {
  if (Array.isArray(props.coach)) {
    return props.coach.slice((currentPage.value - 1) * cardPerPage, (currentPage.value - 1) * cardPerPage + cardPerPage)
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
                  <span class="rating">{{ coach.avgScore }} ({{ coach.reviewCount }})</span>
                </div>
                <!-- 공간 분리 -->
                <q-space></q-space>

                <!-- 문의하기 버튼 섹션-->
                <div>
                  <buttons label="문의하기" style="background-color: #fcbf17" @click="requestDm()"></buttons>
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
        :max="coach ? Math.ceil(coach.length / cardPerPage) : 0"
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
