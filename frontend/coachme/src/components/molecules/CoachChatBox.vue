<!-- 우측 안내창 컴포넌트 
필요한 정보 : 코치 이름
coach : 코치 이름. 문자열
-->

<script setup>
import Labels from '../atoms/CardLabel.vue';
import Like from '../atoms/CustomLike.vue'
import CustomButton from '../atoms/CustomButton.vue';
import { ref } from 'vue'
import { useCounterStore } from "../../stores/chat-status.js"

const store = useCounterStore();
const { requestDm } = store
// 채팅 관련 store

const props = defineProps({
  coach : {  // 코치 이름
    type : String,
  },
})

const chatLabel = ref(props.coach + "님께 문의해보세요.")
// 코치이름에 따라 반응형으로 변경 

</script>

<template>
  <q-card class="my-card">
    <q-item>
      <q-item-section>
        <!-- 설명 섹션-->
        <Labels label="궁금한 내용이 있으신가요?" class="card-margin"></Labels>
        <Labels :label="chatLabel" class="card-margin"></Labels>
        <!-- 침콩 버튼과 채팅하기 버튼 -->
        <q-item-section>
          <div class="buttons card-margin">
            <Like></Like>
            <CustomButton style="width: 100px; height: 20px; background-color: #FCBF17; color: black;" 
            @click="requestDm()">채팅하기</CustomButton>
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