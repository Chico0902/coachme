import { setupWorker } from 'msw/browser'
import { HttpResponse, http } from 'msw'

const mockLoginResponseDto = {
  memberId: '123',
  privilege: '1',
  accessToken: 'mock-access-token',
  refreshToken: 'mock-refresh-token'
}

const handlers = [
  http.post('http://localhost/members/login', () => {
    return HttpResponse.json(mockLoginResponseDto)
  })
]

export const worker = setupWorker(...handlers)
