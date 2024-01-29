import { defineStore } from 'pinia'

export const useMemberStore = defineStore('member', {
  state: () => {
    return {
      longId: Number,
      stringId: '',
      name: '',
      privilege: ''
    }
  }
})
