import { defineStore } from 'pinia'

export const useProfileStore = defineStore('profile', {
  state: () => {
    return {
      profileImageUrl: '',
      profileText: ''
    }
  }
})
