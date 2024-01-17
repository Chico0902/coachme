export default {
  title: 'Member API',
  apis: [
    {
      id: 'member-1',
      spec: '1-2',
      method: 'POST',
      uri: '/members/login',
      privilege: '0',
      description: '로그인 요청 시 해당 id와 pw가 유효한지 여부를 응답한다.',
      request: {
        name: 'LoginRequestDto',
        data: { id: 'String(max : 20)', pw: 'String(max : 30)' }
      },
      response: {
        name: 'LoginResponseDto',
        success: {
          description: '해당 로그인 요청 유효함',
          code: '200',
          data: {
            memberId: 'Long',
            privilege: 'String',
            accessToken: 'String',
            refreshToken: 'String'
          }
        },
        fail: {
          description: '잘못된 로그인 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-2',
      spec: '1-3',
      method: 'POST',
      uri: '/members',
      privilege: '0',
      description: '회원가입 요청 시 해당 정보를 DB에 저장한다.',
      request: {
        name: 'MemberRegistRequestDto',
        data: {
          id: 'String(max : 20)',
          pw: 'String(max : 30)',
          name: 'String(max : 50)',
          nick: 'String(max : 10)',
          email: 'String(max : 50)'
        }
      },
      response: {
        name: 'MemberRegistResponseDto',
        success: {
          description: '해당 회원가입 요청 유효함',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 회원가입 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-3',
      spec: '1-4',
      method: 'GET',
      uri: '/members?id={아이디}&email={email주소}',
      privilege: '0',
      description: '비밀번호 찾기 요청 시 해당 아이디와 이메일이 유효한지 확인하고, 이메일로 임시 비밀번호를 발송한다.',
      request: {},
      response: {
        name: 'FindPwResponseDto',
        success: {
          description: '비밀번호 변경 및 임시 비밀번호 발송 완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 비밀번호 찾기 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-4',
      spec: '1-5',
      method: 'POST',
      uri: '/members/privileges/elevataions',
      privilege: '1',
      description: '회원정보 권한 상승 요청 시, 유효한 요청인지 확인 후 권한 상승 목록에 추가한다.',
      request: {
        name: 'ElevationRequestDto',
        data: {
          id: 'String',
          message: 'String'
        }
      },
      response: {
        name: 'ElevationResponseDto',
        success: {
          description: '권한 상승목록에 추가 완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-5',
      spec: '1-6',
      method: 'GET',
      uri: '/members/{멤버 pk}',
      privilege: '1',
      description: '기본 회원정보 조회',
      request: {},
      response: {
        name: 'MemberInfoResponseDto',
        success: {
          description: '기본 회원정보 데이터 전송',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-6',
      spec: '1-6',
      method: 'PATCH',
      uri: '/members/{멤버 pk}',
      privilege: '1',
      description: '기본 회원정보 수정',
      request: {
        name: 'MemberInfoChangeRequestDto',
        data: {
          name: 'String',
          nick: 'String',
          email: 'String'
        }
      },
      response: {
        name: 'MemberInfoChangeResponseDto',
        success: {
          description: '기본 회원정보 수정 완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-7',
      spec: '1-7',
      method: 'GET',
      uri: '/members/profiles/texts/{멤버 pk}',
      privilege: '1',
      description: '코미나 코치의 프로필 글에 대한 조회결과를 전송한다.',
      request: {},
      response: {
        name: 'ProfileTextResponseDto',
        success: {
          description: '프로필 글 조회결과 전송',
          code: '200',
          data: {
            profileText: 'String'
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
      id: 'member-8',
      spec: '1-7',
      method: 'GET',
      uri: '/members/profiles/images/{멤버 pk}',
      privilege: '1',
      description: '코미나 코치의 프로필 사진에 대한 조회결과를 전송한다.',
      request: {},
      response: {
        name: 'ProfileImageResponseDto',
        success: {
          description: '프로필 사진 조회결과 전송',
          code: '200',
          data: {
            profileImageUrl: 'String'
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
      id: 'member-9',
      spec: '1-7',
      method: 'POST',
      uri: '/members/profiles/texts/{멤버 pk}',
      privilege: '1',
      description: '코치나 코미가 프로필 글을 등록한다.',
      request: {
        name: 'ProfileTextRegistRequestDto',
        data: {
          profileText: 'String'
        }
      },
      response: {
        name: 'ProfileTextRegistResponseDto',
        success: {
          description: '프로필 글 등록 완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-10',
      spec: '1-7',
      method: 'POST',
      uri: '/members/profiles/images/{멤버 pk}',
      privilege: '1',
      description: '코치나 코미가 프로필 사진을 등록한다.',
      request: {
        name: 'ProfileImageRegistRequestDto',
        data: { type: 'FormData' }
      },
      response: {
        name: 'ProfileImageRegistResponseDto',
        success: {
          description: '프로필 사진 등록 완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-11',
      spec: '1-7',
      method: 'PATCH',
      uri: '/members/profiles/texts/{멤버 pk}',
      privilege: '1',
      description: '코치나 코미가 프로필 글을 수정한다.',
      request: {
        name: 'ProfileTextModifyRequestDto',
        data: {
          profileText: 'String'
        }
      },
      response: {
        name: 'ProfileTextModifyResponseDto',
        success: {
          description: '프로필 글 수정 완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-12',
      spec: '1-7',
      method: 'PATCH',
      uri: '/members/profiles/images/{멤버 pk}',
      privilege: '1',
      description: '코치나 코미가 프로필 사진을 수정한다. 기존 프로필 사진은 삭제한다.',
      request: {
        name: 'ProfileImageModifyRequestDto',
        data: { type: 'FormData' }
      },
      response: {
        name: 'ProfileImageModifyResponseDto',
        success: {
          description: '프로필 사진 수정 및 기존파일 삭제 완료',
          code: '200',
          data: { message: 'String' }
        },
        fail: {
          description: '잘못된 요청',
          code: '403',
          data: { message: 'String' }
        }
      }
    },
    {
      id: 'member-13',
      spec: '1-7',
      method: 'DELETE',
      uri: '/members/profiles/texts/{멤버 pk}',
      privilege: '1',
      description: '코치나 코미가 프로필 글을 삭제한다.',
      request: {},
      response: {
        name: 'ProfileTextDeleteResponseDto',
        success: {
          description: '프로필 글 삭제 완료',
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
