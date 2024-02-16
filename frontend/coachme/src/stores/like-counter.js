/* 찜콩버튼 id 구분용 */

import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useLikeStore = defineStore('counter', () => {
  const count = ref(0) // 현재 웹페이지의 찜콩 버튼 개수

  function incrementCount() {
    count.value++
  } // 찜콩 버튼이 생성되면 +1

  function getCount() {
    return count.value
  } // id 생성용 getter


  return { count, incrementCount, getCount }
})
