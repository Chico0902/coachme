import { createRouter, createWebHistory } from 'vue-router'
import mainIndex from './indexes/main-index'
import mypageIndex from './indexes/mypage-index'
import testIndex from './indexes/test-index'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...mainIndex, ...mypageIndex, ...testIndex]
})

export default router
