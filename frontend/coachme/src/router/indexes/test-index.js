import TestLoginView from '../../tests/components/TestLoginView.vue'
import TestRegistView from '../../tests/components/TestRegistView.vue'
export default [
  {
    path: '/test/login',
    name: 'login-test',
    component: TestLoginView
  },
  {
    path: '/test/regist',
    name: 'regist-test',
    component: TestRegistView
  }
]
