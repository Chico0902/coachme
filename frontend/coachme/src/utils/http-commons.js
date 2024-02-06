import axios from 'axios'
import router from '@/router'
import { getRefresh } from './api/auth-api'
import { getAccessToken } from './functions/auth'

const { VITE_BACKEND_URL } = import.meta.env

// 기본 axios 객체 생성
export function jsonAxios() {
  const instance = axios.create({
    baseURL: VITE_BACKEND_URL,
    headers: {
      'Content-Type': 'application/json;charset=utf-8'
    }
  })

  return instance
}

// formData용 axios 객체 생성
export function fileAxios() {
  const instance = axios.create({
    baseURL: VITE_BACKEND_URL,
    headers: {
      'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>'
    }
  })

  return instance
}

// 인증이 필요한 axios 객체의 interceptor 설정
export const authAxios = axios.create({
  baseURL: VITE_BACKEND_URL,
  headers: {
    'Content-Type': 'application/json;charset=utf-8'
  },
  withCredentials: true
})

// 인증 토큰 header에 추가하는 인터셉터
authAxios.interceptors.request.use((config) => {
  const token = getAccessToken()
  config.headers.Authorization = `Bearer ${token}`

  return config
})

// 인증 만료 시 토큰 재발급을 위한 인터셉터
authAxios.interceptors.response.use(
  function (success) {
    return success
  },
  async function (fail) {
    console.log(fail)
    if (fail.response.status === 401) {
      // 엑세스 토큰 만료일 경우, 토큰 재발급 요청
      if (fail.response.data.message === 'Access Token Expired') {
        await getRefresh(
          (success) => {
            // 토큰 저장
            const accessToken = { accessToken: success.headers.authorization }
            sessionStorage.setItem('auth', JSON.stringify(accessToken))

            // 토큰 달아서 재요청
            fail.config.headers.Authorization = `Bearer ${accessToken.accessToken}`
            return axios.request(fail.config)
          },
          (fail) => console.log(fail)
        )
        return authAxios(fail.config)
        // 리프레쉬 토큰 만료일 경우, 로그인 페이지로 이동
      } else if (fail.response.data.message === 'Refresh Token Expired') {
        router.push('/login')
        return Promise.reject(fail)
      }
    }
  }
)

// 인증이 필요한 form-data axios 객체의 interceptor 설정
export function fileAuthAxios(token) {
  const instance = axios.create({
    baseURL: VITE_BACKEND_URL,
    headers: {
      'Content-Type': 'multipart/form-data; boundary=<calculated when request is sent>',
      Authorization: `Bearer ${token}`
    },
    withCredentials: true
  })

  // 인증 만료 시 토큰 재발급을 위한 인터셉터
  instance.interceptors.response.use(
    function (success) {
      return success
    },
    function (fail) {
      if (fail.response.status === 401) {
        // 엑세스 토큰 만료일 경우, 토큰 재발급 요청
        if (fail.response.data.message === 'Access Token Expired') {
          getRefresh(
            (success) => {
              console.log(success)
              const accessToken = { accessToken: success.headers.Authorization }
              sessionStorage.setItem('auth', JSON.stringify(accessToken))
              fail.config.headers.Authorization = `Bearer ${accessToken.accessToken}`
              return axios.request(fail.config)
            },
            (fail) => console.log(fail)
          )
          // 리프레쉬 토큰 만료일 경우, 로그인 페이지로 이동
        } else if (fail.response.data.message === 'Refresh Token Expired') router.push('/login')
      }
      return Promise.reject(fail)
    }
  )
  return instance
}
