<script setup>
import { useProfileStore } from '@/stores/profile'

// variables
const profileStore = useProfileStore()
const profileText = profileStore.profileText
</script>
<template>
  <q-input
    v-model="profileText"
    filled
    clearable
    color="purple-12"
    label="프로필 글을 입력하세요."
    hint="프로필 글은 50자까지 입력 가능합니다."
    maxlength="50"
  >
    <template v-slot:append>
      <q-icon v-if="profileText == ''" name="draw" />
    </template>

    <template v-slot:after>
      <q-btn round dense flat icon="send" @click="$emit('changeTextEmit', profileText)" />
    </template>
  </q-input>
</template>
