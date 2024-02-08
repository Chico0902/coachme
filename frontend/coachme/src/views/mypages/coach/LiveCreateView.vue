<script setup>
// 코칭 리스트 api받아와야함

import { ref,computed } from 'vue';




const combinedDateTime = computed(() => {
  if (coachingDate.value && coachingTime.value) {
    return `${coachingDate.value}T${coachingTime.value}`;
  } else {
    return '';
  }
});


const coachingDate = ref('');
const coachingTime = ref('');


const formattedDateTime = computed(() => {
  return combinedDateTime.value;
});

const coachingDateTime = ref('');

const sendDataToAPI = () => {
  console.log('API:', coachingDateTime.value);
};


</script>

<template>
  <div class="coaching-outside">
    <div>
      <div class="coaching-title box">
        <div class="coaching-title title">코칭 이름</div>
        <select v-model="coachingName">
          <option v-for="name in coachingList" :key="name" :value="name">{{ name }}</option>
        </select>
      </div>
      <div class="list-box">
      </div>
    </div>
    <div class="coaching-date box">
      <div class="coaching-date title">
        코칭일자 및 시간
      </div>
      <div class="coaching-date input">
        <input type="datetime-local" v-model="coachingDateTime" />
      </div>
    </div>
    <!-- <div class="coaching-date box">
      <div class="coaching-date title">
        코칭일자
      </div>
      <div class="coaching-date input">
        <input type="date" v-model="coachingDate" />
      </div>
      <div class="coaching-time box">
        <div class="coaching-time title">
          코칭 시간
        </div>
        <div class="coaching-time input">
          <input type="time" v-model="coachingTime" />
        </div>
      </div>
    </div> -->
    <div class="menu SMN_effect-42">
      <RouterLink :to="{ name: 'Desktop-5-5' }" @click="sendDataToAPI">
        <span data-hover="라이브 생성2">라이브 생성2</span>
      </RouterLink>
    </div>
  </div>
</template>

<style scoped>
.title {
  margin: 2rem;
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
  display: flex;
  flex-direction: column; 
  align-items: flex-start;
  margin-bottom: 1rem;
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
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 1rem; 
}

.coaching-date .title {
  font-size: 18px; 
  font-weight: 600; 
}

.coaching-date input {
  padding: 8px;
  border-radius: 4px;
  border: 1px solid #ccc;
  font-size: 14px;
}


.coaching-time {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.coaching-time .title {
  font-size: 18px; 
  font-weight: 600; 
}

.coaching-time input {
  padding: 8px;
  border-radius: 4px;
  border: 1px solid #ccc;
  font-size: 14px;
}

.menu {
  display: flex;
  align-items: center;
  justify-content: right;
  margin: 0 2rem;
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