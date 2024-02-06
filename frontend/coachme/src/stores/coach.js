import { defineStore } from 'pinia'

export const useCoachStore = defineStore('coach', {
  state: () => {
    return {
      coaches: []
    }
  }
})
