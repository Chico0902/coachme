import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => {
    return {
      accessToken: '',
      isLogin: false
    }
  },
  persist: {
    enabled: true,
    strategies: [
      {
        storage: sessionStorage,
        paths: ['accessToken', 'isLogin']
      }
    ]
  }
})
