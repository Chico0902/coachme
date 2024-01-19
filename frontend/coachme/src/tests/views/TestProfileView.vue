<script setup>
import { onBeforeMount, ref } from 'vue'
import TestProfileImage from '../components/TestProfileImage.vue'
import TestProfileImageUpload from '../components/TestProfileImageUpload.vue'
import TestProfileTextUpload from '../components/TestProfileTextUpload.vue'
import { validateProfileImage, validateProfileText } from '../../utils/functions/member'
import { decodeToken } from '../../utils/functions/auth'
import { getProfile, patchProfileImage, patchProfileText } from '../../utils/api/member-api'
import { useProfileStore } from '@/stores/profile'
import { storeToRefs } from 'pinia'

// variables
const name = ref('고양이')
const profileStore = useProfileStore()
const token = JSON.parse(sessionStorage.getItem('auth'))
const { profileImageUrl, profileText } = storeToRefs(profileStore)

// methods
onBeforeMount(() => {
  const memberId = decodeToken(token.accessToken).id
  getProfile(
    memberId,
    (success) => {
      profileImageUrl.value = success.data.data.profileImageUrl
      profileText.value = success.data.data.profileText
    },
    (fail) => console.log(fail)
  )
})
const changeProfileImage = (newImage) => {
  try {
    if (validateProfileImage(newImage)) {
      const memberId = decodeToken(token.accessToken).id
      patchProfileImage(
        memberId,
        newImage,
        (success) => {
          alert('이미지 업로드 완료')
          profileImageUrl.value = success.data.data.profileImageUrl
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
      const memberId = decodeToken(token.accessToken).id
      patchProfileText(
        memberId,
        newText,
        () => {
          alert('프로필 변경 완료')
          profileText.value = newText
        },
        (err) => console.log(err)
      )
    }
  } catch (e) {
    alert(e.message)
  }
}
</script>
<template>
  <div class="q-mb-lg">
    <q-card class="my-card">
      <q-item>
        <q-item-section avatar>
          <q-avatar>
            <TestProfileImage :img="profileImageUrl" />
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
  </div>
  <div role="프로필 사진, 글 업로드 검증">
    <TestProfileImageUpload @changeImageEmit="changeProfileImage" />
    <TestProfileTextUpload @changeTextEmit="changeProfileText" />
  </div>
</template>
