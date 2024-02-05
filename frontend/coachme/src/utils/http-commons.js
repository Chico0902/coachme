import axios from 'axios'
import router from '@/router'
import { getRefresh } from './api/auth-api'

const { VITE_BACKEND_URL } = import.meta.env

// 기본 axios 객체 생성
function backendAxios() {
  const instance = axios.create({
    baseURL: VITE_BACKEND_URL,
    headers: {
      'Content-Type': 'application/json;charset=utf-8'
    }
  })

  return instance
}

// axios instance에 header(토큰정보) 추가 및 토큰 재발급 로직 추가
function makeAuthAxios(axios, token) {
  // header에 토큰 추가 + withCredential 추가
  axios.interceptors.request.use(
    function (config) {
      config.headers.Authoriaztion = `Bearer ${token}`
      config.withCredentials = true
      return config
    },
    function (error) {
      Promise.reject(error)
    }
  )

  // 서버 401 응답 시 토큰 재발급 요청
  axios.interceptors.response.use(
    function (success) {
      return success
    },
    function (fail) {
      if (fail.status === '401') {
        // 엑세스 토큰 만료일 경우, 토큰 재발급 요청
        if (fail.data.message === 'Access Token Expired') {
          getRefresh(
            (success) => console.log(success),
            (fail) => console.log(fail)
          )
          // 리프레쉬 토큰 만료일 경우, 로그인 페이지로 이동
        } else if (fail.data.message === 'Refresh Token Expired') router.push('/login')
      }
      return Promise.reject(fail)
    }
  )
  return axios
}

export { backendAxios, makeAuthAxios }
