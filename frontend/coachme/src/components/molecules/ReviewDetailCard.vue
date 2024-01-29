<script setup>
import InputForm from './InputForm.vue';
import { ref } from 'vue'

const ratingModel = ref(4.3)
const reviewRate = ref(5)
const review = ref(124)
</script>

<template>
  <q-card flat class="my-card">
    <q-item>
      <q-item-section vertical>
        <q-item-section>
          <div class="row no-wrap items-center" style="padding-top: 2vh;">
            <q-rating v-model="ratingModel" size="2em" color="orange" readonly></q-rating>
            <span style="font-size: 18px; padding-left: 1.2vw;">{{ ratingModel }}</span>
          </div>
          <div style="text-align: left; padding-top: 2vh; padding-bottom: 3vh; font-size: 18px;">{{ review }}개의 리뷰</div>
        </q-item-section>

        <q-separator></q-separator>

        <q-item-section style="padding-top: 4vh; padding-bottom: 10vh;">
          <q-rating v-model="reviewRate" size="3em" color="orange"></q-rating>
          <InputForm style="padding-top: 3vh; width: 50vw;"></InputForm>
        </q-item-section>

        

      </q-item-section>
    </q-item>
  </q-card>
</template>

<style scoped></style>