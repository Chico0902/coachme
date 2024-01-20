import MainView from '../../views/MainView.vue'
import LoginView from '../../views/LoginView.vue'
import RegistView from '../../views/RegistView.vue'
import RegistCoachView from '../../views/mypages/RegistCoachView.vue'
import AdminMemberListView from '../../views/mypages/admin/AdminMemberListView.vue'
import FindPasswordView from '../../views/FindPasswordView.vue'
import { checkPrivilege } from '../middlewares/check-privilege'
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
    path: '/mypage',
    beforeEnter: checkPrivilege,
    children: [
      { path: '', name: 'mypage' },
      { path: 'regist/coach', name: 'Desktop-5', component: RegistCoachView },
      { path: 'admin', name: 'Desktop-11', component: AdminMemberListView }
    ]
  }
]
