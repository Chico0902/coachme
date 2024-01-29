import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPersist from 'pinia-plugin-persist'
import { Quasar } from 'quasar'
import 'quasar/dist/quasar.css'
import '@quasar/extras/material-icons/material-icons.css'
import App from './App.vue'
import router from './router'
import { worker } from '../src/tests/mocks/worker'

// MSW Mocking Server(가짜 서버) 실행
// if (import.meta.env.MODE === 'development') {
//   worker.start()
// }

// 앱 생성
const app = createApp(App)

// 미들웨어 추가
const pinia = createPinia()
pinia.use(piniaPersist)
app.use(pinia)

app.use(router)

app.use(Quasar, {
  plugins: {}
})

// 앱 마운트
app.mount('#app')
