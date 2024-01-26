export default {
  title: 'Auth API',
  apis: [
    {
      id: 'auth-1',
      spec: '1-2',
      method: 'POST',
      uri: '/auth/login',
      privilege: '0',
      description: '로그인 요청 시 해당 id와 pw가 유효한지 여부를 응답한다.',
      request: {
        name: 'LoginRequestDto',
        data: { id: 'String(max : 20)', pw: 'String(max : 30)' }
      },
      response: {
        name: 'TokenResponseDto',
        success: {
          description: '해당 로그인 요청 유효함',
          code: '200',
          data: {
            accessToken: 'String[token : id, name, privilege]',
            refreshToken: 'String'
          }
        },
        fail: {
          description: '잘못된 로그인 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    }
  ]
}
