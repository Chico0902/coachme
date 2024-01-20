import MainView from '../../views/MainView.vue'
import LoginView from '../../views/LoginView.vue'
import RegistView from '../../views/RegistView.vue'
import FindPasswordView from '../../views/FindPasswordView.vue'
import Component from '../../views/MainPageTestView.vue'
import Layout from '../../layout/MainLayout.vue'
export default [
  {
    path: '/',
    name: 'Desktop-1',
    component: MainView
  },
  {
    path: '/login',
    name: 'Desktop-2',
    component: LoginView
  },
  {
    path: '/regist',
    name: 'Desktop-3',
    component: RegistView
  },
  {
    path: '/find-password',
    name: 'Desktop-4',
    component: FindPasswordView
  },
  {
    path: '/component',
    component: Component
  },
  {
    path: '/layout',
    component: Layout
  }
]
