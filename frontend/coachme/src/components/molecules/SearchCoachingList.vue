<!-- 코칭 리스트 컴포넌트 
필요한 정보는 코칭 정보
coaching : 코칭 정보. object. 기본 값 없음

object에 필용한 정보 : 코칭 이름, 별점, 프로필 사진 주소
coachId : 코치의 id ->
coachingName : 코칭 이름
rating : 별점
img : 프로필 사진 주소
-->

<script setup>
import profileImage from '../atoms/ProfileImage.vue';
import labels from '../atoms/CardLabel.vue';
import buttons from '../atoms/CustomButton.vue';
import { ref, computed } from 'vue';
import { useCounterStore } from "../../stores/chat-status.js"

const store = useCounterStore();
const { requestDm } = store
// 피니아에 저장된 채팅 활성화 함수

const props = defineProps({
  coaching : {  
    type : Object,
  },
})

const currentPage = ref(1)
const cardPerPage = 3
// 현재 페이지, 페이지 당 보여줄 코칭 개수

// 현재 페이지 데이터만 가져오기
const getData = computed(() =>
  props.coaching.slice((currentPage.value - 1) * cardPerPage, (currentPage.value - 1) * cardPerPage + cardPerPage)
)

</script>

<template>
  <div>
    <q-card v-for="(coaching, index) in getData" :key="index"
      style="margin-bottom: 3vh; min-width: 50vw; min-height:15vh;" rounded>
      <q-item>
        <!-- 프로필 사진 -->
        <q-item-section horizontal avatar style="margin-left: 2vw; margin-top: 2vh; margin-right: 2vw;">
          <profileImage :img="`${coaching.img}`" size="80px"></profileImage>
        </q-item-section>

        <!-- 구분선 -->
        <q-separator vertical></q-separator>

        <!-- 오른쪽 세션. 수직으로 배치 -->
        <q-item-section vertical>
          <!-- 코칭 이름 -->
          <q-item-section>
            <labels :label="coaching.coachingName" style="font-size: 25px;"></labels>
          </q-item-section>

          <!-- 별점, 버튼 섹션. 수평으로 배치-->
          <q-item-section horizontal>
            <q-item-section class="card">
              <!-- 별점 아이콘과 별점-->
              <div class="ratingForm" style="width: fit-content;">
                <span class="material-symbols-outlined">
                  stars
                </span>
                <span class="rating">{{ coaching.rating }}</span>
                <q-section></q-section>
              </div>
              <!-- 공간 분리 -->
              <q-space></q-space>
              
              <!-- 코칭 신청과 문의하기 버튼 섹션-->
              <div>
                <buttons label="코칭 신청" style="margin-right: 1vw; background-color: #004C98; color: white;"></buttons>
                <buttons label="문의하기" style="background-color: #FCBF17;" @click="requestDm()"></buttons>
              </div>
            </q-item-section>
          </q-item-section>

        </q-item-section>
      </q-item>
    </q-card>

    <!-- 페이지네이션 -->
    <!-- 페이지의 개수 =  총 데이터 개수 / 3 -->
    <div class="q-pa-lg flex flex-center">
      <q-pagination v-model="currentPage" color="blue-10" :min="1" :max="Math.ceil(coaching.length / cardPerPage)"
        :max-pages="6" boundary-numbers></q-pagination>
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
  color: #FCBF17;
  margin-right: 0.3vw;
}

.rating {
  font-size: 25px;
}

.ratingForm {
  display: inline-flex;
  vertical-align: center;
}
</style>