export default {
  title: 'Coaching API',
  apis: [
    {
      id: 'coaching-1',
      spec: '5-1',
      method: 'GET',
      uri: '/coachings',
      privilege: '3',
      description: '모든 코칭들의 정보를 받아온다.',
      request: {},
      response: {
        name: 'CoachingsResponseDtos',
        success: {
          description: '코칭정보 리스트로 반환',
          code: '200',
          data: {
            dtos: 'List<CoachingsResponseDto>',
            CoachingsResponseDtos: {
              title: 'String',
              avgScore: 'Float',
              likes: 'Integer',
              Coamelimit: 'Integer',
              currentCoame: 'Integer'
            }
          }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    }
  ]
}
