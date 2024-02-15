<script setup>
import labels from '../atoms/CardLabel.vue'
import Swal from 'sweetalert2'
</script>

<template>
  <q-card class="my-card">
    <!-- 상단 섹션 -->
    <q-item>
      <!-- 영상 -->
      <q-item-section style="flex-direction: row">
        <q-responsive :ratio="16 / 9" style="width: 100%; height: 100%">
          <q-spinner-gears color="primary" />
        </q-responsive>
      </q-item-section>
    </q-item>
    <q-separator></q-separator>
    <!-- 하단 섹션 -->
    <q-item>
      <!-- 캡션과 라벨 -->
      <q-item-section>
        <labels class="coaching-name" label="AI가 편집하는 중입니다."></labels>
      </q-item-section>
      <!-- 대표 영상 설정 -->
    </q-item>
    <q-separator v-if="!visible"></q-separator>
    <q-item v-if="!visible">
      <q-item-section>
        <div class="buttons">
          <q-btn padding="xs" color="amber-7" icon="check" disable>
            <q-tooltip class="bg-blue">대표 영상으로 설정하기</q-tooltip>
          </q-btn>
          <q-btn padding="xs" icon="download" disable color="amber-7">
            <q-tooltip class="bg-blue">다운로드</q-tooltip>
          </q-btn>
          <q-btn padding="xs" color="blue-10" disable>
            <span class="material-symbols-outlined"> smart_toy </span>
            <q-tooltip class="bg-blue">AI에게 편집 요청하기</q-tooltip>
          </q-btn>
        </div>
      </q-item-section>
    </q-item>
  </q-card>
</template>

<style scoped>
.gear-container {
  position: absolute;
  top: 0px;
  left: 0px;
}
.gear-container-inner {
  width: 100%;
  height: 100%;
}
.my-card {
  min-width: 15vw;
  max-width: 30vw;
}

.coaching-name {
  min-width: 150px;
}

.likebtn {
  display: flex;
  justify-content: right;
}

.buttons {
  display: flex;
  align-items: center;
  justify-content: space-around;
}

.material-symbols-outlined {
  font-size: 20px;
}
</style>
