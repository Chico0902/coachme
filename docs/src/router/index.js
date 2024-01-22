import { createRouter, createWebHistory } from 'vue-router'
import RouterButtonView from '../views/RouterButtonView.vue'
import MainView from '../views/MainView.vue'
import ProposalView from '../views/ProposalView.vue'
import SpecView from '../views/SpecView.vue'
import WireframeView from '../views/WireframeView.vue'
import DicView from '../views/DicView.vue'
import ApiView from '../views/ApiView.vue'
import GitView from '../views/GitView.vue'
import AgileView from '../views/AgileView.vue'
import ConventionView from '../views/ConventionView.vue'
import ServerView from '../views/ServerView.vue'
import ErdView from '../views/ErdView.vue'
import MinutesView from '../views/MinutesView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'index',
      components: { default: MainView, nav: RouterButtonView }
    },
    {
      path: '/proposal',
      name: 'proposal',
      components: { default: ProposalView, nav: RouterButtonView }
    },
    {
      path: '/spec',
      name: 'spec',
      components: { default: SpecView, nav: RouterButtonView }
    },
    {
      path: '/wireframe',
      name: 'wireframe',
      components: { default: WireframeView, nav: RouterButtonView }
    },
    {
      path: '/dic',
      name: 'dic',
      components: { default: DicView, nav: RouterButtonView }
    },
    {
      path: '/api',
      name: 'api',
      components: { default: ApiView, nav: RouterButtonView }
    },
    {
      path: '/git',
      name: 'git',
      components: { default: GitView, nav: RouterButtonView }
    },
    {
      path: '/agile',
      name: 'agile',
      components: { default: AgileView, nav: RouterButtonView }
    },
    {
      path: '/convention',
      name: 'convention',
      components: { default: ConventionView, nav: RouterButtonView }
    },
    {
      path: '/server',
      name: 'server',
      components: { default: ServerView, nav: RouterButtonView }
    },
    {
      path: '/erd',
      name: 'erd',
      components: { default: ErdView, nav: RouterButtonView }
    },
    {
      path: '/minutes',
      name: 'minutes',
      components: { default: MinutesView, nav: RouterButtonView }
    }
  ]
})

export default router
