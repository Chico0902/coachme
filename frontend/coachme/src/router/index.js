import { createRouter, createWebHistory } from 'vue-router'
import mainIndex from './indexes/main-index'
import mypageIndex from './indexes/mypage-index'
import searchIndex from './indexes/search-index'
import liveIndex from './indexes/live-index'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...mainIndex, ...mypageIndex, ...searchIndex, ...liveIndex]
})

export default router
