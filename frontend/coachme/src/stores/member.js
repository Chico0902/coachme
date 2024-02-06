import { defineStore } from 'pinia'

export const useMemberStore = defineStore('member', {
  state: () => {
    return {
      profileText: '프로필을 등록하세요.',
      profileImageUrl: '/assets/icons/coame.png'
    }
  },
  persist: {
    enabled: true,
    strategies: [
      {
        storage: sessionStorage,
        paths: ['profileText', 'profileImageUrl']
      }
    ]
  }
})
