<script setup>
import { getApplyLiveCoaching } from '@/utils/api/coaching-api'
import { useMemberStore } from '@/stores/member'
import Swal from 'sweetalert2'

const memberStore = useMemberStore()
const { longId } = memberStore
const props = defineProps({ timeTable: Object })

const apply = (id) => {
  getApplyLiveCoaching(
    id,
    longId,
    (response) => {
      if (response.status === 200) {
        Swal.fire({
          icon: 'success',
          title: '신청 완료'
        })
      }
    },
    (fail) => {
      console.error(fail)
      if (fail.response.status === 500 && fail.response.data.message === 'sign up duplicated') {
        Swal.fire({
          icon: 'info',
          title: '이미 신청한 라이브 강의입니다.'
        })
      } else {
        Swal.fire({
          icon: 'error',
          title: '오류 발생',
          text: '라이브 강의 신청 중 오류가 발생했습니다.'
        })
      }
    }
  )
}
</script>

<template>
  <q-card class="my-card" style="margin-left: 2vw; width: 20vw; max-height: 52vh">
    <q-card-section>
      <q-item-label style="font-size: 18px">시간표</q-item-label>
    </q-card-section>

    <q-separator spaced inset></q-separator>

    <!-- 시간표(배정된 시간 있음) -->
    <template v-if="props.timeTable != undefined && props.timeTable.length > 0">
      <div v-for="coaching in props.timeTable" :key="coaching.id">
        <q-list>
          <q-item>
            <q-item-section>
              <q-item-label>{{ coaching.time }}</q-item-label>
            </q-item-section>

            <q-item-section side top>
              <q-btn @click="apply(coaching.id)" style="background-color: #fcbf17; color: black">신청하기</q-btn>
            </q-item-section>
          </q-item>

          <q-separator spaced inset></q-separator>
        </q-list>
      </div>
    </template>

    <!-- 배정된 시간이 없을 때 -->
    <template v-else>
      <q-list>
        <q-item>
          <q-item-section>
            <q-item-label>가능한 시간이 없습니다.</q-item-label>
          </q-item-section>
        </q-item>
      </q-list>
    </template>
  </q-card>
</template>

<style scoped>
.my-card {
  overflow: scroll;
}
</style>
