import { setupWorker } from 'msw/browser'
import { HttpResponse, http } from 'msw'

const mockLoginResponseDto = {
  data: { accessToken: 'mock-access-token', refreshToken: 'mock-refresh-token' }
}

const mockMemberRegistResponseDto = {
  data: { message: '회원가입 성공' }
}

const mockChangePasswordResponseDto = {
  data: { message: '비밀번호 변경 및 임시 비밀번호 발송 완료' }
}
const mockProfileResponseDto = {
  data: {
    profileText: '테스트 프로필입니다. 사진은 랜덤입니다.',
    profileImageUrl: 'https://picsum.photos/200'
  }
}
const mockProfileImageModifyResponseDto = {
  data: { message: '프로필 사진 수정 및 기존파일 삭제 완료' }
}

const mockProfileTextModifyResponseDto = {
  data: { message: '프로필 변경 완료' }
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
  http.patch('http://localhost/members/profiles/:id', ({ params }) => {
    console.log('params : ' + params.id)
    return HttpResponse.json(mockProfileResponseDto)
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
