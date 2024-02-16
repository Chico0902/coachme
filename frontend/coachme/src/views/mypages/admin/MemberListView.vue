<script setup>
import { getAllMemberInfo } from '@/utils/api/admin-api'
import { ref, onBeforeMount } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'

const authStore = useAuthStore()
const { accessToken } = storeToRefs(authStore)
const columns = [
  {
    name: 'stringId',
    required: true,
    label: '아이디',
    align: 'left',
    field: (row) => row.stringId,
    format: (val) => `${val}`,
    sortable: true
  },
  { name: 'priv', align: 'left', label: '권한', field: 'priv', sortable: true },
  { name: 'name', align: 'left', label: '이름', field: 'name', sortable: true },
  { name: 'nick', align: 'left', label: '닉네임', field: 'nick', sortable: true },
  { name: 'email', align: 'left', label: '이메일', field: 'email', sortable: true },
  { name: 'cdate', align: 'left', label: '가입일', field: 'cdate', sortable: true },
  { name: 'mdate', align: 'left', label: '수정일', field: 'mdate', sortable: true },
  { name: 'status', align: 'left', label: '상태', field: 'status', sortable: true }
]
const rows = ref([])

onBeforeMount(() => {
  getAllMemberInfo(
    (success) => {
      rows.value = success.data.data
    },
    (fail) => {
      console.error(fail)
    }
  )
})
</script>
<template>
  <div class="list-container">
    <h1>전체 회원목록</h1>
    <q-table
      class="my-sticky-header-table"
      flat
      bordered
      title="회원정보"
      dense
      :rows="rows"
      :columns="columns"
      row-key="name"
    />
  </div>
</template>

<style scoped>
h1 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  padding: 0;
  line-height: normal;
}
.list-container {
  margin-top: 2rem;
  text-align: center;
}
</style>
