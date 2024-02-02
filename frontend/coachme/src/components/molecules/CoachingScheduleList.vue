<!-- 코칭 상세 정보 페이지의 날짜별 스케줄 리스트 컴포넌트
필요한 정보 : 클릭한 날짜, 클릭한 날짜에 배정된 코칭 시간표
date : 클릭한 날짜. 문자열.
timeTable : 클릭한 날짜에 배정되어있는 코칭 시간표. Object
-->

<script setup>

const props = defineProps({
  date: {
    type: String
  },
  timeTable: {
    type: Object
  },
})


</script>

<template>
  <q-card class="my-card" style="margin-left: 2vw; width: 20vw; max-height: 52vh;">
    <!-- 날짜 -->
    <q-card-section style="font-size: 22px;">
      {{ date }}
    </q-card-section>

    <q-card-section>
      <q-item-label style="font-size: 18px;">시간표</q-item-label>
    </q-card-section>

    <q-separator spaced inset></q-separator>

    <!-- 시간표(배정된 시간 있음) -->
    <template v-if="props.timeTable.length > 0">
      <div v-for="(time,index) in props.timeTable" :key="time">
        <q-list>
          <q-item>
            <q-item-section>
              <q-item-label>{{ time }}</q-item-label>
            </q-item-section>

            <q-item-section side top>
              <q-btn style="background-color: #FCBF17; color: black;">신청하기</q-btn>
            </q-item-section>
          </q-item>

          <q-separator v-if="index != timeTable.length - 1" spaced inset></q-separator>
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