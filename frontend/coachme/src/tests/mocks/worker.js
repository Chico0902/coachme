import { setupWorker } from 'msw/browser'
import { HttpResponse, http } from 'msw'

const mockLoginResponseDto = {
  accessToken:
    'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxMjMiLCJuYW1lIjoi6rOg7JaR7J20IiwicHJpdmlsZWdlIjoiQ09BTUUifQ.el7ZYY5gjG2Jr6Te2cx_Ia-qllT1-hU2Jrz69vr0PYg',
  refreshToken: 'mock-refresh-token'
}

const mockMemberRegistResponseDto = {
  message: '회원가입 성공'
}

const mockChangePasswordResponseDto = {
  message: '비밀번호 변경 및 임시 비밀번호 발송 완료'
}

const mockProfileImageModifyResponseDto = {
  message: '프로필 사진 수정 및 기존파일 삭제 완료'
}

const mockProfileTextModifyResponseDto = {
  message: '프로필 변경 완료'
}

const handlers = [
  http.post('http://localhost/members/login', () => {
    return HttpResponse.json(mockLoginResponseDto)
  }),
  http.post('http://localhost/members', () => {
    return HttpResponse.json(mockMemberRegistResponseDto)
  }),
  http.patch('http://localhost/members/passwords', () => {
    return HttpResponse.json(mockChangePasswordResponseDto)
  }),
  http.patch('http://localhost/members/profiles/images/:id', ({ params }) => {
    console.log('params : ' + params.id)
    return HttpResponse.json(mockProfileImageModifyResponseDto)
  }),
  http.patch('http://localhost/members/profiles/texts/:id', ({ params }) => {
    console.log('params : ' + params.id)
    return HttpResponse.json(mockProfileTextModifyResponseDto)
  })
]

export const worker = setupWorker(...handlers)
