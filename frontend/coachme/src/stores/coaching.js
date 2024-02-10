import { defineStore } from 'pinia'

export const useCoachingStore = defineStore('coach', {
  state: () => {
    return {
      coachings: []
    }
  }
})
