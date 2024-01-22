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
        <div v-if="props.myId == list.id">
          <q-chat-message :name="list.id" :text="list.chat" sent bg-color="amber-7"></q-chat-message>
        </div>
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