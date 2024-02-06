<script setup>
import Labels from '../atoms/CardLabel.vue'
import CustomButton from '../atoms/CustomButton.vue'
import { ref } from 'vue'
import { useChatStore } from '../../stores/chat-status.js'

const store = useChatStore()
const { requestDm } = store
// 채팅 관련 store

const props = defineProps({
  coach: {
    // 코치 이름
    type: String
  }
})

const chatLabel = ref(props.coach + '님께 문의해보세요.')
// 코치이름에 따라 반응형으로 변경
</script>

<template>
  <q-card class="my-card">
    <q-item>
      <q-item-section>
        <!-- 설명 섹션-->
        <Labels label="이 코칭에 관심이 있으신가요?" class="card-margin" style="margin: auto"></Labels>
        <Labels :label="chatLabel" class="card-margin" style="margin: auto"></Labels>
        <!-- 코칭 신청하기 버튼과 채팅하기 버튼 -->
        <q-item-section>
          <div class="buttons card-margin">
            <CustomButton style="width: max-content; height: 20px; background-color: #fcbf17; color: black"
              >코칭 신청하기</CustomButton
            >
            <CustomButton
              style="width: 100px; height: 20px; margin-left: 0.5vw; background-color: #004c98; color: white"
              @click="requestDm()"
              >채팅하기</CustomButton
            >
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
.card-margin {
  padding-top: 2vh;
}
</style>
