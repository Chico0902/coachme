<script setup>
const sprints = [
  {
    title: '스프린트 계획',
    details: [
      '해당 스프린트 목표 정하기',
      '백로그에 스프린트에 포함할 이슈(에픽, 스토리) 정하기',
      '선정된 이슈 세분화(하위태스크 선정), 작업수행 방법 및 순서 선정',
      '작업 소요시간 산정 및 하위태스크 분배'
    ]
  },
  {
    title: '개발',
    details: ['코딩 : 본인에게 할당된 작업 직접 구현', '테스트 : 구현한 작업이 실제로 동작하는지 테스트']
  },
  {
    title: '스크럼',
    details: [
      '시간 : 컨설턴트 미팅 종료 후 5분 이내',
      '진행사항 : 전날까지 본인의 작업이 얼마나 달성되었는지 공유',
      '일일목표 : 오늘 작업목표 지정, 프로젝트 진행상태와 작업시간 등 고려하여 필요시 할일 추가 분배',
      '기타 : 스프린트에 추가로 영향을 미칠 사항이 있으면 공유'
    ]
  },
  {
    title: '검토 및 피드백',
    details: [
      '시간 : 매일 17:00 ~ 18:00',
      'MR(Merge Request) 검토 : 현재까지 발생한 MR 검토 및 승인',
      '코드리뷰 : MR 코드에 대해 각 팀원 상호 리뷰(프론트 / 백 분리하며 필요시 참여 요청)',
      'MR이 없는 날은 생략하며, MR 종료 시 작업 마무리'
    ]
  },
  {
    title: '회고 및 스프린트 계획',
    details: [
      '시간 : 매주 금요일 15:00 ~ 18:00',
      '완성본에 대한 리뷰',
      '스프린트 동작 방식에 대한 건의 및 다음 스프린트 개선사항 결정',
      '다음주 스프린트 계획'
    ]
  }
]
const jiras = [
  {
    title: '에픽(Epic)',
    details: [
      '우리팀의 최종 목표를 달성하기 위해 반드시 달성해야 할 하위 목표',
      '스토리로 나눌 수 있는 작업 단위',
      '1개 이상의 스프린트를 통해 구현됨',
      '예시 : 계정(member) 관리 기능'
    ]
  },
  {
    title: '스토리(Story)',
    details: [
      '에픽을 달성하기 위한 하위 작업 단위',
      '해당 작업은 사용자 입장에서 작성되어야 함',
      '한 주의 스프린트 이내에 끝낼 수 있는 수준의 작업 단위 선정',
      '예시 : 코미(coame)는 기본 회원정보를 조회 및 수정할 수 있어야 한다.'
    ]
  },
  {
    title: '태스크(Task)',
    details: [
      '에픽을 달성하기 위한 하위 작업 단위',
      '사용자에게 노출되지 않는 개발자의 작업을 표현',
      '한 주의 스프린트 이내에 끝낼 수 있는 수준의 작업 단위 선정',
      '예시 : 서버 관리, CI/CD 및 파이프라인 구축 등의 작업'
    ]
  },
  {
    title: '버그(Bug)',
    details: [
      '진행중이거나 완료된 스토리/태스크에서 중대한 문제가 발생해 향후 반드시 수정해야 하는 이슈',
      '한 주의 스프린트 이내에 끝낼 수 있는 수준의 작업 단위 선정',
      '예시 : 코칭 리스트 조회 시 트래픽 과다로 서버 다운'
    ]
  },
  {
    title: '하위 태스크(Sub Task)',
    details: [
      '스토리와 태스크, 버그를 구현하기 위해 수행해야 할 작업',
      '상위 이슈의 중요도와 소요시간 등을 고려하여 생성 및 분배',
      '1명의 팀원에게 해당 작업 할당',
      '예시 : (F/B)회원정보 API 구현'
    ]
  }
]
</script>
<template>
  <div id="agile">
    <h1 class="mt-3 mb-3">애자일 규칙(Agile Rules)</h1>
    <hr />
    <!-- 스프린트 -->
    <h2 class="mt-3 mb-3">스프린트(Sprint)</h2>
    <ol>
      <template v-for="(sprint, index) in sprints" :key="index">
        <h4 class="ps-3">
          <li>{{ sprint.title }}</li>
        </h4>
        <ul class="mb-3">
          <template v-for="(detail, index2) in sprint.details" :key="index2">
            <li class="p-2">{{ detail }}</li>
          </template>
        </ul>
      </template>
    </ol>
    <hr />

    <!-- 지라 컨벤션 -->
    <h2 class="mt-3 mb-3">지라 규칙(Jira Convention)</h2>
    <ol>
      <template v-for="(jira, index) in jiras" :key="index">
        <h4>
          <li>{{ jira.title }}</li>
        </h4>
        <ul class="mb-3">
          <template v-for="(detail, index2) in jira.details" :key="index2">
            <li class="p-2">{{ detail }}</li>
          </template>
        </ul>
      </template>
    </ol>
  </div>
</template>
