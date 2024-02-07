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

let isAlreadyFetchingAccessToken = false
let subscribers = []

async function refreshAndNewRequest(error) {
  console.log(error)
  try {
    const { response: errorResponse } = error

    // 결과값을 처음 요청했던 promise의 resolve로 settle시킨다.
    const retryOriginalRequest = new Promise((resolve, reject) => {
      console.log('retry')
      addSubscriber(async (accessToken) => {
        try {
          errorResponse.config.headers['Authorization'] = 'Bearer ' + accessToken
          resolve(authAxios(errorResponse.config))
        } catch (err) {
          reject(err)
        }
      })
    })

    // refresh token을 이용해서 access token 요청
    if (!isAlreadyFetchingAccessToken) {
      isAlreadyFetchingAccessToken = true // 문닫기 (한 번만 요청)

      const { data } = await getRefresh((success) => {
        const accessToken = { accessToken: success.headers.authorization }
        sessionStorage.setItem('auth', JSON.stringify(accessToken))
      })

      isAlreadyFetchingAccessToken = false // 문열기 (초기화)

      onAccessTokenFetched(data.access)
    }

    return retryOriginalRequest // pending 됐다가 onAccessTokenFetched가 호출될 때 resolve
  } catch (error) {
    // router.push('/login')
    return Promise.reject(error)
  }
}

function addSubscriber(callback) {
  subscribers.push(callback)
}

function onAccessTokenFetched(accessToken) {
  subscribers.forEach((callback) => callback(accessToken))
  subscribers = []
}

// 인증 만료 시 토큰 재발급을 위한 인터셉터
authAxios.interceptors.response.use(
  function (success) {
    console.log(success)
    return success
  },
  async function (fail) {
    console.log(fail)
    if (fail.response.status === 401) {
      // 엑세스 토큰 만료일 경우, 토큰 재발급 요청
      if (fail.response.data.message === 'Access Token Expired') {
        return await refreshAndNewRequest(fail)

        // 리프레쉬 토큰 만료일 경우, 로그인 페이지로 이동
      } else if (fail.response.data.message === 'Refresh Token Expired') {
        router.push('/login')
        return Promise.reject(fail)
      }
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
