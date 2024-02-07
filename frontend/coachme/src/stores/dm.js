import { defineStore } from 'pinia'

export const useDmStore = defineStore('dm', {
  state: () => {
    return {
      roomId: ''
    }
  }
})
