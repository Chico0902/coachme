<script setup>
import { ref } from 'vue'
import TestProfileImage from '../components/TestProfileImage.vue'

const profileImage = ref('')
</script>
<template>
  <div>
    <q-file
      filled
      bottom-slots
      v-model="profileImage"
      label="프로필 사진을 업로드하세요."
      counter
      max-files="1"
    >
      <template v-slot:before>
        <TestProfileImage v-if="profileImage == ''" :size="'20'" />
        <TestProfileImage v-else :img="profileImage" :size="'20'" />
      </template>

      <template v-slot:append>
        <q-icon
          v-if="profileImage !== null"
          name="close"
          @click.stop.prevent="profileImage = null"
          class="cursor-pointer"
        />
        <q-icon name="create_new_folder" @click.stop.prevent />
      </template>

      <template v-slot:after>
        <q-btn round dense flat icon="send" @click="$emit('changeImageEmit', profileImage)" />
      </template>
    </q-file>
  </div>
</template>
