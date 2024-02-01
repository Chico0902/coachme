<script>
import { ref } from 'vue'

export default {
  setup() {
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
    return {
      date,
      events,
      isModalVisible,
      selectedDateEvents,
      showModal,
      hideModal,
      value: ref(true)
    }
  }
}
</script>
<template>
  <div class="outside">
    <div class="togglebox">
      <div>
        <div class="q-pa-md q-gutter-lg">
          <div>
            <q-toggle v-model="value" color="yellow" label="코치/코미" />
          </div>
        </div>
      </div>
    </div>
    <div class="coach-main">
      <div class="calendar-div">
        <div class="calendar">
          <div class="q-pa-md">
            <div class="q-gutter-md">
              <q-date v-model="date" :events="events" class="custom-q-date" @click="showModal" />
            </div>
          </div>
        </div>
        <div class="memo" v-show="isModalVisible">
          <div>
            <!-- 모달 내용 -->
            <div class="memo-list" v-for="event in selectedDateEvents" :key="event">
              {{ event }}
            </div>
            <button @click="hideModal" class="close-button">X</button>
          </div>
        </div>
      </div>
      <div class="coaching-detail">
        <div class="coaching-name-box">
          <div class="coaching-img">
            <q-avatar size="50px">
              <img src="https://src.hidoc.co.kr/image/lib/2022/11/15/1668491763670_0.jpg" />
            </q-avatar>
          </div>
          <div class="coaching-name">강의 이름</div>
        </div>
        <div class="coaching-plan-box">
          <div class="coaching-plan">강의 계획</div>
          <div class="coaching-plan-detail">맞춤강의!</div>
        </div>
      </div>
    </div>
    -->
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
.coaching-name {
  display: inline-block;
  margin: 20px 0;
}
.coaching-name-box {
  display: flex;
  justify-content: space-around; /* 가로 중앙 정렬 */
  align-items: center; /* 세로 중앙 정렬 */
  border-color: black;
  border-bottom: solid 3px;
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
  border-radius: 1.5rem;
  align-items: left;
  flex-direction: column;
  width: 80%;
  margin: auto;
}
.coach-main {
  background-color: rgb(222, 222, 222);
  position: relative;
  width: 90%;
  margin: 20px auto;
  border-radius: 1.5rem;
}

.togglebox {
  display: flex;
  justify-content: right;
  margin: 20px 80px;
}
.calendar-div {
  display: flex;
  position: relative;
  margin: 0 0 0 60px;
  display: inline-block;
}
.memo {
  position: absolute;
  top: 0;
  left: 100%; /* 왼쪽 끝에서 시작하도록 함 */
  margin: 10px; /* 왼쪽 간격 조절 */
  background-color: rgb(250, 244, 185);
  width: 300px;
  height: 350px;
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
.q-date__main {
  width: 10px; /* 적절한 크기로 조절하세요 */
  height: 10px;
  line-height: 30px;
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
</style>
