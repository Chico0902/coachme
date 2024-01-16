<script setup>
import axios from 'axios'
import { ref, onBeforeMount } from 'vue'
const FILESERVER_IP = '70.12.246.246'
const FILESERVER_PORT = 1235
const upload = async () => {
  const files = document.getElementById('minuteFile').files

  // exception : 파일 없을때
  if (files.length === 0) {
    alert('파일을 선택해주세요.')
    return
  }

  // 파일 업로드
  await axios
    .post(`http://${FILESERVER_IP}:${FILESERVER_PORT}/uploads/minutes`, files)
    .then((success) => console.log(success))
    .then(() => alert('파일 업로드 완료'))
    .then(() => window.location.reload())
    .catch((fail) => console.log(fail))
}
const minutes = ref([])

onBeforeMount(async () => {
  console.log('axios')

  // 파일 서버 응답여부 확인
  axios
    .get(`http://${FILESERVER_IP}:${FILESERVER_PORT}/`)
    .then((success) => console.log(success))
    .catch(() => alert('파일 서버가 응답하지 않습니다. 팀장에게 문의하세요.'))

  // 파일 서버 응답확인 후 axios 보내기
  await axios
    .get(`http://${FILESERVER_IP}:${FILESERVER_PORT}/uploads/minutes`)
    .then((success) => (minutes.value = success.data.files))
    .catch((fail) => console.log(fail))
})
</script>
<template>
  <div id="minutes">
    <h1 class="mt-3 mb-3">회의록(Meeting Minutes)</h1>
    <hr />

    <!-- 회의록 목록 -->
    <h2>목록</h2>
    <hr />
    <div class="container">
      <ul class="list-group list-group-flush">
        <template v-for="minute in minutes" :key="minute">
          <a :href="`http://${FILESERVER_IP}:${FILESERVER_PORT}/uploads/minutes/${minute}`"
            ><li class="list-group-item">{{ minute }}</li></a
          >
        </template>
        <h6 v-if="minutes.length === 0">표시할 항목이 없습니다.</h6>
      </ul>
    </div>

    <!-- 회의록 추가버튼 -->
    <div class="container mt-4" style="max-width: 40vw">
      <form @submit.prevent="upload">
        <div class="input-group mb-3">
          <label class="input-group-text" for="minuteFile"><strong>등록</strong></label>
          <input type="file" class="form-control" id="minuteFile" multiple />
          <button type="submit" class="btn btn-primary">업로드</button>
        </div>
      </form>
    </div>
  </div>
</template>
<style scoped>
a {
  text-decoration: none;
}
</style>
