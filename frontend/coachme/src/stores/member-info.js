import { defineStore } from 'pinia'

export const useMemberStore = defineStore('member-info', {
  state: () => {
    return {
      memberId: '',
      privilege: '',
      accessToken: '',
      refreshToken: ''
    }
  },
  persist: {
    enabled: true,
    strategies: [
      {
        storage: sessionStorage,
        paths: ['memberId', 'privilege', 'accessToken', 'refreshToken']
      }
    ]
  }
})
