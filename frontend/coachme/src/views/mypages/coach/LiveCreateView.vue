<script setup>
import { useLiveCoachingStore } from '@/stores/live-coaching'
import { useMemberStore } from '@/stores/member'
import { getMyCoaching, postCreateLiveCoaching } from '@/utils/api/coach-api'
import { storeToRefs } from 'pinia'
import { ref, computed, onBeforeMount, watch } from 'vue'

/**
 * VARIABLES
 */

// in pinia
const memberStore = useMemberStore()
const liveCoachingStore = useLiveCoachingStore()
const { liveCoachings, allLiveCoachings, events } = storeToRefs(liveCoachingStore)
const { longId } = memberStore

// for render
const coachings = ref([])
const selectedCoaching = ref(null)

const now = computed(() => {
  const nowObject = new Date()
  const nowMonth = nowObject.getMonth() + 1 < 10 ? `0${nowObject.getMonth() + 1}` : nowObject.getMonth() + 1
  const nowDate = nowObject.getDate() < 10 ? `0${nowObject.getDate()}` : nowObject.getDate()
  const nowHour = nowObject.getHours() < 10 ? `0${nowObject.getHours()}` : nowObject.getHours()
  const nowMinute = nowObject.getMinutes() < 10 ? `0${nowObject.getMinutes()}` : nowObject.getMinutes()
  return `${nowObject.getFullYear()}-${nowMonth}-${nowDate}T${nowHour}:${nowMinute}`
})

const dateTime = ref(now.value)

/**
 * METHODS
 */

function parseCoaching(list) {
  const ret = []
  list.forEach((element) => {
    ret.push({
      id: element.id,
      main: element.main,
      sub: element.sub,
      value: element.name,
      label: element.name,
      summary: element.summary
    })
  })
  return ret
}

const create = () => {
  const dto = { coachingId: selectedCoaching.value.id, date: dateTime.value }
  console.log(dto)
  postCreateLiveCoaching(
    longId,
    dto,
    (success) => {
      console.log(success)
      alert('등록 완료')
      // router.push({ name: 'Desktop-5-5' })
    },
    (error) => console.log(error)
  )
}

onBeforeMount(() => {
  getMyCoaching(
    longId,
    (success) => {
      console.log(success)
      coachings.value = parseCoaching(success.data.list)
      console.log(coachings.value)
    },
    (error) => console.log(error)
  )
})

watch(
  () => dateTime.value,
  () => {
    const dateKey = getDateKey(dateTime.value)
    liveCoachings.value = allLiveCoachings.value[dateKey]
  }
)

function getDateKey(dateTime) {
  return dateTime.substring(0, 10).replace(/-/g, '/')
}
</script>

<template>
  <div class="coaching-outside">
    <div class="coaching-title title">코칭 이름</div>
    <div class="coaching-select">
      <q-select
        class="select"
        filled
        v-model="selectedCoaching"
        :options="coachings"
        label="라이브를 생성할 코칭 선택"
      />
      <div class="list-box">
        <q-item class="shadow-2" v-if="selectedCoaching != null">
          <q-item-section>
            <q-item-label caption class="coaching-caption">설명</q-item-label>
            <q-item-label class="coaching-name">{{ selectedCoaching.summary }}</q-item-label>
          </q-item-section>

          <q-item-section side top>
            <q-item-label caption class="coaching-caption">{{ selectedCoaching.main }}</q-item-label>
            <q-item-label class="category">{{ selectedCoaching.sub }}</q-item-label>
          </q-item-section>
        </q-item>
      </div>
    </div>
    <div class="coaching-date-title">코칭일자 및 시간</div>
    <div class="coaching-date">
      <q-date v-model="dateTime" mask="YYYY-MM-DDTHH:mm" :events="events" />
      <q-time v-model="dateTime" mask="YYYY-MM-DDTHH:mm" />
      <div class="menu SMN_effect-42">
        <div class="button-container">
          <a href="#" @click.prevent="create">
            <span data-hover="생성하기">생성하기</span>
          </a>
          <RouterLink :to="{ name: 'Desktop-5-5' }">
            <span data-hover="돌아가기">돌아가기</span>
          </RouterLink>
        </div>
        <div>
          <template v-if="liveCoachings == undefined || liveCoachings.length === 0">
            <p style="font-size: 0.8rem; color: #5f5f5f">계획된 강의가 없습니다.</p>
          </template>
          <template v-else>
            <template v-for="liveCoaching in liveCoachings" :key="liveCoaching.id">
              <q-field
                color="green"
                bg-color="amber-5"
                outlined
                :label="liveCoaching.time"
                stack-label
                style="margin-bottom: 1rem"
              >
                <template v-slot:prepend>
                  <q-icon name="event" color="primary" />
                </template>
                <template v-slot:control>
                  <div class="self-center full-width no-outline" tabindex="0">{{ liveCoaching.className }}</div>
                </template>
              </q-field>
            </template>
          </template>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.button-container {
  display: flex;
  justify-content: center;
}
.coaching-select {
  display: flex;
  justify-content: space-between;
}
.select {
  width: 40%;
}
.list-box {
  width: 55%;
}
.title {
  text-align: left;
  font-size: 24px;
  font-weight: 700;
}

.coaching-outside {
  width: 80%;
  justify-content: center;
  margin: 2rem auto;
}
.coaching-title {
  width: 50%;
  max-width: 50%;
}

.coaching-title .title {
  margin-bottom: 10px;
}

select {
  padding: 8px;
  border-radius: 4px;
  border: 1px solid #ccc;
  font-size: 14px;
}

.coaching-date {
  display: flex;
  margin-bottom: 1rem;
  margin-top: 2rem;
}

.coaching-date-title {
  margin-top: 2rem;
  font-size: 18px;
  font-weight: 600;
}

.coaching-time {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.menu {
  min-width: 30%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: right;
  margin: 0 2rem;
}
.menu a {
  color: rgba(0, 0, 0, 0.8);
  font-size: 1.1rem;
  font-weight: 400;
  padding: 15px 25px;
  position: relative;
  display: inline-blockk;
  text-decoration: none;
  text-transform: uppercase;
  margin-bottom: 1rem;
}
.SMN_effect-42 a {
  position: relative;
}

.SMN_effect-42 a:before {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 1.5rem;
  background-color: #034c8c;
  transform-origin: 100% 50%;
  transform: scale(0, 1);
  top: 0;
  left: 0;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1);
}

.SMN_effect-42 a:after {
  content: '';
  position: absolute;
  width: 100%;
  height: 2px;
  background-color: #034c8c;
  left: 0;
  bottom: 0;
  transform-origin: 0% 50%;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0.2s;
}

.SMN_effect-42 a span {
  position: relative;
}

.SMN_effect-42 a span:after {
  content: attr(data-hover);
  position: absolute;
  left: 0;
  width: 100%;
  height: 100%;
  transition: opacity 0.2s cubic-bezier(0.33, 0.91, 0.42, 1) 0s;
}

.SMN_effect-42 a:hover:before {
  transform: scale(1);
  transform-origin: 0 50%;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0.2s;
}

.SMN_effect-42 a:hover:after {
  transform: scale(0, 1);
  transform-origin: 100% 50%;
  transition: transform 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0s;
}

.SMN_effect-42 a:hover span:after {
  color: #ffffff;
  transition: color 0.3s cubic-bezier(0.33, 0.91, 0.42, 1) 0.2s;
}
</style>
