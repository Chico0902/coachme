<!-- 코칭 채팅 컴포넌트 
필요한 내용 : dm 내역, 자신의 아이디
directMessage : 보낸 사람 id, [채팅 내역]
myId : 자신의 아이디
-->

<script setup>
import InputForm from './InputForm.vue';

const props = defineProps({
  directMessage: {
    type: Object,
    // 주의 directMessage chat 부분은 []로 쌓인 배열이어야 함
  },
  myId: { // 내 id
    type: String
  }, 
})

const background = "white"

</script>

<template>
  <div class="q-pa-md row justify-center window-box">
    <div class="q-pa-md row justify-center chat-box">
      <div style="width: 100%;">
        <div class="title">Live Chat</div>
        <div v-for="list in props.directMessage" :key="list">
          <!-- 자신이 보낸 부분-->
          <div v-if="props.myId == list.id">
            <q-chat-message :name="list.id" :text="list.chat" sent bg-color="amber-7"></q-chat-message>
          </div>

          <!-- 나 외에 다른 사람이 보낸 부분-->
          <div v-else>
            <q-chat-message :name="list.id" :text="list.chat" size="6" text-color="white" bg-color="primary">
            </q-chat-message>
          </div>
        </div>
      </div>
    </div>
    <div class="input">
      <InputForm :background="background" bg-color="white"></InputForm>
    </div>
  </div>
</template>

<style scoped>

.window-box {
  height: 70vh;
  width: 95%;
  margin: auto;
}

.title {
  text-align: center;
  margin-bottom: 1vh;

}

.chat-box {
  width: 100%;
  height: calc(100% - 60px);
  background-color: white;
  overflow-y: auto;
}

.material-symbols-outlined {
  font-size: 25px;
}
.input {
  max-width: 400px;
}
</style>