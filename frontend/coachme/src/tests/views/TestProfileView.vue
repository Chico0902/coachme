<script setup>
import { ref } from 'vue'
import TestProfileImage from '../components/TestProfileImage.vue'
import TestProfileImageUpload from '../components/TestProfileImageUpload.vue'
import TestProfileTextUpload from '../components/TestProfileTextUpload.vue'

const name = ref('고양이')
const profileText = ref('안녕하세요. 강아지입니다.')
const changeProfileImage = (profileImage) => {
  console.log(profileImage)
}
const changeProfileText = () => {}
</script>
<template>
  <div class="q-mb-lg">
    <q-card class="my-card">
      <q-item>
        <q-item-section avatar>
          <q-avatar>
            <TestProfileImage />
          </q-avatar>
        </q-item-section>

        <q-item-section>
          <q-item-label>{{ name }}</q-item-label>
          <q-item-label v-if="profileText.length === 0" caption
            >등록된 프로필이 없습니다.</q-item-label
          >
          <q-item-label v-else caption>{{ profileText }}</q-item-label>
        </q-item-section>
      </q-item>
    </q-card>
  </div>
  <div role="프로필 사진, 글 업로드 검증">
    <TestProfileImageUpload @changeImageEmit="changeProfileImage" />
    <TestProfileTextUpload @changeTextEmit="changeProfileText" />
  </div>
</template>
