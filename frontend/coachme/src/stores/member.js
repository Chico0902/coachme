import { defineStore } from 'pinia'

export const useMemberStore = defineStore('member', {
  state: () => {
    return {
      profileText: '',
      profileImageUrl: ''
    }
  }
})
