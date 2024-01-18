import { setupWorker } from 'msw/browser'
import { HttpResponse, http } from 'msw'

const mockLoginResponseDto = {
  accessToken: 'mock-access-token',
  refreshToken: 'mock-refresh-token'
}

const mockMemberRegistResponseDto = {
  message: '회원가입 성공'
}

const mockchangePasswordResponseDto = {
  message: '비밀번호 변경 및 임시 비밀번호 발송 완료'
}

const handlers = [
  http.post('http://localhost/members/login', () => {
    return HttpResponse.json(mockLoginResponseDto)
  }),
  http.post('http://localhost/members', () => {
    return HttpResponse.json(mockMemberRegistResponseDto)
  }),
  http.patch('http://localhost/members/passwords', () => {
    return HttpResponse.json(mockchangePasswordResponseDto)
  })
]

export const worker = setupWorker(...handlers)
