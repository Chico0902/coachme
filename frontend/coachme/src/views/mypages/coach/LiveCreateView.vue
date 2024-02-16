<script setup>
import { useMemberStore } from '@/stores/member'
import { getMyCoaching, postCreateLiveCoaching, getLiveCoachingCalendar } from '@/utils/api/coach-api'
import { ref, computed, onBeforeMount, watch } from 'vue'

/**
 * VARIABLES
 */

// in pinia
const memberStore = useMemberStore()
const { longId } = memberStore

// 로컬변수
const liveCoachings = ref([])
const allLiveCoachings = ref([])
const events = ref([])

// for render
const coachings = ref([])
const selectedCoaching = ref(null)

const nowObject = new Date()
const now = computed(() => {
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
  postCreateLiveCoaching(
    longId,
    dto,
    (success) => {
      Swal.fire({
        title: '생성 완료',
        text: '라이브가 생성되었습니다!',
        icon: 'success'
      }).then(() => {
        // alert('등록 완료');
        window.location.reload()
      })
    },
    (error) => console.error(error)
  )
}

// DB에서 받은시간으로 Date객체 생성
function parseDateTime(dateTime) {
  return new Date(
    dateTime.substring(0, 4),
    dateTime.substring(5, 7) - 1,
    dateTime.substring(8, 10),
    dateTime.substring(11, 13),
    dateTime.substring(14, 16)
  )
}

// DB에서 받은 데이터로 라이브코칭 data 생성
function parseLiveCoachingData(list) {
  allLiveCoachings.value = []
  liveCoachings.value = []
  events.value = []
  list.forEach((element) => {
    // 라이브코칭 배열 생성
    const date = parseDateTime(element.date)
    const dateKey = element.date.substring(0, 10).replace(/-/g, '/')
    const _time = element.date.substring(11, 16)
    if (allLiveCoachings.value[dateKey] == undefined)
      allLiveCoachings.value[dateKey] = [{ id: element.id, className: element.className, time: _time }]
    else allLiveCoachings.value[dateKey].push({ id: element.id, className: element.className, time: _time })

    // 오늘 라이브코칭 있는지 확인
    const today = new Date()
    if (
      date.getFullYear() === today.getFullYear() &&
      date.getMonth() === today.getMonth() &&
      date.getDate() === today.getDate()
    )
      liveCoachings.value.push({ id: element.id, className: element.className, time: _time })
    // 이벤트 배열 생성
    events.value.push(dateKey)
  })
}

onBeforeMount(() => {
  getMyCoaching(
    longId,
    (success) => {
      coachings.value = parseCoaching(success.data.list)
    },
    (error) => console.error(error)
  )
  getLiveCoachingCalendar(
    longId,
    (success) => {
      parseLiveCoachingData(success.data.list)
    },
    (fail) => console.error(fail)
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
  <div class="main-title">
    라이브 생성
    <div class="editor-detail">원하시는 코칭과 날짜를 선택하고, 생성 버튼을 클릭하세요.</div>
  </div>
  <div class="coaching-outside">
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
.main-title {
  display: inline-block;
  font-size: 2rem;
  margin-top: 1.5rem;
  margin-left: 3rem;
}
.editor-detail {
  margin-left: 0.5rem;
  color: #034c8c;
  font-size: 0.9rem;
}
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
  min-width: 15rem;
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
