import { createRouter, createWebHistory } from 'vue-router'
import MainView from '../views/MainView.vue'
import ProposalView from '../views/ProposalView.vue'
import SpecView from '../views/SpecView.vue'
import WireframeView from '../views/WireframeView.vue'
import DicView from '../views/DicView.vue'
import ApiView from '../views/ApiView.vue'
import GitView from '../views/GitView.vue'
import AgileView from '../views/AgileView.vue'
import ConventionView from '../views/ConventionView.vue'
import ErdView from '../views/ErdView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'index',
      component: MainView
    },
    {
      path: '/proposal',
      name: 'proposal',
      component: ProposalView
    },
    {
      path: '/spec',
      name: 'spec',
      component: SpecView
    },
    {
      path: '/wireframe',
      name: 'wireframe',
      component: WireframeView
    },
    {
      path: '/dic',
      name: 'dic',
      component: DicView
    },
    {
      path: '/api',
      name: 'api',
      component: ApiView
    },
    {
      path: '/git',
      name: 'git',
      component: GitView
    },
    {
      path: '/agile',
      name: 'agile',
      component: AgileView
    },
    {
      path: '/convention',
      name: 'convention',
      component: ConventionView
    },
    {
      path: '/erd',
      name: 'erd',
      component: ErdView
    }
  ]
})

export default router
