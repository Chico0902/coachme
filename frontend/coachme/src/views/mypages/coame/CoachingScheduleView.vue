<script setup>
import { computed, onBeforeMount, ref, watch } from 'vue'
import { useLiveCoachingStore } from '@/stores/live-coaching'
import { storeToRefs } from 'pinia'
import { useMemberStore } from '@/stores/member'
import { getCoameLiveCoachingCalendar } from '@/utils/api/member-api'

/**
 * VARIABLES
 */

// in pinia
const memberStore = useMemberStore()
const liveCoachingStore = useLiveCoachingStore()
const { longId } = memberStore
const { liveCoachings, allLiveCoachings, events } = storeToRefs(liveCoachingStore)

// 현재시간 계산
const nowObject = new Date()
const now = computed(() => {
  const nowMonth = nowObject.getMonth() + 1 < 10 ? `0${nowObject.getMonth() + 1}` : nowObject.getMonth() + 1
  const nowDate = nowObject.getDate() < 10 ? `0${nowObject.getDate()}` : nowObject.getDate()
  return `${nowObject.getFullYear()}-${nowMonth}-${nowDate}`
})
const date = ref(now.value)

// 로컬변수
const liveCoachings_ = ref([])
const allLiveCoachings_ = ref([])
const events_ = ref([])

/**
 * METHODS
 */

// 로컬변수 -> 피니아
watch(
  () => liveCoachings_,
  () => (liveCoachings.value = liveCoachings_.value)
)
watch(
  () => allLiveCoachings_,
  () => (allLiveCoachings.value = allLiveCoachings_.value)
)
watch(
  () => events_,
  () => (events.value = events_.value)
)

function parseDateTime(dateTime) {
  return new Date(
    dateTime.substring(0, 4),
    dateTime.substring(5, 7) - 1,
    dateTime.substring(8, 10),
    dateTime.substring(11, 13),
    dateTime.substring(14, 16)
  )
}

function parseLiveCoachingData(list) {
  allLiveCoachings_.value = []
  liveCoachings_.value = []
  events_.value = []
  list.forEach((element) => {
    // 라이브코칭 배열 생성
    const date = parseDateTime(element.date)
    const dateKey = element.date.substring(0, 10).replace(/-/g, '/')
    const _time = element.date.substring(11, 16)
    if (allLiveCoachings_.value[dateKey] == undefined)
      allLiveCoachings_.value[dateKey] = [
        {
          id: element.id,
          coachId: element.coachId,
          className: element.className,
          time: _time,
          isStart: date.getTime() < nowObject.getTime() ? true : false,
          isOpened: element.status
        }
      ]
    else
      allLiveCoachings_.value[dateKey].push({
        id: element.id,
        coachId: element.coachId,
        className: element.className,
        time: _time,
        isStart: date.getTime() < nowObject.getTime() ? true : false,
        isOpened: element.status
      })

    // 오늘 라이브코칭 있는지 확인
    const today = new Date()
    if (
      date.getFullYear() === today.getFullYear() &&
      date.getMonth() === today.getMonth() &&
      date.getDate() === today.getDate()
    )
      liveCoachings_.value.push({
        id: element.id,
        coachId: element.coachId,
        className: element.className,
        time: _time,
        isStart: date.getTime() < nowObject.getTime() ? true : false,
        isOpened: element.status
      })
    // 이벤트 배열 생성
    events_.value.push(dateKey)
  })
}

onBeforeMount(() => {
  getCoameLiveCoachingCalendar(
    longId,
    (success) => {
      parseLiveCoachingData(success.data.list)
    },
    (fail) => console.error(fail)
  )
})

function getDateKey(date) {
  if (date === null) return
  return date.substring(0, 10).replace(/-/g, '/')
}

watch(
  () => date.value,
  () => {
    const dateKey = getDateKey(date.value)
    liveCoachings_.value = allLiveCoachings_.value[dateKey]
  }
)
</script>
<template>
  <div class="outside">
    <div class="main-title">
      나의 코칭일정
      <div class="editor-detail">코치님이 라이브를 시작하면, 입장 버튼이 활성화됩니다.</div>
    </div>
    <div class="coach-main">
      <div class="main-container">
        <div class="calendar">
          <q-date v-model="date" :events="events_" class="custom-q-date" mask="YYYY-MM-DD" />
        </div>
        <div class="memo">
          <template v-if="liveCoachings_ == undefined || liveCoachings_.length === 0">
            <p style="font-size: 0.8rem; color: #5f5f5f">등록된 라이브 코칭이 없습니다.</p>
          </template>
          <template v-else>
            <template v-for="liveCoaching in liveCoachings_" :key="liveCoaching.id">
              <q-field
                :color="liveCoaching.isStart ? `amber-5` : `green`"
                :bg-color="liveCoaching.isStart ? `green` : `amber-5`"
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
                <template v-slot:append>
                  <q-btn
                    v-if="liveCoaching.isStart && liveCoaching.isOpened"
                    icon="meeting_room"
                    flat
                    color="black"
                    @click="$router.push(`/live/${liveCoaching.id}/${liveCoaching.coachId}`)"
                  ></q-btn>
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
.main-title {
  display: inline-block;
  font-size: 2rem;
  margin-top: 1.5rem;
  margin-left: 3rem;
}
.editor-detail {
  margin-bottom: 1rem;
  margin-left: 0.5rem;
  color: #034c8c;
  font-size: 0.9rem;
}
.coaching-plan-detail {
  margin: 30px 0;
}
.coaching-plan {
  margin: 30px 0;
}
.coaching-plan-box {
  display: flex;
  justify-content: center; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
}
.coame-name {
  display: inline-block;
  margin: 20px 0;
}
.coaching-img {
  margin: 20px 0;
}

.close-button:hover {
  background-color: rgb(233, 233, 233);
  border-radius: 1.5rem;
}
.coach-main {
  width: 90%;
  margin: 20px auto;
  border-radius: 1.5rem;
}
.main-container {
  display: flex;
  justify-content: space-evenly;
}
.memo {
  margin-top: 2rem;
  min-width: 100px;
  max-width: 30%;
  border-radius: 0.5rem;
  text-align: center;
}
.custom-q-date {
  display: inline-block;
  font-size: small;
  max-width: 30%;
  margin: 10px;
  padding: 5px;
}
.q-pa-md {
  padding: 1rem 0 0 0;
}
.close-button {
  position: absolute;
  top: 5px;
  right: 5px;
  background-color: transparent;
  border: none;
  font-weight: bolder;
  margin: 10px;
}

.coaching-create-box {
  display: flex;
  justify-content: right;
  height: 3rem;
  padding: 0;
}
.calendar {
  margin-top: 1rem;
}
.menu {
  display: flex;
  justify-content: center;
  align-items: center;
}
.menu a {
  color: rgba(0, 0, 0, 0.8);
  font-size: 10pt;
  font-weight: 400;
  padding: 15px 25px;
  position: relative;
  display: inline-blockk;
  text-decoration: none;
  text-transform: uppercase;
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
  background-color: 034c8c (0, 0, 0);
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
