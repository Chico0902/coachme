<script setup>
const props = defineProps({
  coachingCategory : {
    type: Object
  }
})

</script>

<template>
  <q-breadcrumbs class="text-brown">
    <template v-slot:separator>
      <q-icon size="1.5em" name="chevron_right" color="primary"></q-icon>
    </template>

    <q-breadcrumbs-el v-for="menu in props.coachingCategory" :key="menu" :label="menu"></q-breadcrumbs-el>
  </q-breadcrumbs>
</template>

<style scoped></style>