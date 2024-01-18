<script setup>
import TestProfileImage from '../components/TestProfileImage.vue'
import TestProfileImageUpload from '../components/TestProfileImageUpload.vue'
import TestProfileTextUpload from '../components/TestProfileTextUpload.vue'
import { validateProfileImage, validateProfileText } from '../../utils/functions/member'
import { patchProfileImage, patchProfileText } from '../../utils/api/member-api'
import { useProfileStore } from '@/stores/profile'
import { storeToRefs } from 'pinia'

// variables
const name = '고양이'
const profileStore = useProfileStore()
const { profileImage, profileText } = storeToRefs(profileStore)

// methods
const changeProfileImage = (newImage) => {
  try {
    if (validateProfileImage(newImage)) {
      const mid = 1
      patchProfileImage(
        mid,
        newImage,
        (success) => {
          alert('이미지 업로드 완료')
          profileImage.value = success.data.data.profileImageUrl
        },
        (fail) => console.log(fail)
      )
    }
  } catch (e) {
    alert(e.message)
  }
}

const changeProfileText = (newText) => {
  try {
    if (validateProfileText(newText)) {
      const mid = 1
      patchProfileText(
        mid,
        newText,
        () => {
          alert('프로필 업로드 완료')
          profileText.value = newText
        },
        (fail) => console.log(fail)
      )
    }
  } catch (e) {
    alert(e.message)
  }
}
</script>
<template>
  <div class="q-mb-lg">
    <q-card class="my-card q-ma-lg">
      <q-item>
        <q-item-section avatar>
          <q-avatar>
            <TestProfileImage />
          </q-avatar>
        </q-item-section>

        <q-item-section>
          <q-item-label>{{ name }}</q-item-label>
          <q-item-label v-if="profileText.length === 0" caption>
            등록된 프로필이 없습니다.
          </q-item-label>
          <q-item-label v-else caption>{{ profileText }}</q-item-label>
        </q-item-section>
      </q-item>
    </q-card>
    <div role="프로필 사진, 글 업로드 검증">
      <TestProfileImageUpload @changeImageEmit="changeProfileImage" />
      <TestProfileTextUpload @changeTextEmit="changeProfileText" />
    </div>
  </div>
</template>
