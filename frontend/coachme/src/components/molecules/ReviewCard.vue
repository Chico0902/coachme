<!-- 리뷰 목록 컴포넌트 
필요한 정보는 리뷰 Object
리뷰 Object에 필요한 정보 : 이름, 리뷰 작성일, 별점, 리뷰 내용
name : 리뷰 작성자 이름. 문자열
reviewDate : 리뷰 작성일. 문자열
ratingModel : 별점. 숫자. 정수 아니어도 ok
review : 리뷰 내용
-->

<script setup>
import Labels from '../atoms/CardLabel.vue';
import DeleteButton from '../materialIcon/DeleteButton.vue';
import { ref, computed } from 'vue'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'

const props = defineProps({
  reviews: {
    type: Object,
  },
})

const ratingScore = computed(() => Math.round(props.reviews.score * 10) / 10);
const comments = ref(props.reviews.comment);
const tempRating = ref(props.reviews.score)


// v-model 적용을 위한 별점 저장

const longId = decodeToken(getAccessToken()).longId

const emit = defineEmits(['deleteReview'], ['updateReview'])

const deleteReview = (reviewId) => {
  emit('deleteReview', reviewId)
}


const updateReview = (reviewId) => {
  const data = { "reviewId" : reviewId, "review" : comments, "ratingScore" : tempRating }
  emit('updateReview', data)
}

</script>

<template>
  <q-card flat class="my-card">
    <q-item style="padding-left: 0px;">
      <q-item-section vertical class="card-margin">
        <!-- 리뷰 작성자 -->
        <q-item-section class="card-margin top-section" horizontal>
          <Labels style="font-size: 24px; margin-top: 6vh; width: fit-content;" :label="props.reviews.nickName"></Labels>
        </q-item-section>

        <!-- 별점 표시 섹션 -->
        <q-item-section class="card-margin">
          <div class="row no-wrap items-center" style="margin-top: 3vh;">
            <q-rating v-model="ratingScore" size="2em" color="orange" readonly></q-rating>
            <span style="font-size: 18px; margin-left: 1.2vw;">{{ ratingScore }} </span>
          </div>
        </q-item-section>

        <!-- 리뷰 내용 -->
        <q-item-section class="card-margin">
          <Labels style="font-size: 18px; margin-top: 6vh; width: fit-content;" :label="props.reviews.comment"></Labels>
        </q-item-section>

        <!-- 수정, 삭제버튼 -->
        <q-item-section class="card-margin" v-if="longId === props.reviews.coameId">
          <div class="row no-wrap items-center justify-end" style="color : gray;">
            <q-btn flat>
              <span class="material-symbols-outlined">
                edit
              </span>
              <q-popup-edit v-model="comments" v-slot="scope">
                <q-input autofocus dense v-model="scope.value" :model-value="scope.value">
                  <template v-slot:after>
                    <q-btn flat dense color="negative" icon="cancel" @click.stop.prevent="scope.cancel"></q-btn>

                    <q-btn flat dense color="positive" icon="check_circle" @click.stop.prevent="scope.set(); updateReview(reviews.reviewId)"
                      :disable="scope.validate(scope.value) === false || scope.initialValue === scope.value"></q-btn>
                  </template>
                </q-input>
              </q-popup-edit>
            </q-btn>
            <DeleteButton style="width: fit-content;" @click="deleteReview(reviews.reviewId)"></DeleteButton>
          </div>
        </q-item-section>

      </q-item-section>
    </q-item>
  </q-card>
</template>

<style scoped>
.my-card {
  width: 100%;
}

.card-margin {
  margin-left: 0;
}

.material-symbols-outlined {
  font-size: 25px;
}
</style>