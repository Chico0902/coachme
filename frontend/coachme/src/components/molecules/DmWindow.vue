<!-- dm 1:1 컴포넌트 
필요한 내용 : dm 내역, 자신의 아이디, 코치(상대)의 아이디
chatList : 보낸 사람 id, [채팅 내역]
myId : 자신의 아이디
otherId : 코치(상대)의 아이디
-->

<script setup>
const props = defineProps({
  chatList: {
    type: Object,
    // 주의 chatList의 chat 부분은 []로 쌓인 배열이어야 함
  },
  myId: {
    type: String
  },
  otherId: {
    type: String
  }
})

</script>

<template>
  <div class="q-pa-md row justify-center chat-box">
    <div style="width: 100%; max-width: 400px">
      <div v-for="list in props.chatList" :key="list">
        <!-- 자신이 보낸 부분-->
        <div v-if="props.myId == list.id">
          <q-chat-message :name="list.id" :text="list.chat" sent bg-color="amber-7"></q-chat-message>
        </div>

        <!-- 상대가 보낸 부분-->
        <div v-else>
          <q-chat-message :name="list.id" :text="list.chat" size="6" text-color="white" bg-color="primary">
          </q-chat-message>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.chat-box {

  background-color: #FFEEC1;
  max-width: 380px;
  max-height: 500px;
}
</style>