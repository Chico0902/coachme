<script setup>
import profile from '@/components/atoms/ProfileImage.vue'
import { computed, onBeforeMount, ref, watch } from 'vue'
import { useMemberStore } from '@/stores/member'
import { getLiveCoachingCalendar } from '@/utils/api/coach-api'
import { getLiveCoachingCoame } from '@/utils/api/coaching-api'
import { getStartLiveCoaching } from '@/utils/api/livecoaching-api'
import router from '@/router'
import Swal from 'sweetalert2'

/**
 * VARIABLES
 */

// in pinia
const memberStore = useMemberStore()
const { longId } = memberStore

// 로컬변수
const students = ref([])
const liveCoachings = ref([])
const allLiveCoachings = ref([])
const events = ref([])

// 현재시간 계산
const nowObject = new Date()
const now = computed(() => {
  const nowMonth = nowObject.getMonth() + 1 < 10 ? `0${nowObject.getMonth() + 1}` : nowObject.getMonth() + 1
  const nowDate = nowObject.getDate() < 10 ? `0${nowObject.getDate()}` : nowObject.getDate()
  return `${nowObject.getFullYear()}-${nowMonth}-${nowDate}`
})
const date = ref(now.value)

/**
 * METHODS
 */

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
      allLiveCoachings.value[dateKey] = [
        {
          id: element.id,
          className: element.className,
          time: _time,
          isStart: date.getTime() < nowObject.getTime() ? true : false
        }
      ]
    else
      allLiveCoachings.value[dateKey].push({
        id: element.id,
        className: element.className,
        time: _time,
        isStart: date.getTime() < nowObject.getTime() ? true : false
      })
    // 오늘 라이브코칭 있는지 확인
    const today = new Date()
    if (
      date.getFullYear() === today.getFullYear() &&
      date.getMonth() === today.getMonth() &&
      date.getDate() === today.getDate()
    )
      liveCoachings.value.push({
        id: element.id,
        className: element.className,
        time: _time,
        isStart: date.getTime() < nowObject.getTime() ? true : false
      })
    // 이벤트 배열 생성
    events.value.push(dateKey)
  })
}

function getLiveCoachingStudent(coachingId) {
  getLiveCoachingCoame(
    coachingId,
    (success) => {
      students.value = success.data.list
    },
    (fail) => console.error(fail)
  )
}

onBeforeMount(() => {
  getLiveCoachingCalendar(
    longId,
    (success) => {
      parseLiveCoachingData(success.data.list)
    },
    (fail) => console.error(fail)
  )
})
watch(
  () => date.value,
  () => {
    const dateKey = getDateKey(date.value)
    liveCoachings.value = allLiveCoachings.value[dateKey]
    students.value = []
  }
)
function getDateKey(date) {
  return date.substring(0, 10).replace(/-/g, '/')
}
const startLiveCoaching = (liveCoachingId) => {
  Swal.fire({
    title: '라이브 코칭 시작',
    text: '라이브 코칭을 시작하시겠습니까?',
    icon: 'question',
    showCancelButton: true,
    confirmButtonText: '예',
    cancelButtonText: '아니오'
  }).then((result) => {
    if (result.isConfirmed) {
      getStartLiveCoaching(
        liveCoachingId,
        (success) => {
          Swal.fire('성공', '화상회의 페이지로 이동합니다.', 'success').then(() => {
            router.push(`/live/${liveCoachingId}/${longId}`)
          })
        },
        (fail) => {
          Swal.fire('에러', '라이브 코칭 시작에 실패했습니다.', 'error')
        }
      )
    }
  })
}
</script>
<template>
  <div class="outside">
    <div class="main-title">
      라이브 관리
      <div class="editor-detail">
        달력에서 날짜를 선택하면 생성된 라이브 코칭과 현재까지 신청한 수강생 목록을 확인할 수 있습니다.
      </div>
    </div>
    <div class="coach-main">
      <div class="main-container">
        <div class="calendar">
          <q-date v-model="date" :events="events" class="custom-q-date" mask="YYYY-MM-DD" />
        </div>
        <div class="memo">
          <template v-if="liveCoachings == undefined || liveCoachings.length === 0">
            <p style="font-size: 0.8rem; color: #5f5f5f">등록된 라이브 코칭이 없습니다.</p>
          </template>
          <template v-else>
            <template v-for="liveCoaching in liveCoachings" :key="liveCoaching.id">
              <div @click="getLiveCoachingStudent(liveCoaching.id)">
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
                      v-if="liveCoaching.isStart"
                      icon="meeting_room"
                      flat
                      color="black"
                      @click="startLiveCoaching(liveCoaching.id)"
                    ></q-btn>
                  </template>
                </q-field>
              </div>
            </template>
          </template>
          <div class="menu SMN_effect-42">
            <RouterLink :to="{ name: 'Desktop-5-6' }"><span data-hover="등록하기">등록하기</span></RouterLink>
          </div>
        </div>
        <div class="coaching-detail">
          <p class="coaching-detail-title">수강생 목록</p>
          <template v-if="students == undefined || students.length === 0">
            <p style="font-size: 0.8rem; color: #5f5f5f">검색된 수강생이 없습니다.</p>
          </template>
          <template v-else>
            <template v-for="student in students" :key="student.id">
              <q-item clickable v-ripple>
                <q-item-section avatar>
                  <q-item>
                    <profile :img="student.imageUrl"></profile>
                  </q-item>
                </q-item-section>
                <q-item-section>
                  <q-item-label>{{ student.name }}</q-item-label>
                  <q-item-label caption>{{ student.profileText }}</q-item-label>
                </q-item-section>
              </q-item>
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
.coaching-detail {
  min-width: 15%;
  max-width: 30%;
}
.coaching-detail-title {
  width: 80%;
  margin: 2rem auto;
  text-align: center;
  font-size: 1.1rem;
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
