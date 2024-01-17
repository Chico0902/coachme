import { setupWorker } from 'msw/browser'
import { HttpResponse, http } from 'msw'

const mockLoginResponseDto = {
  memberId: '123',
  privilege: '1',
  accessToken: 'mock-access-token',
  refreshToken: 'mock-refresh-token'
}

const mockMemberRegistResponseDto = {
  message: '회원가입 성공'
}
const handlers = [
  http.post('http://localhost/members/login', () => {
    return HttpResponse.json(mockLoginResponseDto)
  }),
  http.post('http://localhost/members', () => {
    return HttpResponse.json(mockMemberRegistResponseDto)
  })
]

export const worker = setupWorker(...handlers)
