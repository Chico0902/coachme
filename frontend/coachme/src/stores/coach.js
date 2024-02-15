import { defineStore } from 'pinia'
import { ref } from 'vue'
import { postCoachesByCategory } from '@/utils/api/coach-api'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'
import { decodeToken, getAccessToken } from '@/utils/functions/auth'

export const useCoachStore = defineStore('coach', () => {
  // member login 여부
  const authStore = useAuthStore()
  const { isLogin } = storeToRefs(authStore)
  const loginMemberId = isLogin.value ? decodeToken(getAccessToken()).longId : -1

  // coaching data
  const coaches = ref([])

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

  function receiveCoachesByCategoryAndWord(mainCatagoryName, subCategoryName, keyword) {
    // subCategory 변경
    const subCategoryIndex = getSubCategoryIndex(mainCatagoryName)
    subCategories.value = sideButtonList[subCategoryIndex]

    // 반응형 변수 변경(첫글자 대문자)
    selectedMainCategory.value = mainCatagoryName.replace(/^[a-z]/, (char) => char.toUpperCase())
    selectedSubCategory.value = subCategoryName.replace(/^[a-z]/, (char) => char.toUpperCase())

    // 데이터 받아오기
    postCoachesByCategory(
      mainCatagoryName,
      subCategoryName,
      { words: keyword, loginMemberId },
      (success) => {
        coaches.value = success.data.list
      },
      (fail) => console.error(fail)
    )
  }

  // mainCategoryName -> subCategoryIndex
  function getSubCategoryIndex(mainCatagoryName) {
    switch (mainCatagoryName) {
      case 'ALL':
        return 0
      case 'LIFE':
        return 1
      case 'CREATION':
        return 2
      case 'SPORTS':
        return 3
      case 'DEVELOP ':
        return 4
      case 'HEALTH':
        return 5
    }
    return 0
  }

  return {
    sideButtonList,
    coaches,
    selectedMainCategory,
    selectedSubCategory,
    subCategories,
    receiveCoachesByCategoryAndWord
  }
})
