import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { Quasar } from 'quasar'
import App from './App.vue'
import piniaPersist from 'pinia-plugin-persist'
import router from './router'
import 'quasar/dist/quasar.css'
import '@quasar/extras/material-icons/material-icons.css'
import './style.css'
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
