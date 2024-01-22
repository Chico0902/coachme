<script setup>
const test = () => {
  console.log("test");
};
</script>

<template>
  <div class="select-coach">
    <div class="flex-container">
      <q-card class="my-card" style="width: 260px; height: 490px;">
        <q-btn flat style="width: 100%; height: 100%;">
          <span class="material-symbols-outlined">navigate_before</span>
        </q-btn>
      </q-card>

      <div class="coach-card">
        <q-card class="my-card" style="width: 260px; height: 490px;">
          <q-btn flat style="width: 100%; height: 100%;">
            check
          </q-btn>
        </q-card>
      </div>

      <q-card class="my-card" style="width: 260px; height: 490px;">
        <q-btn flat style="width: 100%; height: 100%;">
          <span class="material-symbols-outlined">navigate_next</span>
        </q-btn>
      </q-card>
    </div>
  </div>
</template>

<style scoped>
.material-symbols-outlined {
  font-size: 50px;
}

.select-coach {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}

.coach-card {
  position: absolute;
  left: 50%; /* 가운데 정렬을 위한 조정 */
  transform: translateX(-50%); /* 가운데 정렬을 위한 조정 */
  z-index: 1;
}

.flex-container {
  display: flex;
}
</style>
