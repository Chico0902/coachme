<script setup>
import { ref } from 'vue'

// variables
const profileImageFile = ref('')
</script>
<template>
  <div>
    <q-file filled bottom-slots v-model="profileImageFile" label="프로필 사진을 업로드하세요." counter max-files="1">
      <template v-slot:append>
        <q-icon
          v-if="profileImageFile !== ''"
          name="close"
          @click.stop.prevent="profileImageFile = null"
          class="cursor-pointer"
        />
        <q-icon v-else name="create_new_folder" @click.stop.prevent />
      </template>

      <template v-slot:after>
        <q-btn round dense flat icon="send" @click="$emit('changeImageEmit', profileImageFile)" />
      </template>
    </q-file>
  </div>
</template>
