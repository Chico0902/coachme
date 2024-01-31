<script setup>
const props = defineProps({
  video : {
    type: String
  }, 
})

</script>

<template>
  <q-dialog>
    <q-card style="width: 500px" class="q-px-sm q-pb-md">
      <q-card-section>
        <q-video :ratio="16 / 9" :src="props.video"></q-video>
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<style scoped></style>