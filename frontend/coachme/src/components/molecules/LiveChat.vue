<script setup></script>

<template>
  <q-page id="window" style="padding-top: 60px; padding-bottom: 60px" class="window-box" padding>
    <div v-for="chat in chats" :key="chat">
      <div v-if="chat.memberId === myLongId">
        <q-chat-message
          :avatar="chat.memberProfileUrl"
          :text="chat.message"
          sent
          bg-color="amber-5"
          :stamp="`${parseTime(chat.createDate)}`"
        ></q-chat-message>
      </div>
      <div v-else>
        <q-chat-message
          :avatar="chat.memberProfileUrl"
          :name="chat.memberName"
          :text="chat.message"
          text-color="white"
          bg-color="primary"
          :stamp="`${parseTime(chat.createDate)}`"
        ></q-chat-message>
      </div>
    </div>
    <div id="end" style="height: 55px"></div>
    <q-page-sticky expand position="bottom">
      <q-toolbar class="bg-white">
        <form @submit.prevent="sendMessage">
          <q-input bottom-slots v-model="newMessage" counter dense style="min-width: 276px">
            <template v-slot:after>
              <q-btn @click="sendMessage" round dense flat icon="send" :disabled="!connected" />
            </template>
          </q-input>
        </form>
      </q-toolbar>
    </q-page-sticky>
    <q-page-sticky expand position="top">
      <q-toolbar class="bg-amber-5 text-white">
        <span class="coach-name-container">라이브 채팅목록</span>
        <q-space />
        <q-btn dense flat icon="close" @click="$emit('chatOff')" />
      </q-toolbar>
    </q-page-sticky>
  </q-page>
</template>

<style scoped>
.window-box {
  text-align: left;
  color: black;
  font-size: 0.9rem;
}
.coach-name-container {
  color: black;
  font-size: 0.9rem;
  margin-left: 0.7rem;
  font-weight: bold;
  line-height: normal;
}
.dm-window-container {
  position: fixed;
  bottom: 150px;
  right: 10vw;
  color: #fff;
  background-color: white;
  text-align: center;
  z-index: 7000;
  max-height: 600px;
  max-width: 300px;
  overflow: scroll;
}

.material-symbols-outlined {
  font-size: 25px;
}
.input {
  min-width: 200px;
  min-height: 30px;
  max-height: 60px;
  overflow: scroll;
  display: flex;
  align-self: baseline;
}
</style>
