import axios from 'axios'
import router from '@/router'
import { getRefresh } from './api/auth-api'
import { getAccessToken } from './functions/auth'

const { VITE_BACKEND_URL } = import.meta.env

// 기본 axios 객체 생성
export const publicAxios = axios.create({
  baseURL: VITE_BACKEND_URL,
  headers: {
    'Content-Type': 'application/json;charset=utf-8'
  }
})

// 인증이 필요한 axios 객체 생성
export const authAxios = axios.create({
  baseURL: VITE_BACKEND_URL,
  headers: {
    'Content-Type': 'application/json;charset=utf-8'
  },
  withCredentials: true
})

// 인증이 필요한 axios에 token 추가하는 인터셉터
authAxios.interceptors.request.use((config) => {
  const token = getAccessToken()
  config.headers.Authorization = `Bearer ${token}`
  return config
})

// 인증 만료 시 토큰 재발급을 위한 인터셉터
const refreshInterceptor = authAxios.interceptors.response.use(
  function (success) {
    return success
  },
  async function (fail) {
    try {
      if (fail.response.status === 401) {
        // 엑세스 토큰 만료일 경우, 토큰 재발급 요청
        if (fail.response.data.message === 'Access Token Expired') {
          console.error('access token expired')
          await getRefresh()
          fail.config.headers.Authorization = `Bearer ${getAccessToken()}`
          authAxios.interceptors.response.eject(refreshInterceptor)
          return await authAxios.request(fail.config)

          // 리프레쉬 토큰 만료일 경우, 로그인 페이지로 이동
        } else if (fail.response.data.message === 'Refresh Token Expired') {
          router.push('/login')
          return Promise.reject(fail)
        } else if (fail.response.data.message === 'Refresh Token is Null') {
          alert('리프레쉬 토큰이 없습니다.')
          router.push('/login')
          return Promise.reject(fail)
        } else if (fail.response.data.message === 'Refresh Token Forged') {
          alert('리프레쉬 토큰이 변경되었습니다.')
          router.push('/login')
          return Promise.reject(fail)
        }
      } else {
        return Promise.reject(fail)
      }
    } catch (e) {
      return Promise.reject(fail)
    }
  }
)

// formData(파일 전송)용 axios 객체 생성
export const authFileAxios = axios.create({
  baseURL: VITE_BACKEND_URL,
  headers: {
    'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>'
  }
})

// 인증이 필요한 axios에 token 추가하는 인터셉터
authFileAxios.interceptors.request.use((config) => {
  const token = getAccessToken()
  config.headers.Authorization = `Bearer ${token}`
  return config
})

// 인증 만료 시 토큰 재발급을 위한 인터셉터
authFileAxios.interceptors.response.use(
  function (success) {
    return success
  },
  async function (fail) {
    if (fail.response.status === 401) {
      // 엑세스 토큰 만료일 경우, 토큰 재발급 요청
      if (fail.response.data.message === 'Access Token Expired') {
        await getRefresh()
        fail.config.headers.Authorization = `Bearer ${getAccessToken()}`
        return await authAxios.request(fail.config)

        // 리프레쉬 토큰 만료일 경우, 로그인 페이지로 이동
      } else if (fail.response.data.message === 'Refresh Token Expired') {
        router.push('/login')
        return Promise.reject(fail)
      } else if (fail.response.data.message === 'Refresh Token is Null') {
        alert('리프레쉬 토큰이 없습니다.')
        router.push('/login')
        return Promise.reject(fail)
      }
    }
  }
)
