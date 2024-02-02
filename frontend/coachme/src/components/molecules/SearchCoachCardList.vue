<!-- 코치 리스트 카드(틴더라이크)
  주의할 사항 : 갱신해야하는 것은 cards 배열임에 주의하기
  배열에 필요한 사항 
  name, category, rating, desc, img
 -->

<script setup>
import selectCoachCard from '../molecules/SelectCoachCard.vue'

import { onMounted, ref } from 'vue'

const cards = ref([
  { name: 'Korean Fried', category: '한국', rating: '4.6', reviewCount: 122, desc: '한국 치킨', img: `${cloudUrl}tender01.jpg` },
  { name: 'Grilled', category: '그릴', rating: '4.6', reviewCount: 122, desc: '그릴 치킨', img: `${cloudUrl}tender02.jpg` },
  { name: 'Fried', category: '튀김', rating: '4.6', reviewCount: 122, desc: '치킨', img: `${cloudUrl}tender03.jpg` },
  {
    name: 'Deep Fried', category: '아무튼 치킨', rating: '4.6', reviewCount: 122, desc:
      'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes',
    img: `${cloudUrl}tender04.jpg`
  }
]);

let imgCount = 0
const cloudUrl = 'https://djjjk9bjm164h.cloudfront.net/'

let like
let likeText
let frame
let hate
let current
let MAX_DEFAULT_MOVE

