<script setup>
import selectCoachCard from '../molecules/SelectCoachCard.vue'
import { useCoachStore } from '@/stores/coach'
import { storeToRefs } from 'pinia'
import { onMounted } from 'vue'

/**
 * VARIABLES
 */

// in pinia
const coachStore = useCoachStore()
const { coaches } = storeToRefs(coachStore)

// for card
const cards = coaches
let likeText
let frame
let current
let MAX_DEFAULT_MOVE

/**
 * METHODS
 */

// card animation

onMounted(() => {
  if (cards.value.length > 0) {
    frame = document.querySelector('.frame')

    current = frame.querySelector('.card:last-child')
    likeText = current.children[0]
    MAX_DEFAULT_MOVE = frame.clientWidth * 0.6

    initCard(current)
  }
})

let startX = 0,
  startY = 0,
  moveX = 0,
  moveY = 0
let firstCardAnimated = false
let a_count = 0
const FPS = 150
let lastTimestamp = 0

function animateFirstCard(timestamp) {
  if (a_count >= Math.round(70 * Math.PI)) {
    setTransform(0, 0, 0)
    firstCardAnimated = true
    return cancelAnimationFrame(animateFirstCard)
  }
  requestAnimationFrame(animateFirstCard)
  if (timestamp - lastTimestamp < 1000 / FPS) return
  a_count += 1
  moveX = MAX_DEFAULT_MOVE * Math.sin(a_count / 35)
  setTransform(moveX, 0, (moveX / MAX_DEFAULT_MOVE) * 25)
  lastTimestamp = timestamp
}

function initCard(card) {
  if (!firstCardAnimated) animateFirstCard()
  card.addEventListener('pointerdown', onPointerDown)
}

function setTransform(x, y, deg, duration) {
  current.style.transform = `translate3d(${x}px, ${y}px, 0) rotate(${deg}deg)`
  likeText.style.opacity = Math.abs((x / innerWidth) * 2.1)
  likeText.className = `is-like ${x > 0 ? 'like' : 'nope'}`
  if (duration) current.style.transition = `transform ${duration}ms`
}

function onPointerDown({ clientX, clientY }) {
  if (!firstCardAnimated) return
  startX = clientX
  startY = clientY
  current.addEventListener('pointermove', onPointerMove)
  current.addEventListener('pointerup', onPointerUp)
  current.addEventListener('pointerleave', onPointerUp)
}

function onPointerMove({ clientX, clientY }) {
  moveX = clientX - startX
  moveY = clientY - startY
  setTransform(moveX, moveY, (moveX / innerWidth) * 50)
}

function onPointerUp() {
  current.removeEventListener('pointermove', onPointerMove)
  current.removeEventListener('pointerup', onPointerUp)
  current.removeEventListener('pointerleave', onPointerUp)
  if (Math.abs(moveX) > frame.clientWidth / 2) {
    current.removeEventListener('pointerdown', onPointerDown)
    complete()
  } else cancel()
}

function complete() {
  const flyX = (Math.abs(moveX) / moveX) * innerWidth * 1.3
  const flyY = (moveY / moveX) * flyX
  setTransform(flyX, flyY, (flyX / innerWidth) * 50, innerWidth)

  const prev = current
  const next = current.previousElementSibling
  if (next) initCard(next)
  current = next
  moveX = 0
  moveY = 0
  // 클릭만으로도 작동하는 것을 방지하는 코드. moveX와 moveY를 초기화함
  likeText = current.children[0]
  // appendCard(data[imgCount % 4])
  setTimeout(() => frame.removeChild(prev), innerWidth)
}

function cancel() {
  setTransform(0, 0, 0, 100)
  setTimeout(() => (current.style.transition = ''), 100)
}
</script>
<template>
  <template v-if="cards.length > 0">
    <section style="width: 75%">
      <div class="frame">
        <!-- 코치 선택 리스트 -->
        <q-card v-for="card in cards" :key="card.id" class="card coach" style="background-color: white">
          <!-- 지우지 말 것-->
          <div class="is-like">좋아요</div>
          <!-- 코치 카드 영역 -->
          <selectCoachCard
            :id="card.coachId"
            :coachName="card.memberName"
            :coaching="card.lessonInfo"
            :rating="card.avgScore"
            :reviewCount="card.reviewCount"
            :img="card.profileImg"
          ></selectCoachCard>
        </q-card>
      </div>
      <!-- 검색 결과가 없을 때 -->
    </section>
  </template>
  <template v-else>
    <q-card
      style="min-width: 50vw; height: 15vh; margin-top: 2vh; margin-left: 0.6vw"
      :rounded="true"
      :color="cardColor"
    >
      <q-item-section class="q-mb-md">
        <q-item-label class="text-h6" style="margin-top: 5.7vh">검색 결과가 없습니다.</q-item-label>
      </q-item-section>
    </q-card>
  </template>
</template>

<style scoped>
section {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 40vw;
  height: 100%;
  overflow: hidden;
  font-family: 'Nunito', sans-serif;
  /* background-color: #edeef3; */
}

.coach {
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  /*수직 정렬*/
  align-items: center;
  justify-content: center;
}

.frame {
  position: relative;
  width: 35vh;
  height: 58vh;
  min-width: 265px;
  min-height: 430px;
  z-index: 1;
  margin-top: -2vh;
}

.icons {
  margin-top: -1.7vh;
  user-select: none;
  z-index: 1;
}

.icons > svg {
  width: 10vh;
  height: 10vh;
  max-width: 60px;
  max-height: 60px;
  border-radius: 50%;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  cursor: pointer;
}

.card {
  position: absolute;
  display: flex;
  justify-content: center;
  width: 100%;
  height: 90%;
  color: #f1f1f1;
  border-radius: 10px;
  user-select: none;
  cursor: pointer;
  overflow: hidden;
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  touch-action: none;
  margin-top: 2vh;
}

.card .is-like {
  width: 100%;
  height: 100%;
  position: absolute;
  opacity: 0;
}

.card .is-like::after {
  position: absolute;
  left: 50%;
  top: 47%;
  transform: translateX(-50%) rotate(-10deg);
  width: 50%;
  height: 13%;
  font-size: 3em;
  font-weight: 600;
  border-radius: 0.15em;
  display: flex;
  align-items: center;
  justify-content: center;
}

.card .like::after {
  content: 'LIKE';
  color: #4dca93;
  border: 0.1em solid #4dca93;
  letter-spacing: normal;
}

.card .nope::after {
  content: 'PASS';
  color: #fb4f68;
  border: 0.1em solid #fb4f68;
  letter-spacing: normal;
}

.card .bottom {
  width: 100%;
  height: 25%;
  background: linear-gradient(to top, #00000080, #00000000);
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  padding-left: 7%;
  font-weight: 400;
}

.card .bottom .title > span:nth-child(1) {
  font-size: 2em;
  font-weight: 700;
  margin-right: 0.2em;
}

.card .bottom .title > span:nth-child(2) {
  font-size: clamp(13px, 1.5em, 25px);
}

.card .bottom .title > span:nth-child(2) > b {
  font-size: 0.6em;
  margin-right: 0.2em;
}

.card .bottom .info {
  margin: 3% 0 0 2%;
  font-size: clamp(10px, 1.1em, 20px);
}

@media screen and (max-height: 540px) {
  .frame {
    width: 54vh;
    height: 81vh;
    font-size: 13px;
  }
}

@media screen and (max-height: 440px) {
  .frame {
    font-size: 8px;
  }
}
</style>
