import { defineStore } from 'pinia'

export const useMemberStore = defineStore('member', {
  state: () => {
    return {
      longId: Number,
      profileText: '프로필을 등록하세요.',
      profileImageUrl: '/assets/icons/coame.png'
    }
  },
  persist: {
    enabled: true,
    strategies: [
      {
        storage: sessionStorage,
        paths: ['longId', 'profileText', 'profileImageUrl']
      }
    ]
  }
})
