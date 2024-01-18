import { defineStore } from 'pinia'

export const useMemberStore = defineStore('member', {
  state: () => {
    return {
      accessToken: '',
      refreshToken: ''
    }
  },
  persist: {
    enabled: true,
    strategies: [
      {
        storage: sessionStorage,
        paths: ['accessToken', 'refreshToken']
      }
    ]
  }
})
