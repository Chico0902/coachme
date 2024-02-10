import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useLiveCoachingStore = defineStore('counter', () => {
  const allLiveCoachings = ref({
    '2024/02/01': [
      {
        id: 1,
        className: '이준학의 라이브 농구교실 1',
        time: '12:00'
      },
      {
        id: 2,
        className: '이준학의 라이브 농구교실 2',
        time: '13:00'
      },
      {
        id: 3,
        className: '이준학의 라이브 농구교실 3',
        time: '14:00'
      }
    ],
    '2024/02/03': [
      {
        id: 4,
        className: '이준학의 라이브 농구교실 4',
        time: '13:00'
      }
    ],
    '2024/02/09': [
      {
        id: 5,
        className: '이준학의 라이브 농구교실 5',
        time: '13:00'
      }
    ]
  })
  const liveCoachings = ref([])
  const events = ref(['2024/02/01', '2024/02/03', '2024/02/09'])
  const students = ref([])
  return { allLiveCoachings, liveCoachings, events, students }
})
