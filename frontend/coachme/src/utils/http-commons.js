import axios from 'axios'
import router from '@/router'
import { getRefresh } from './api/auth-api'

const { VITE_BACKEND_URL } = import.meta.env

function backendAxios() {
  const instance = axios.create({
    baseURL: VITE_BACKEND_URL,
    headers: {
      'Content-Type': 'application/json;charset=utf-8'
    }
  })
  return instance
}

// 토큰 재발급 로직이 들어간 Axios
function authBackendAxios() {
  const instance = axios.create({
    baseURL: VITE_BACKEND_URL,
    withCredentials: true,
    headers: {
      'Content-Type': 'application/json;charset=utf-8'
    }
  })
  console.log(instance)

  // instance.interceptors.response.use(function (response) {
  //   if (response.status === '401') {
  //     // 엑세스 토큰 만료일 경우, 토큰 재발급 요청
  //     if (response.data.message === 'Access Token Expired') {
  //       getRefresh()
  //       // 리프레쉬 토큰 만료일 경우, 로그인 페이지로 이동
  //     } else if (response.data.message === 'Refresh Token Expired') router.push('/login')
  //   }
  // })
  console.log(instance)
  return instance
}

export { backendAxios, authBackendAxios }
