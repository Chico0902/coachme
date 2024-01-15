export default {
  title: 'Coach API',
  apis: [
    {
      id: 'coach-1',
      spec: '3-1',
      method: 'GET',
      uri: '/coaches/categories?division1={대분류명}&division2={소분류명}',
      privilege: '0',
      description: '해당 분류 코치들의 정보를 받아온다.',
      request: {},
      response: {
        name: 'CoachesResponseDtos',
        success: {
          description: '해당 코치정보 리스트로 반환',
          code: '200',
          data: {
            dtos: 'List<CoachesResponseDtos>',
            CoachesResponseDto: {
              name: 'String',
              avgScore: 'Float',
              description: 'String',
              lessonInfo: 'String',
              lessonPrice: 'Integer'
            }
          }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'coach-2',
      spec: '3-1',
      method: 'GET',
      uri: '/coaches/profile/image/id={멤버 pk}',
      privilege: '0',
      description: '해당 코치의 프로필 사진을 받아온다.',
      request: {},
      response: {
        name: 'MultipartFile',
        success: {
          description: '해당 코치의 프로필 사진 반환',
          code: '200',
          data: {
            byte: 'Byte'
          }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },

    {
      id: 'coach-3',
      spec: '3-2',
      method: 'GET',
      uri: '/coaches/coames',
      privilege: '2',
      description: '해당 수강을 신청한 코미의 목록을 가져온다.',
      request: {
        name: 'AdminElevateRequestDtos',
        data: {
          ids: 'Array'
        }
      },
      response: {
        name: 'AdminElevateResponseDto',
        success: {
          description: '권한 변경 완료',
          code: '200',
          data: { message: 'String' }
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