onMounted(() => {
  frame = document.querySelector('.frame')
  console.log(frame)
  like = document.querySelector('#like')
  hate = document.querySelector('#hate')

  current = frame.querySelector('.card:last-child')
  likeText = current.children[0]
  MAX_DEFAULT_MOVE = frame.clientWidth * 0.6

  initCard(current)

  like.onclick = () => {
    if (!firstCardAnimated) return
    moveX = 1
    moveY = 0
    complete()
  }
  hate.onclick = () => {
    if (!firstCardAnimated) return
    moveX = -1
    moveY = 0
    complete()
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

// const appendCard = (data) => {
//   const newCard = {
//     id: imgCount++,
//     img: data.img,
//     name: data.name,
//     price: data.price,
//     distance: data.distance,
//   };

//   cards.value.unshift(newCard);
// };

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
  <section>
    <div class="frame">
      <!-- 코치 선택 리스트 -->
      <q-card v-for="card in cards" :key="card.id" class="card coach" style="background-color: white;">
        <!-- 지우지 말 것-->
        <div class="is-like">좋아요</div>
        <!-- 코치 카드 영역 -->
        <selectCoachCard :name="card.name" :category="card.category" :rating="card.rating" :reviewCount="card.reviewCount"
        :desc="card.desc" :img="card.img"></selectCoachCard>
      </q-card>
    </div>

    <!-- 버튼 영역 -->
    <div class="icons">
      <svg id="hate" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 128.07 127.89">
        <path class="a"
          d="M128.07,64.07c-.5,36.31-28,63.57-64,63.82S-.17,99.33,0,63.29C.17,28.25,29.23-.3,64.43,0A63.88,63.88,0,0,1,128.07,64.07ZM45.32,38.54c-2.44.36-4.63,1.12-6,3.68a6.39,6.39,0,0,0,.94,7.83A143,143,0,0,0,50.42,60.36c2.73,2.48,3.44,4.31.2,7a98.44,98.44,0,0,0-9.52,9.53c-3.62,4-3.66,7.48-.47,10.59,2.82,2.76,7.12,2.54,10.7-.79,3.05-2.83,5.91-5.86,8.85-8.8,2.58-2.57,5.16-2.53,7.73,0,2.83,2.81,5.62,5.67,8.52,8.42,3.87,3.68,8.08,4.08,11,1.15,3.23-3.21,3-6.85-.83-11C83.57,73.21,80.44,70,77.1,67c-2.37-2.13-2.71-3.65-.13-5.91,3.24-2.85,6.15-6.08,9.2-9.15,4.17-4.2,4.66-8,1.45-11.34-2.93-3-7.58-2.61-11.49,1.19-3.34,3.25-6.66,6.52-9.85,9.91-1.64,1.74-2.85,1.73-4.49,0-3.32-3.5-6.84-6.81-10.21-10.26A9,9,0,0,0,45.32,38.54Z" />
        <path
          d="M45.32,38.54a9,9,0,0,1,6.26,2.87c3.37,3.45,6.89,6.76,10.21,10.26,1.64,1.73,2.85,1.74,4.49,0,3.19-3.39,6.51-6.66,9.85-9.91C80,38,84.69,37.52,87.62,40.57c3.21,3.34,2.72,7.14-1.45,11.34-3,3.07-6,6.3-9.2,9.15-2.58,2.26-2.24,3.78.13,5.91,3.34,3,6.47,6.24,9.53,9.52,3.87,4.16,4.06,7.8.83,11-2.95,2.93-7.16,2.53-11-1.15-2.9-2.75-5.69-5.61-8.52-8.42-2.57-2.54-5.15-2.58-7.73,0-2.94,2.94-5.8,6-8.85,8.8-3.58,3.33-7.88,3.55-10.7.79-3.19-3.11-3.15-6.6.47-10.59a98.44,98.44,0,0,1,9.52-9.53c3.24-2.72,2.53-4.55-.2-7A143,143,0,0,1,40.28,50.05a6.39,6.39,0,0,1-.94-7.83C40.69,39.66,42.88,38.9,45.32,38.54Z" />
      </svg>
      <svg id="like" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 128.06 127.99">
        <path class="a"
          d="M128.06,63.83a63.65,63.65,0,0,1-64,64.16A63.57,63.57,0,0,1,0,64a64,64,0,0,1,128.06-.13ZM96,56.53c0-5.82-3.9-13.3-10.19-16.05-6.9-3-13.67-2.67-19.37,2.82-2,1.9-3.16,1.41-4.93-.17-2.34-2.08-4.86-3.89-8.25-4.24-9.13-.92-15.31,2.3-19.11,10.25-3.89,8.11-2.42,17.27,4,23.34,7.5,7,15.22,13.88,22.77,20.89,2.06,1.92,3.76,2.27,6,.21C74.36,86.7,82,80,89.39,73.09,93.57,69.21,96.06,64.45,96,56.53Z" />
        <path
          d="M96,56.53c.08,7.92-2.41,12.68-6.59,16.56C82,80,74.36,86.7,66.93,93.58c-2.23,2.06-3.93,1.71-6-.21-7.55-7-15.27-13.84-22.77-20.89-6.46-6.07-7.93-15.23-4-23.34,3.8-8,10-11.17,19.11-10.25,3.39.35,5.91,2.16,8.25,4.24,1.77,1.58,2.95,2.07,4.93.17,5.7-5.49,12.47-5.84,19.37-2.82C92.08,43.23,96,50.71,96,56.53Z" />
      </svg>
    </div>
  </section>
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
  height: 78vh;
  max-width: 400px;
  max-height: 700px;
  z-index: 1;
}

.icons {
  margin-top: 6vh;
  user-select: none;
  z-index: 1;
}

.icons>svg {
  width: 10vh;
  height: 10vh;
  max-width: 60px;
  max-height: 60px;
  border-radius: 50%;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  cursor: pointer;
}

.icons>svg:nth-child(1) {
  fill: #fb4f68;
  margin-right: 2vh;
}

.icons>svg:nth-child(2) {
  fill: #4dca93;
}

.icons>svg>path:nth-child(1) {
  fill: #fff;
}

.card {
  position: absolute;
  display: flex;
  justify-content: center;
  width: 100%;
  height: 100%;
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
  bottom: 30%;
  transform: translateX(-50%) rotate(-10deg);
  width: 70%;
  height: 13%;
  font-size: 3em;
  letter-spacing: 0.2em;
  font-weight: 600;
  border-radius: 0.15em;
  display: flex;
  align-items: center;
  justify-content: center;
}

.card .like::after {
  content: 'Like';
  color: #4dca93;
  border: 0.1em solid #4dca93;
}

.card .nope::after {
  content: 'Pass';
  color: #fb4f68;
  border: 0.1em solid #fb4f68;
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

.card .bottom .title>span:nth-child(1) {
  font-size: 2em;
  font-weight: 700;
  margin-right: 0.2em;
}

.card .bottom .title>span:nth-child(2) {
  font-size: clamp(13px, 1.5em, 25px);
}

.card .bottom .title>span:nth-child(2)>b {
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
}</style>
