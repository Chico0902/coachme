<!-- 코칭 카드 컴포넌트
필요한 정보 : 카드 라벨, 카드 캡션, 영상 비율, 영상 링크, 찜콩버튼 함수
label : 카드에 있는 라벨으로 짙은 색 글자. 문자열. 기본값 없음
caption : 카드에 있는 문자로 옅은 색 글자, 설명과 같은 기능. 문자열. 기본값 없음
ratio : 카드에 포함될 영상의 비율. 숫자. 기본값 16/9
video : 카드에 포함될 영상의 주소. 문자열. 기본값 없음
liked : 찜콩버튼 클릭시 발생할 함수. function. 기본값 없음
-->

<script setup>
import labels from '../atoms/CardLabel.vue'
import { ref, computed, defineProps } from 'vue'

const props = defineProps({
  label: {
    type: String,
    default: ''
  },
  caption: {
    type: [String, Number],
    default: ''
  },
  ratio: {
    type: Number,
    default: 16 / 9
  },
  video: {
    type: String,
    default: ''
  },
  coachingId: {
    type: String
  },
  videoId: {
    type: Number
  },
  liked: {
    type: Function,
    default: () => {}
  },
  coachIndex: {
    type: Number,
    default: -1
  }
})

const caption = typeof props.caption === 'number' ? Math.round(props.caption * 10) / 10 : props.caption

const isFirstCard = computed(() => props.coachIndex === 0 || props.coachIndex === -1)
const isSecondCard = computed(() => props.coachIndex === 1)
const isThirdCard = computed(() => props.coachIndex === 2)
</script>

<template>
  <q-card class="my-card">
    <!-- 상단 섹션 -->
    <q-item>
      <!-- 영상 -->
      <q-item-section>
        <q-video :ratio="`${props.ratio}`" :src="`${props.video}`"></q-video>
      </q-item-section>
    </q-item>
    <q-separator></q-separator>
    <!-- 하단 섹션 -->
    <q-item>
      <!-- 캡션과 라벨 -->
      <q-item-section>
        <labels class="coaching-name" :label="`${props.label}`"></labels>
        <q-item-section class="star">
          <img class="star-img" src="/assets/img/star.png" alt="star" />
          <labels class="star-num" caption :label="caption"></labels>
        </q-item-section>
      </q-item-section>
      <q-item-section>
        <div class="medal-box">
          <img class="medal" v-if="isFirstCard" src="/assets/img/medal_1.png" alt="" />
          <img class="medal" v-if="isSecondCard" src="/assets/img/medal_2.png" alt="" />
          <img class="medal" v-if="isThirdCard" src="/assets/img/medal_3.png" alt="" />
        </div>
      </q-item-section>
    </q-item>
  </q-card>
</template>

<style scoped>
.my-card {
  width: 100%;
  min-width: 15vw;
}
.coaching-name {
  min-width: 150px;
}
.medal-box {
  display: flex;
  justify-content: right;
  margin-left: 40px;
}
.medal {
  width: 50px;
  height: 50px;
}
.star-img {
  display: inline-block;
  width: 15px;
  height: 15px;
  margin: 0.5rem;
  margin-left: 0;
}
.star {
  display: flex;
  flex-direction: row;
  justify-content: start;
  align-items: center;
}
.star-num {
  align-items: center;
}
</style>
