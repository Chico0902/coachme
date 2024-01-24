import MainView from '../../views/MainView.vue'
export default [
  {
    path: '/',
    name: 'Desktop-1',
    component: MainView
  },
  {
    path: '/login',
    name: 'Desktop-2',
    component: () => import('../../views/LoginView.vue')
  },
  {
    path: '/regist',
    name: 'Desktop-3',
    component: () => import('../../views/RegistView.vue')
  },
  {
    path: '/find-password',
    name: 'Desktop-4',
    component: () => import('../../views/FindPasswordView.vue')
  }
]
