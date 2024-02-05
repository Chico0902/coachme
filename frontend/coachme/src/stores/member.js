import { defineStore } from 'pinia'

export const useMemberStore = defineStore('member', {
  state: () => {
    return {
      profileText: '프로필을 등록하세요.',
      profileImageUrl: '/src/assets/icons/coame.png'
    }
  }
})
