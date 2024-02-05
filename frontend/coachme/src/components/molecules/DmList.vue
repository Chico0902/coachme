<!-- dm 리스트 컴포넌트
필요한 정보 : dm 리스트
dm 리스트에 필요한 정보 : 프로필 사진 링크, 이름, 마지막 dm, 마지막 dm을 보낸 시간(수정 시간)
-->
<script setup>
import profile from '../atoms/ProfileImage.vue';
import { useCounterStore } from "../../stores/chat-status";

const store = useCounterStore();

const { closeDmWindow } = store

const props = defineProps({
  dmList: {
    type: Array,
  }
})

const emit = defineEmits(['clickDm'])

const clickDm = (index) => {
  emit('clickDm', index)
}

</script>

<template>
  <q-list bordered class="rounded-borders" style="width: 100%; max-width: 400px">
    <q-item>
      <q-item-label header>주고 받은 DM</q-item-label>
      <!-- 닫기 버튼 -->
        <q-btn flat @click="closeDmWindow()" style="margin-right: -2vw; margin-left: 5vw;">
          <span class="material-symbols-outlined">
            Close
          </span>
        </q-btn>
    </q-item>
    <q-separator spaced></q-separator>
    <div v-for="(dm, index) in props.dmList" :key="dm">
      <q-item clickable v-ripple @click="clickDm(index)">
        <!-- 프로필 사진 영역 -->
        <q-item-section avatar>
          <profile :img="`${dm.img}`"></profile>
        </q-item-section>

        <!-- 주고받은 dm 영역-->
        <q-item-section>
          <!-- 이름 -->
          <q-item-label>{{ dm.name }}</q-item-label>
          <!-- 마지막 dm -->
          <q-item-label caption>{{ dm.lastDm }}</q-item-label>
          <!-- 새로운 dm이 있을 경우 표시 -->
          <q-badge rounded floating color="red"></q-badge>
        </q-item-section>
      </q-item>

      <!-- 가장 밑에 있는 dm일 경우, 구분자를 출력하지 않음 -->
      <div v-if="index != props.dmList.length - 1"><q-separator spaced></q-separator></div>
    </div>
  </q-list>
</template>

<style scoped>
.material-symbols-outlined {
  font-size: 24px;
}
</style>