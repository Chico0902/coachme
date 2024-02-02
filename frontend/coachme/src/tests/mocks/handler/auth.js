import { HttpResponse, http } from 'msw'

const mockLoginResponse = {
  accessToken: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMyIsIm5hbWUiOiLqs6DslpHsnbQiLCJwcml2aWxlZ2UiOiJDT0FNRSJ9.CJQRuGr5AxmK7Rva0ner_84rHjPc9baBJ3KwD630xRs`,
  profileImageUrl: 'testUrl',
  profileText: 'testText'
}
const mockCoachLoginResponse = {
  accessToken: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEiLCJuYW1lIjoi6rOg7L2U7LmYIiwicHJpdmlsZWdlIjoiQ09BQ0gifQ.2eJI-xdV3-jFXBQ5FaZmqcE2qQXNCgwo-G1BUS3pEJ4`,
  profileImageUrl: 'testUrl',
  profileText: 'testText'
}
const mockAdminLoginResponse = {
  accessToken: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjAiLCJuYW1lIjoi6rSA66as7J6QIiwicHJpdmlsZWdlIjoiQURNSU4ifQ.wxFYo1hPdzVdE56oUkbbqRTxqoXOshHhK-GiI8TDQeY`,
  profileImageUrl: 'testUrl',
  profileText: 'testText'
}

export default [
  http.post('http://localhost/auth/login', async ({ request }) => {
    const newPost = await request.json()
    switch (newPost.stringId) {
      case 'coach':
        return new HttpResponse(
          JSON.stringify({
            profileImageUrl: mockCoachLoginResponse.profileImageUrl,
            profileText: mockCoachLoginResponse.profileText
          }),
          {
            headers: {
              Authorization: mockCoachLoginResponse.accessToken,
              'Content-Type': 'Application/json'
            }
          }
        )

      case 'admin':
        return new HttpResponse(
          JSON.stringify({
            profileImageUrl: mockAdminLoginResponse.profileImageUrl,
            profileText: mockAdminLoginResponse.profileText
          }),
          {
            headers: {
              Authorization: mockAdminLoginResponse.accessToken,
              'Content-Type': 'Application/json'
            }
          }
        )

      default:
        return new HttpResponse(
          JSON.stringify({
            profileImageUrl: mockLoginResponse.profileImageUrl,
            profileText: mockLoginResponse.profileText
          }),
          {
            headers: {
              Authorization: mockLoginResponse.accessToken,
              'Content-Type': 'Application/json'
            }
          }
        )
    }
  })
]
