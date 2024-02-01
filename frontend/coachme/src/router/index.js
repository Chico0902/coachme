import { createRouter, createWebHistory } from 'vue-router'
import mainIndex from './indexes/main-index'
import mypageCoameIndex from './indexes/mypage-coame-index'
import mypageCoachIndex from './indexes/mypage-coach-index'
import mypageAdminIndex from './indexes/mypage-admin-index'
import searchIndex from './indexes/search-index'
import liveIndex from './indexes/live-index'
import testIndex from './indexes/test-index'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...mainIndex,
    ...mypageCoameIndex,
    ...mypageCoachIndex,
    ...mypageAdminIndex,
    ...searchIndex,
    ...liveIndex,
    ...testIndex
  ]
})

export default router
