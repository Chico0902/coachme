import { HttpResponse, http } from 'msw'

const mockLoginResponse = {
  accessToken: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEyMyIsIm5hbWUiOiLqs6DslpHsnbQiLCJwcml2aWxlZ2UiOiJDT0FNRSJ9.CJQRuGr5AxmK7Rva0ner_84rHjPc9baBJ3KwD630xRs`,
  profileText: '테스트 프로필입니다. 사진은 기본사진입니다.',
  profileImageUrl: 'https://ssafycoachme.s3.ap-northeast-2.amazonaws.com/04f44743-301b-4bbb-aece-04815e662b60'
}
const mockCoachLoginResponse = {
  accessToken: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEiLCJuYW1lIjoi6rOg7L2U7LmYIiwicHJpdmlsZWdlIjoiQ09BQ0gifQ.2eJI-xdV3-jFXBQ5FaZmqcE2qQXNCgwo-G1BUS3pEJ4`,
  profileText: '테스트 프로필입니다. 사진은 기본사진입니다.',
  profileImageUrl: 'https://ssafycoachme.s3.ap-northeast-2.amazonaws.com/04f44743-301b-4bbb-aece-04815e662b60'
}
const mockAdminLoginResponse = {
  accessToken: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjAiLCJuYW1lIjoi6rSA66as7J6QIiwicHJpdmlsZWdlIjoiQURNSU4ifQ.wxFYo1hPdzVdE56oUkbbqRTxqoXOshHhK-GiI8TDQeY`,
  profileText: '테스트 프로필입니다. 사진은 기본사진입니다.',
  profileImageUrl: 'https://ssafycoachme.s3.ap-northeast-2.amazonaws.com/04f44743-301b-4bbb-aece-04815e662b60'
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
