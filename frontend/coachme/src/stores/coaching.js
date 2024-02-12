import { defineStore } from 'pinia'
import { ref } from 'vue'
import { postCoachingsByCategory } from '@/utils/api/coaching-api'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'

export const useCoachingStore = defineStore('coaching', () => {
  // member login 여부
  const authStore = useAuthStore()
  const { isLogin } = storeToRefs(authStore)
  const loginMemberId = isLogin.value ? decodeToken(getAccessToken()).longId : -1

  // coaching data
  const coachings = ref([])

  // 검색 옵션
  const selectedMainCategory = ref('all')
  const selectedSubCategory = ref('all')
  const subCategories = ref([])
  const sideButtonList = [
    [],
    [{ name: 'ALL' }, { name: 'House' }, { name: 'Furniture' }, { name: 'Lifestyle' }, { name: 'Design' }],
    [{ name: 'ALL' }, { name: 'Cooking' }, { name: 'Knitting' }, { name: 'Art' }, { name: 'Beauty' }],
    [{ name: 'ALL' }, { name: 'Soccer' }, { name: 'Basketball' }, { name: 'Tennis' }, { name: 'Golf' }],
    [{ name: 'ALL' }, { name: 'Frontend' }, { name: 'Backend' }, { name: 'Database' }, { name: 'DevOps' }],
    [{ name: 'ALL' }, { name: 'Yoga' }, { name: 'Weight' }, { name: 'Running' }, { name: 'Crossfit' }]
  ]

  function receiveCoachingsByCategoryAndWord(mainCatagoryName, subCategoryIndex, keyword) {
    // 반응형 변수 변경
    selectedMainCategory.value = mainCatagoryName
    subCategories.value = sideButtonList[subCategoryIndex]
    console.log({ words: keyword, loginMemberId })

    // 데이터 받아오기
    postCoachingsByCategory(
      selectedMainCategory.value.toLowerCase(),
      selectedSubCategory.value.toLowerCase(),
      { words: keyword, loginMemberId },
      (success) => {
        coachings.value = success.data.list
        console.log(success)
      },
      (fail) => console.log(fail)
    )
  }
  return {
    coachings,
    selectedMainCategory,
    selectedSubCategory,
    subCategories,
    receiveCoachingsByCategoryAndWord
  }
})
