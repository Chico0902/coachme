<script setup>
import ProfileImage from '../atoms/ProfileImage.vue';
import Labels from '../atoms/CardLabel.vue';
import { ref } from 'vue'

const name = ref("고코치")
const ratingModel = ref(4.3)
const review = ref(124)
const lastEdit = ref("2024. 01. 29")
const lastDate = ref("최종 수정 일시 : " + lastEdit.value) 

</script>

<template>
  <q-card flat class="my-card">
    <q-item>
      <q-item-section vertical>
        <q-item-section>
          <ProfileImage size="120px"></ProfileImage>
        </q-item-section>

        <q-item-section>
          <Labels style="font-size: 24px; padding-top: 6vh; width: fit-content;" :label="name"></Labels>
        </q-item-section>

        <q-item-section>
          <div class="row no-wrap items-center" style="padding-top: 2vh;">
            <q-rating v-model="ratingModel" size="2em" color="orange" readonly></q-rating>
            <span style="font-size: 18px; padding-left: 1.2vw;">{{ ratingModel }} ({{ review }})</span>
          </div>
        </q-item-section>

        <q-item-section>
          <Labels style="font-size: 18px; padding-top: 8vh; padding-bottom: 2vh;" :label="lastDate"></Labels>
        </q-item-section>

      </q-item-section>
    </q-item>
  </q-card>
</template>

<style scoped>
.my-card {
  width: 100%;
  max-width: max-content;
}
</style>