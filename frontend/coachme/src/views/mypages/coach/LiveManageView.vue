<script setup>
import { ref } from 'vue'
const date = ref('2019/02/01')
const events = ['2019/02/01', '2019/02/05', '2019/02/06', '2019/02/09', '2019/02/23']
const isModalVisible = ref(false)
const selectedDateEvents = ref([])
const showModal = () => {
  isModalVisible.value = true
  // 여기서 날짜에 해당하는 이벤트를 가져오는 로직을 추가할 수 있습니다.
  selectedDateEvents.value = events.filter((event) => event === date.value)
}
const hideModal = () => {
  isModalVisible.value = false
}
const studentData = [
  { name: '김싸피', imageUrl: 'https://src.hidoc.co.kr/image/lib/2022/11/15/1668491763670_0.jpg' },
  { name: '홍길동', imageUrl: 'https://src.hidoc.co.kr/image/lib/2022/11/15/1668491763670_0.jpg' }
]

const selectedStudent = ref(null)

const showStudentList = (event) => {
  // Find the corresponding student data for the clicked event
  const eventData = studentData.find((student) => student.name === event)

  // Store selected student data
  selectedStudent.value = eventData

  // Display the modal
  isModalVisible.value = true
}
</script>
<template>
  <div class="outside">
    <div class="coach-main">
      <div class="calendar-div">
        <div class="calendar">
          <div class="q-pa-md">
            <div class="q-gutter-md">
              <q-date v-model="date" :events="events" class="custom-q-date" @click="showModal" />
            </div>
          </div>
          <div class="coaching-create-box">
            <div class="menu SMN_effect-42">
              <RouterLink :to="{ name: 'Desktop-5-6' }"><span data-hover="라이브생성1">라이브생성1</span></RouterLink>
            </div>
          </div>
        </div>
        <div class="memo" v-show="isModalVisible">
          <div>
            <!-- 모달 내용 -->
            <div class="memo-list" v-for="event in selectedDateEvents" :key="event" @click="showStudentList(event)">
              {{ event }}
            </div>
            <button @click="hideModal" class="close-button">X</button>
          </div>
        </div>
      </div>
      <div class="coaching-deatil-title">수강생 목록</div>
      <div class="coaching-detail">
        <div class="name-box">
          <div class="coaching-img">
            <q-avatar size="50px">
              <img src="https://src.hidoc.co.kr/image/lib/2022/11/15/1668491763670_0.jpg" />
            </q-avatar>
          </div>
          <div class="coame-name">김싸피</div>
        </div>
        <div class="name-box">
          <div class="coaching-img">
            <q-avatar size="50px">
              <img src="https://src.hidoc.co.kr/image/lib/2022/11/15/1668491763670_0.jpg" />
            </q-avatar>
          </div>
          <div class="coaching-name">홍길동</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.coaching-plan-detail {
  margin: 30px 0;
}
.coaching-plan {
  margin: 30px 0;
}
.coaching-plan-box {
  display: flex;
  justify-content: space-around; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
}
.coame-name {
  display: inline-block;
  margin: 20px 0;
}
.coaching-deatil-title {
  width: 80%;
  margin: 2rem auto;
}

.name-box {
  display: flex;
  justify-content: space-around; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  border-color: black;
  border: solid 1px;
}
.coaching-img {
  margin: 20px 0;
}

.close-button:hover {
  background-color: rgb(233, 233, 233);
  border-radius: 1.5rem;
}
.coaching-detail {
  display: flex;
  justify-content: center;
  background-color: aliceblue;
  /* border-radius: 1.5rem; */
  align-items: left;
  flex-direction: column;
  width: 80%;
  margin: auto;
}
.coach-main {
  background-color: rgb(222, 222, 222);
  /* position: relative; */
  width: 90%;
  margin: 20px auto;
  border-radius: 1.5rem;
}
.calendar-div {
  display: flex;
  justify-content: center;
  position: relative;
  margin: 0 0 0 60px;
  display: inline-block;
}
.memo {
  position: absolute;
  top: 0;
  left: 100%;
  margin: 2rem;
  background-color: rgb(250, 244, 185);
  width: 300px;
  height: 400px;
  border-radius: 1.5rem;
}
.memo-list {
  margin: 20px;
}
.custom-q-date {
  display: inline-block;
  font-size: small;
  width: 300px;
  height: 350px;
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
  margin: 1rem;
}
.menu {
  display: flex;
  align-items: center;
  margin: 2rem;
}
.menu a {
  color: rgba(0, 0, 0, 0.8);
  font-size: 10pt;
  font-weight: 400;
  padding: 15px 25px;
  /**/
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
  background-color: #6e6e6e;
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
  background-color: rgb(0, 0, 0);
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
