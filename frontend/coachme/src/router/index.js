import { createRouter, createWebHistory } from 'vue-router'
import mainIndex from './indexes/main-index'
import testIndex from './indexes/test-index'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...mainIndex, ...testIndex]
})

export default router
