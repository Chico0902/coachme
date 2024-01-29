import axios from 'axios'
import { getAccessToken } from './functions/auth'

const { VITE_BACKEND_URL } = import.meta.env
let accessToken

function backendAxios() {
  const instance = axios.create({
    baseURL: VITE_BACKEND_URL,
    headers: {
      'Content-Type': 'application/json;charset=utf-8'
    }
  })
  return instance
}

function authBackendAxios() {
  try {
    accessToken = getAccessToken()
    console.log(accessToken)
    return axios.create({
      baseURL: VITE_BACKEND_URL,
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
        Authorization: 'Bearer ' + accessToken
      }
    })
  } catch (e) {
    console.log('엑세스 토큰이 없습니다.')
  }
}

export { backendAxios, authBackendAxios }
