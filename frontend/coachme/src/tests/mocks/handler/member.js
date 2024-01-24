import { HttpResponse, http } from 'msw'
const mockLoginResponseDto = {
  data: {
    accessToken:
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMyIsIm5hbWUiOiLqs6DslpHsnbQiLCJwcml2aWxlZ2UiOiJDT0FNRSJ9.CJQRuGr5AxmK7Rva0ner_84rHjPc9baBJ3KwD630xRs',
    refreshToken: 'mock-refresh-token'
  }
}
const mockCoachLoginResponseDto = {
  data: {
    accessToken:
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEiLCJuYW1lIjoi6rOg7L2U7LmYIiwicHJpdmlsZWdlIjoiQ09BQ0gifQ.2eJI-xdV3-jFXBQ5FaZmqcE2qQXNCgwo-G1BUS3pEJ4',
    refreshToken: 'mock-refresh-token'
  }
}
const mockAdminLoginResponseDto = {
  data: {
    accessToken:
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjAiLCJuYW1lIjoi6rSA66as7J6QIiwicHJpdmlsZWdlIjoiQURNSU4ifQ.wxFYo1hPdzVdE56oUkbbqRTxqoXOshHhK-GiI8TDQeY',
    refreshToken: 'mock-refresh-token'
  }
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
  data: { profileImageUrl: 'https://picsum.photos/200/300' }
}

const mockProfileTextModifyResponseDto = {
  data: { message: '프로필 변경 완료' }
}

const mockMemberDuplicateRequestDto = {
  data: { message: '사용가능한 아이디입니다.' }
}

export default [
  http.post('http://localhost/members', () => {
    return HttpResponse.json(mockMemberRegistResponseDto)
  }),
  http.patch('http://localhost/members/passwords', () => {
    return HttpResponse.json(mockChangePasswordResponseDto)
  }),
  http.get('http://localhost/members/profiles/:id', ({ params }) => {
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
  }),
  http.post('http://localhost/members/duplicate/id', () => {
    return HttpResponse.json(mockMemberDuplicateRequestDto)
  })
]
