import TestLoginView from '../../tests/components/TestLoginView.vue'
import TestRegistView from '../../tests/components/TestRegistView.vue'
export default [
  {
    path: '/test/login',
    name: 'test-login',
    component: TestLoginView
  },
  {
    path: '/test/regist',
    name: 'test-regist',
    component: TestRegistView
  }
]
