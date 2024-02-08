<!-- 영상 모달 컴포넌트 
영상을 모달로 띄움
필요한 정보 : 영상 주소
video : 영상 주소
-->

<script setup>
const props = defineProps({
  video: {
    type: String
  },
})

</script>

<template>
  <q-dialog>
    <q-card style="width: 500px" class="q-px-sm q-pb-md">

      <!-- 상단 -->
      <q-card-section class="row items-center q-pb-none">
        <div class="text-h6">코칭 미리보기</div>
        <q-space></q-space>
        <q-btn icon="close" flat round dense v-close-popup></q-btn>
      </q-card-section>

      <!-- 미리보기 -->
      <q-card-section v-if="props.video">
        <q-video :ratio="16 / 9" :src="props.video"></q-video>
      </q-card-section>

      <q-card-section v-else>
        미리보기 영상이 없습니다.
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<style scoped></style>