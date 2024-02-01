import { HttpResponse, http } from 'msw'

const mockLoginResponseDto = {
  accessToken:
    'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMyIsIm5hbWUiOiLqs6DslpHsnbQiLCJwcml2aWxlZ2UiOiJDT0FNRSJ9.CJQRuGr5AxmK7Rva0ner_84rHjPc9baBJ3KwD630xRs',
  refreshToken: 'mock-refresh-token'
}
const mockCoachLoginResponseDto = {
  accessToken:
    'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEiLCJuYW1lIjoi6rOg7L2U7LmYIiwicHJpdmlsZWdlIjoiQ09BQ0gifQ.2eJI-xdV3-jFXBQ5FaZmqcE2qQXNCgwo-G1BUS3pEJ4',
  refreshToken: 'mock-refresh-token'
}
const mockAdminLoginResponseDto = {
  accessToken:
    'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjAiLCJuYW1lIjoi6rSA66as7J6QIiwicHJpdmlsZWdlIjoiQURNSU4ifQ.wxFYo1hPdzVdE56oUkbbqRTxqoXOshHhK-GiI8TDQeY',
  refreshToken: 'mock-refresh-token'
}

export default [
  http.post('http://localhost/auth/login', async ({ request }) => {
    const newPost = await request.json()
    switch (newPost.stringId) {
      case 'coach':
        return HttpResponse.json(mockCoachLoginResponseDto)
      case 'admin':
        return HttpResponse.json(mockAdminLoginResponseDto)
      default:
        return HttpResponse.json(mockLoginResponseDto)
    }
  })
]
