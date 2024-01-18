<script setup>
import { ref } from 'vue'

const profileText = ref('')
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
      <q-btn round dense flat icon="send" @click="$emit('changeTextEmit')" />
    </template>
  </q-input>
</template>
