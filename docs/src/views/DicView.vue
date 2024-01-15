<script setup>
const words = [
  { name: '코치(coach)', description: '코칭하는 대상, 통상 수입을 창출하기 위해 서비스 사용' },
  {
    name: '코미(coame)',
    description: '코칭받는 대상, 재화를 전문적인 서비스나 영상 정보로 교환하기 위해 서비스 사용'
  },
  {
    name: '관리자(admin)',
    description: '관리자 페이지 접근권한을 가짐, 코미의 권한 승격이 가능하며 모든 멤버를 관리'
  },
  {
    name: '코칭(coaching)',
    description: '코치가 제공하는 화상서비스나 녹화영상 등 포괄적으로 포함'
  },
  {
    name: '카테고리(category)',
    description: '코칭의 종류, Life, Creation, Sports, Develop, Health, Etc로 구분됨.',
    nested: [
      {
        name: 'life',
        description:
          '코치의 서비스 종류로 일상에서 만날 수 있는 정보를 제공, House, Furniture, Lifestyle, Design로 구분됨.'
      },
      {
        name: 'Creations',
        description:
          '코치의 서비스 종류로 창작/예술 등 생산적인 활동과 관련된 정보 제공, Cooking, Knitting, Art, Beauti로 구분됨.'
      },
      {
        name: 'Sports',
        description: '코치의 서비스 종류로 스포츠 관련된 정보를 제공, Soccer, Basketball, Tennis, Golf로 구분됨.'
      },
      {
        name: 'Develop',
        description: '코치의 서비스 종류로 개발 관련된 정보를 제공, Frontend, Backend, Database, Devops로 구분됨.'
      },
      {
        name: 'Health',
        description: '코치의 서비스 종류로 건강 관련된 정보를 제공, Yoga, Weight, Running, Crossfit로 구분됨.'
      },
      {
        name: 'Etc',
        description: '코치의 서비스 종류로 기타 서비스들이 포함됨.'
      }
    ]
  },
  {
    name: '권한(privilege)',
    description: '사용자별 다른 권한 부여',
    nested: [
      { name: '일반사용자', description: 'lv.0' },
      { name: '코미', description: 'lv.1' },
      { name: '코치', description: 'lv.2' },
      { name: '관리자', description: 'lv.3' }
    ]
  },
  {
    name: '리스트(--list)',
    description: '코치, 코미, 코칭 등을 한번에 묶는 개념'
  },
  {
    name: '프로필(profile)',
    description: '코치나 코미가 본인을 나타내기 위한 개념, 글과 닉네임과 사진으로 구성'
  },
  {
    name: '포트폴리오(portfolio)',
    description: '코치가 본인을 어필하기 위한 소개, 이력과 쇼츠으로 구성'
  },
  { name: '영상(video) ', description: '녹화된 코칭' },
  { name: '쇼츠(shorts)', description: '코치의 1분 자기소개 어필 영상' },
  { name: '라이브(live)', description: '코치의 실시간 코칭' },
  { name: '캘린더(calendar)', description: '코치의 코칭시간 표현, 코미의 코칭시간도 나타냄' },
  { name: '요약(summary)', description: '코칭에 대한 요약' },
  { name: '소개페이지(introduce)', description: '코칭에 대한 코치의 자세한 설명' },
  { name: '공지사항(notice)', description: '코칭에 대한 공지' },
  { name: '진실의 방(proom)', description: '코칭 시 1:1호출 기능' },
  { name: '칠판(whiteboard)', description: '코칭 시 공유칠판 기능' },
  { name: '영상편집(edit)', description: '코칭 영상을 편집하는 기능' },
  { name: 'DM', description: '1:1 채팅기능' },
  { name: '찜콩(like)', description: '관심가는 코치나 코칭에 대한 표시' },
  { name: '알람(notification)', description: '코칭 시작알람, 찜콩 여부, DM 등 발생 알림' },
  { name: '리뷰(review)', description: '코칭이나 코치에 대한 평가' },
  { name: '별점(score)', description: '코칭이나 코치에 대한 평가 지표' }
]
</script>
<template>
  <div id="dic">
    <h1 class="mt-3 mb-3">용어사전(Dictionary)</h1>
    <hr />
    <!-- table -->
    <table class="table">
      <thead>
        <th scope="col">번호</th>
        <th scope="col">이름</th>
        <th scope="col">설명</th>
      </thead>
      <tbody v-for="(word, index) in words" :key="index">
        <tr>
          <td class="text-center">{{ index + 1 }}</td>
          <td>{{ word.name }}</td>
          <td>{{ word.description }}</td>
        </tr>
        <template v-if="word.nested != undefined">
          <tr v-for="(nestedWord, index2) in word.nested" :key="index2">
            <td colspan="2">
              <span class="ms-5">
                {{ nestedWord.name }}
              </span>
            </td>
            <td>
              <span class="ms-3">{{ nestedWord.description }}</span>
            </td>
          </tr>
        </template>
      </tbody>
    </table>
  </div>
</template>
