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
import like from '../atoms/CustomLike.vue'
const props = defineProps({
  label: {
    type: String,
    default: ''
  },
  caption: {
    type: String,
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
  liked: {
    type: Function,
    default: () => {}
  },
  visible: {
    type: Boolean,
    default: true
  }
})
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
        <labels :label="`${props.label}`"></labels>
        <labels caption :label="`${props.caption}`"></labels>
      </q-item-section>
      <!-- 공간 차지-->
      <q-item-section v-if="visible">
        <q-space></q-space>
      </q-item-section>
      <!-- 찜콩 버튼-->
      <q-item-section v-if="visible">
        <like :clicked="liked"></like>
      </q-item-section>
    </q-item>
  </q-card>
</template>

<style scoped>
.my-card {
  width: 100%;
  min-width: 15vw;
}
</style>
