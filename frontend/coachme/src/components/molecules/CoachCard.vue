<!-- 코치 카드 컴포넌트
필요한 정보 : 카드 라벨, 카드 캡션, 프로필사진 주소, 찜콩버튼 함수
label : 카드에 있는 라벨으로 짙은 색 글자. 문자열. 기본값 없음
caption : 카드에 있는 문자로 옅은 색 글자, 설명과 같은 기능. 문자열. 기본값 없음
img : 카드에 있는 코치의 프로필 사진. 문자열. 기본값 없음
liked : 찜콩버튼 클릭시 발생할 함수. function. 기본값 없음
-->

<script setup>
import profile from '../atoms/ProfileImage.vue'
import labels from '../atoms/CardLabel.vue'
import like from '../atoms/CustomLike.vue'

const props = defineProps({
  label: {
    // 카드 라벨
    type: String,
    default: ''
  }, // 카드 캡션
  caption: {
    type: [String,Number],
    default: ''
  }, // 프로필 사진 주소
  img: {
    type: String,
    default: ''
  }, // 찜콩버튼 함수
  liked: {
    type: Function,
    default: () => {}
  }
})

const caption = typeof props.caption === 'number' ? Math.round(props.caption * 10) / 10 : props.caption;



</script>

<template>
  <q-card class="my-card">
    <!-- 상단 섹션 -->
    <q-item>
      <!-- 프로필 사진-->
      <q-item-section avatar>
        <profile :img="`${props.img}`"></profile>
      </q-item-section>
      <!-- 공간 차지 -->
      <q-item-section>
        <q-space>
        </q-space>
      </q-item-section>
    </q-item>
    <q-separator></q-separator>
    <!-- 하단 섹션 -->
    <q-item>
      <q-item-section>
        <!-- 라벨과 캡션 -->
        <labels :label="`${props.label}`"></labels>
        <labels caption :label="caption"></labels>
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
