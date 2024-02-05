<script setup></script>
<template>
  <div class="all">
    <div class="main-layout">
      <div class="mypage-outside">
        <div class="mainpage">
          <h1>Live Coaching on coach page</h1>
        </div>
      </div>
    </div>
  </div>
  <div class="footer">
    <q-toolbar class="bg-purple text-white shadow-2 rounded-borders">
      
    </q-toolbar>
    <span class="material-symbols-outlined">
      videocam
    </span>
    <span class="material-symbols-outlined">
      mic
    </span>
    <span class="material-symbols-outlined">
      group
    </span>
    <span class="material-symbols-outlined">
      meeting_room
    </span>
    <span class="material-symbols-outlined">
      draw
    </span>
    <span class="material-symbols-outlined">
      forum
    </span>
  </div>
</template>

<style scoped>
.all {
  display: flex;
  justify-content: center;
}

.main-layout {
  width: 1280px;
  min-height: 90vh;
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
}

.mypage-outside {
  display: flex;
  justify-content: space-between;
}

.mainpage {
  background-color: white;
  width: 80%;
  height: 70vh;
  margin: auto;
  margin-top: 5vh;
  margin-bottom: 5vh;
  border-radius: 1.5rem;
  overflow: scroll;
  display: flex;
  text-align: center;
  flex-direction: row;
  -ms-overflow-style: none;
}

.footer {
  height: 10vh;
  background-color: #fcbf17;
  color: #034c8c;
  text-align: center;
}

</style>