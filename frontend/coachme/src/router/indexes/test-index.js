import TestIndexView from '../../tests/views/TestIndexView.vue'
import TestLoginView from '../../tests/views/TestLoginView.vue'
import TestRegistView from '../../tests/views/TestRegistView.vue'
import TestChangePasswordView from '../../tests/views/TestChangePasswordView.vue'
import TestProfileView from '../../tests/views/TestProfileView.vue'
import { guardPrivilegeOne } from '../middlewares/test-before-guard-privilege'
export default [
  {
    path: '/test',
    name: 'test-index',
    component: TestIndexView
  },
  {
    path: '/test/login',
    name: 'test-login',
    component: TestLoginView
  },
  {
    path: '/test/regist',
    name: 'test-regist',
    component: TestRegistView
  },
  {
    path: '/test/newpassword',
    name: 'test-newpassword',
    component: TestChangePasswordView
  },
  {
    path: '/test/profile',
    name: 'test-profile',
    component: TestProfileView,
    beforeEnter: guardPrivilegeOne
  }
]
