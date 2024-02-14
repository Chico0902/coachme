<script setup>
import { ref } from 'vue'

// variables
const videoFile = ref('')
</script>
<template>
  <div>
    <q-file filled bottom-slots v-model="videoFile" label="코칭 영상을 업로드하세요." counter max-files="1">
      <template v-slot:append>
        <q-icon v-if="videoFile !== ''" name="close" @click.stop.prevent="videoFile = null" class="cursor-pointer" />
        <q-icon v-else name="create_new_folder" @click.stop.prevent />
      </template>
    </q-file>
  </div>
</template>
