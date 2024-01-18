<!-- 코치 카드 컴포넌트
필요한 정보 : 카드 라벨, 카드 캡션, 프로필사진 주소, 찜콩버튼 함수
카드 라벨 : 카드에 있는 라벨으로 짙은 색 글자. 문자열. 기본값 없음
카드 캡션 : 카드에 있는 문자로 옅은 색 글자, 설명과 같은 기능. 문자열. 기본값 없음
프로필 사진 주소 : 카드에 있는 코치의 프로필 사진. 문자열. 기본값 없음
찜콩버튼 함수 : 찜콩버튼 클릭시 발생할 함수. function. 기본값 없음
-->

<script setup>
import profile from '../atoms/ProfileImage.vue'
import labels from '../atoms/CardLabel.vue'
import like from '../atoms/Like.vue'
const props = defineProps({
  label : {
    type : String,
    default : ""
  },
  caption : {
    type : String,
    default : ""
  },
  img : {
    type : String,
    default : ""
  },
  liked : {
    type : Function,
    default : () => { }
  }
});
</script>

<template>
  <div class="q-pa-md row items-start q-gutter-md" style="max-width: 300px; min-width: 300px">
    <q-card class="my-card">
      <q-item>
        <q-item-section avatar>
          <profile :img="`${props.img}`"></profile>
        </q-item-section>
        <q-item-section>
          <q-space></q-space>
        </q-item-section>
        <q-item-section>
          <like :clicked="liked"></like>
        </q-item-section>
      </q-item>
      <q-separator></q-separator>
      <q-item>
        <q-item-section>
          <labels :label="`${props.label}`"></labels>
          <labels caption :label="`${props.caption}`"></labels>
        </q-item-section>
      </q-item>
    </q-card>
  </div>
</template>

<style scoped>
.my-card {
  width: 100%;
  max-width: 250px
}
</style>

