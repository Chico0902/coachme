import { defineStore } from 'pinia'

export const useMemberStore = defineStore('member', {
  state: () => {
    return {
      isLogin: false,
      longId: Number,
      stringId: '',
      name: '',
      privilege: 'COAME' // 테스트용
    }
  }
})
