<script setup>
import Labels from '../atoms/CardLabel.vue';
import Like from '../atoms/Like.vue';
import CustomButton from '../atoms/CustomButton.vue';
import { ref } from 'vue'
import { useCounterStore } from "../../stores/chat-status.js"

const store = useCounterStore();
const { requestDm } = store

const coach = ref("고코치")
const chatLabel = ref(coach.value + "님께 문의해보세요.")
</script>

<template>
  <q-card class="my-card">
    <q-item>
      <q-item-section>
        <Labels label="궁금한 내용이 있으신가요?" class="card-padding"></Labels>
        <Labels :label="chatLabel" class="card-padding"></Labels>
        <q-item-section>
          <div class="buttons card-padding" >
            <Like></Like>
            <CustomButton style="width: 100px; height: 20px" @click="requestDm">채팅하기</CustomButton>
          </div>
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

.buttons {
  display: flex;
  align-items: center;
}

.card-padding {
  padding-top: 2vh;
}
</style>