import TestIndexView from '../../tests/views/TestIndexView.vue'
import TestLoginView from '../../tests/views/TestLoginView.vue'
import TestRegistView from '../../tests/views/TestRegistView.vue'
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
  }
]
