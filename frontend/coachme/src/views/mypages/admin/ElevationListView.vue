<script setup>
import { ref, onBeforeMount } from 'vue'
import { getAllElevations, patchElevations } from '@/utils/api/admin-api'
import { AdminElevateRequestDto } from '@/utils/api/dto/admin-dto'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'

const authStore = useAuthStore()
const { accessToken } = storeToRefs(authStore)

const selected = ref([])
const filter = ref('')
const show = ref(false)
const innerPortfolio = ref('')
const columns = ref([
  { name: 'id', align: 'center', label: '회원번호', field: 'id', sortable: true },
  { name: 'name', align: 'center', label: '이름', field: 'name', sortable: true },
  { name: 'portfolio', label: '포트폴리오', field: 'portfolio' }
])

const rows = ref([])
const showPortfolio = (portfolio) => {
  innerPortfolio.value = portfolio
  show.value = true
}

// 백엔드에 선택한 목록 요청
const elevation = (array) => {
  const dto = new AdminElevateRequestDto(array)
  patchElevations(
    dto,
    () => {
      alert('권한상승 완료')
      window.location.reload()
    },
    (fail) => {
      console.error(fail)
    }
  )
}

onBeforeMount(() => {
  getAllElevations(
    (success) => {
      rows.value = success.data.list
    },
    (fail) => {
      console.error(fail)
    }
  )
})
</script>
<template>
  <div class="q-pa-md table-container">
    <q-table
      title="권한상승 요청 리스트"
      :rows="rows"
      :columns="columns"
      row-key="name"
      selection="multiple"
      v-model:selected="selected"
      :filter="filter"
      grid
      hide-header
    >
      <template v-slot:top-right>
        <q-input borderless dense debounce="300" v-model="filter" placeholder="Search">
          <template v-slot:append>
            <q-icon name="search" />
          </template>
        </q-input>
      </template>

      <template v-slot:item="props">
        <div
          class="q-pa-xs col-xs-12 col-sm-6 col-md-4 col-lg-3 grid-style-transition"
          :style="props.selected ? 'transform: scale(0.95);' : ''"
        >
          <q-card bordered flat :class="props.selected ? ($q.dark.isActive ? 'bg-grey-9' : 'bg-grey-2') : ''">
            <q-card-section>
              <q-checkbox dense v-model="props.selected" :label="props.row.name" />
            </q-card-section>
            <q-separator />
            <q-list dense>
              <q-item v-for="col in props.cols.filter((col) => col.name !== 'desc')" :key="col.name">
                <q-item-section>
                  <q-item-label>{{ col.label }}</q-item-label>
                </q-item-section>
                <q-item-section side>
                  <template v-if="col.label === '포트폴리오'">
                    <q-btn round color="primary" :size="size" :icon="'camera'" @click="showPortfolio(col.value)" />
                  </template>
                  <template v-else>
                    <q-item-label caption>{{ col.value }}</q-item-label>
                  </template>
                </q-item-section>
              </q-item>
            </q-list>
          </q-card>
        </div>
      </template>
    </q-table>
    <div class="btn-container">
      <form @submit.prevent="elevation(selected)">
        <q-btn type="submit" color="secondary" :size="size" label="권한상승"></q-btn>
      </form>
    </div>
  </div>
  <q-dialog v-model="show">
    <q-card>
      <q-card-section>
        <div class="text-h6">포트폴리오</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        <div v-html="innerPortfolio"></div>
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="OK" color="primary" v-close-popup />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<style scoped>
.grid-style-transition {
  transition:
    transform 0.28s,
    background-color 0.28s;
}
.btn-container {
  display: flex;
  justify-content: center;
}
</style>
