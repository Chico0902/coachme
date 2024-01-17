import axios from 'axios'

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

export { backendAxios }
