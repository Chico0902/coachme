<script setup>
import profile from '../atoms/ProfileImage.vue'
import labels from '../atoms/CardLabel.vue'

const props = defineProps({
  name: {
    type: String,
    default: ''
  }, 
  category: {
    type: String,
    default: ''
  },
  rating: {
    type: String, 
    default: ''
  },
  desc: {
    type: String,
    default: ''
  }, 
  img: {
    type: String,
    default: ''
  }
})

</script>

<template>
  <q-item>
    <q-item-section avatar style="padding-right: 0px;">
      <profile :img="props.img" size="150px"></profile>
    </q-item-section>
  </q-item>

  <q-item>
    <q-item-section>
      <labels :label="`${props.name}`" class="text-black text-bold"></labels>
    </q-item-section>
  </q-item>

  <q-item>
    <q-item-section>
      <labels :label="`${props.category}`" style="min-width: 7vw;" class="text-black text-bold"></labels>
    </q-item-section>
    <q-item-section>
      <q-space></q-space>
    </q-item-section>
    <q-item-section>
      <labels :label="`${props.rating}`" style="min-width: 2vw;" class="text-black text-bold"></labels>
    </q-item-section>
  </q-item>

  <q-item>
    <q-item-section>
      <labels caption :label="`${props.desc}`" style="max-height: 5vh;" class="text-black"></labels>
    </q-item-section>
  </q-item>

  <q-item>
    <q-item-section>
      <q-space></q-space>
    </q-item-section>
  </q-item>
</template>

<style scoped></style>