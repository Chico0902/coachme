<script setup>
import profile from '../atoms/ProfileImage.vue'
import labels from '../atoms/CardLabel.vue'
const props = defineProps({
  label: {
    // 카드 라벨
    type: String,
    default: ''
  }, // 카드 캡션
  caption: {
    type: String,
    default: ''
  }, // 프로필 사진 주소
  img: {
    type: String,
    default: ''
  }, // 찜콩버튼 함수
  liked: {
    type: Function,
    default: () => { }
  }
})

const size = "150px"
</script>

<template>
  <q-card class="my-card">
    <q-item>
    </q-item>

    <q-item>
      <q-item-section>
        <q-space></q-space>
      </q-item-section>

      <q-item-section avatar>
        <profile :img="`${props.img}`" :size="size"></profile>
      </q-item-section>

      <q-item-section>
        <q-space></q-space>
      </q-item-section>
    </q-item>

    <q-item>
      <q-item-section>
        <labels :label="`${props.label}`" style="font-size: 20px;"></labels>
      </q-item-section>
    </q-item>
  </q-card>
</template>

<style scoped>
.my-card {
  width: 100%;
  text-align: center;
}
</style>
