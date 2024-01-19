<script setup>
import SideIndex from '../components/SideIndex.vue'
const indexes = [
  { id: 'spec-1', title: '계정 관리 기능' },
  { id: 'spec-2', title: '관리자 기능' },
  { id: 'spec-3', title: '코치 정보 관리기능' },
  { id: 'spec-4', title: '채팅(DM) 기능' },
  { id: 'spec-5', title: '코칭 기능' },
  { id: 'spec-6', title: '라이브 코칭 기능' },
  { id: 'spec-7', title: '영상 코칭 기능' },
  { id: 'spec-8', title: '찜콩 기능' },
  { id: 'spec-9', title: '리뷰 관리 기능' },
  { id: 'spec-10', title: '수익 관리 기능' }
]
const specs = [
  {
    title: '계정(member) 관리 기능',
    stories: [
      { category: '명세 필수', description: '방문자(visitor)는 메인페이지를 볼 수 있어야 한다.' },
      { category: '명세 필수', description: '방문자(visitor)는 로그인 할 수 있어야 한다.' },
      { category: '명세 필수', description: '방문자(visitor)는 회원가입할 수 있어야 한다.' },
      { category: '명세 필수', description: '방문자(visitor)는 비밀번호를 찾을 수 있어야 한다.' },
      {
        category: '핵심 부가',
        description: '코미(coame)는 코치(coach)로 권한(privilege)상승을 요청할 수 있어야 한다.'
      },
      {
        category: '핵심 부가',
        description: '코미(coame)는 기본 회원정보를 조회 및 수정할 수 있어야 한다.'
      },
      {
        category: '핵심 부가',
        description: '코미(coame)는 프로필(profile)을 조회, 수정, 삭제할 수 있어야 한다.'
      }
    ]
  },
  {
    title: '관리자(admin) 기능',
    stories: [
      {
        category: '핵심 부가',
        description: '관리자(admin)는 사용자 리스트(list)를 조회할 수 있어야 한다.'
      },
      {
        category: '핵심 부가',
        description: '관리자(admin)는 권한(privilege) 상승리스트(list)를 확인할 수 있어야 한다.'
      },
      {
        category: '핵심 부가',
        description: '관리자(admin)는 권한(privilege)을 변경할 수 있어야 한다.'
      }
    ]
  },
  {
    title: '코치(coach) 정보 관리기능',
    stories: [
      {
        category: '핵심 부가',
        description: '코미(coame)는 코치(coach) 리스트(list)를 확인해서 원하는 코치(coach)를 선택할 수 있어야 한다.'
      },
      {
        category: '핵심 부가',
        description: '코치(coach)는 포트폴리오(portfolio)를 작성할 수 있어야 한다.'
      },
      {
        category: '핵심 부가',
        description: '코치(coach)는 소개영상(video)을 관리할 수 있어야 한다.'
      }
    ]
  },
  {
    title: '채팅(DM) 기능',
    stories: [
      {
        category: '명세 필수',
        description: '코미(coame)는 코치(coach)에게 DM을 보낼 수 있어야 한다.'
      },
      {
        category: '명세 필수',
        description: '코치(coach)와 코미(coame)는 DM 리스트(list)를 조회 및 관리할 수 있어야 한다.'
      }
    ]
  },
  {
    title: '코칭(coaching) 기능',
    stories: [
      {
        category: '명세 필수',
        description: '코치(coach)는 코칭(coaching)을 개설할 수 있어야 한다.'
      },
      {
        category: '명세 필수',
        description: '코치(coach)는 코칭(coaching)을 조회 및 수정할 수 있어야 한다.'
      },
      {
        category: '명세 필수',
        description:
          '코미(coame)는 코칭(coaching) 리스트(list)를 확인해서 원하는 코칭(coaching)을 선택할 수 있어야 한다.'
      },
      {
        category: '명세 필수',
        description:
          '코치(coach)는 해당 코칭(coaching)을 신청한 코미(coame) 리스트(list)를 확인 및 관리할 수 있어야 한다.'
      },
      {
        category: '명세 필수',
        description: '코미(coame)는 마이페이지에서 계획된 코칭(coaching) 리스트(list)를 볼 수 있어야 한다.'
      }
    ]
  },
  {
    title: '라이브(live) 코칭(coaching) 기능',
    stories: [
      {
        category: '명세 필수',
        description: '코치(coach)는 라이브(live) 코칭(coaching)을 개설할 수 있어야 한다.'
      },
      {
        category: '명세 필수',
        description: '코미(coame)는 라이브(live) 코칭(coaching)에 참가할 수 있어야 한다.'
      },
      {
        category: '명세 필수',
        description: '코미(coame)는 라이브(live) 채팅 시 코치(coach)와 화상채팅을 할 수 있어야 한다.'
      },
      {
        category: '핵심 부가',
        description: '코치(coach)는 코칭(coaching)을 녹화해서 영상(video)으로 만들 수 있어야 한다.'
      },
      {
        category: '기타 부가',
        description:
          '코치(coach)는 코칭(coaching)시 특정 코미(coame)를 선택해서 진실의 방(proom)에 초대할 수 있어야 한다.'
      },
      {
        category: '기타 부가',
        description: '코치(coach)와 코미(coame)는 칠판(whiteboard)을 사용할 수 있어야 한다.'
      }
    ]
  },
  {
    title: '영상(video) 코칭(coaching) 기능',
    stories: [
      {
        category: '기타 부가',
        description: '코치(coach)는 코칭(coaching) 영상(video)을 등록, 삭제할 수 있어야 한다.'
      },
      {
        category: '기타 부가',
        description: '코치(coach)는 코칭(coaching) 영상(video)을 편집(edit)할 수 있어야 한다.'
      },
      {
        category: '기타 부가',
        description: '코치(coach)는 코칭(coaching) 영상(video)을 AI를 통해 빠르게 편집(edit)할 수 있어야 한다.'
      },
      {
        category: '기타 부가',
        description: '코치(coach)는 코미(coame)에게 영상(video)을 공유할 수 있어야 한다.'
      },
      {
        category: '기타 부가',
        description: '코미(coame)는 공유된 코칭(coaching) 영상(video)을 조회할 수 있어야 한다.'
      }
    ]
  },
  {
    title: '찜콩(like) 기능',
    stories: [
      {
        category: '핵심 부가',
        description: '코미(coame)는 원하는 코치(coach)나 코칭(coaching)을 찜콩(like)할 수 있어야 한다.'
      },
      {
        category: '핵심 부가',
        description: '코미(coame)는 마이페이지에서 찜콩(like)한 수강리스트(list)를 확인할 수 있어야 한다.'
      },
      {
        category: '핵심 부가',
        description: '찜콩(like) 시 해당 코치(coach)에게 알람(notification)이 발생해야 한다.'
      }
    ]
  },
  {
    title: '리뷰(review) 관리 기능',
    stories: [
      {
        category: '핵심 부가',
        description: ' 코미(coame)는 코치(coach)나 코칭(coame)의 글과 별점(score)를 남길 수 있어야 한다.'
      },
      {
        category: '핵심 부가',
        description: '코미(coame)는 본인의 리뷰(review)와 별점(score)을 관리할 수 있어야 한다.'
      },
      {
        category: '핵심 부가',
        description: '코치(coach)는 리뷰(review)를 조회할 수 있어야 한다.'
      }
    ]
  },
  {
    title: '수익 관리 기능',
    stories: [
      {
        category: '기타 부가',
        description: '코미(coame)는 결제내역을 확인할 수 있어야 한다.'
      },
      {
        category: '기타 부가',
        description: '코치(coach)는 현재까지의 수익을 확인할 수 있어야 한다.'
      }
    ]
  }
]
</script>
<template>
  <div id="spec">
    <h1 class="mt-3 mb-3">명세서(Specification)</h1>
    <SideIndex :indexes="indexes" />
    <hr />
    <!-- index -->
    <h2>Index</h2>
    <ol>
      <h4>
        <li class="p-2">
          <a href="#spec-1">계정(member) 관리 기능</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-2">관리자(admin) 기능</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-3">코치(coach) 정보 관리기능</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-4">채팅(DM) 기능</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-5">코칭(coaching) 기능</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-6">라이브(live) 코칭(coaching) 기능</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-7">영상(video) 코칭(coaching) 기능</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-8">찜콩(like) 기능</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-9">리뷰(review) 관리 기능</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#spec-10">수익 관리 기능</a>
        </li>
      </h4>
    </ol>
    <hr />
    <!-- table -->
    <table class="table">
      <template v-for="(spec, index) in specs" :key="index">
        <thead>
          <tr>
            <td colspan="4">
              <h4 :id="`spec-${index + 1}`">{{ index + 1 }}. {{ spec.title }}</h4>
            </td>
          </tr>
          <tr>
            <th scope="col">번호</th>
            <th scope="col">분류</th>
            <th scope="col">스토리</th>
          </tr>
        </thead>
        <tbody v-for="(story, index2) in spec.stories" :key="index2">
          <tr>
            <td class="text-center" :id="`spec-${index + 1}-${index2 + 1}`">{{ index2 + 1 }}</td>
            <td>{{ story.category }}</td>
            <td>{{ story.description }}</td>
          </tr>
        </tbody>
        <td>&nbsp;</td>
      </template>
    </table>
  </div>
</template>
<style scoped>
#spec {
  width: 75%;
}
a {
  text-decoration: none;
}
</style>
