<script setup>
import SideIndex from '../components/SideIndex.vue'
const FILESERVER_IP = '70.12.246.246'
const FILESERVER_PORT = 1235
const indexes = [
  { id: 'ssh', title: 'SSH접속' },
  { id: 'directory', title: '디렉토리 구조' },
  { id: 'jenkins', title: 'Jenkins 설정' }
]

const sshDocument = [
  {
    title: 'PuTTY, PuTTYGEN 다운로드',
    details: [
      '인터넷 익스플로어에서 PuTTY, PuTTYGEN 최신버전을 다운로드 받습니다.',
      '아래 링크로도 다운로드 받을 수 있습니다.'
    ],
    link: `http://${FILESERVER_IP}:${FILESERVER_PORT}/uploads/files/putty.zip`
  },
  {
    title: '인증용 개인키 다운로드',
    details: ['아래 링크로 개인키를 다운로드 받습니다.', '* 해당 키는 외부유출 금지입니다!!'],
    link: `http://${FILESERVER_IP}:${FILESERVER_PORT}/uploads/files/i10A403T.pem`
  },
  {
    title: 'PuTTY GEN 실행',
    imgNo: '1',
    details: ['PuTTY GEN을 실행합니다. ', 'Load를 클릭하여 위에서 저장한 개인키를 선택합니다.']
  },
  {
    title: '개인키 선택',
    imgNo: '2',
    details: ['해당 키가 보이지 않으면 파일명 분류를 All Files(*.*) 으로 변경하시면 보입니다.']
  },
  {
    title: '인증키 생성창 확인',
    imgNo: '3',
    details: ['인증키가 성공적으로 생성된 것을 확인합니다.']
  },
  {
    title: 'SSH키 저장 - 1',
    imgNo: '4',
    details: ['해당 파일을 안전한 경로에 저장합니다(Save Private Key).']
  },
  {
    title: 'SSH키 저장 - 2',
    imgNo: '5',
    details: [
      '개인키 비밀번호는 설정하지 않고 다운로드 받습니다.',
      '비밀번호 설정 시 차후 접속시마다 해당 비밀번호를 사용해야 합니다.'
    ]
  },
  {
    title: 'SSH키 저장 - 3',
    imgNo: '6',
    details: ['식별 가능한 이름으로 설정해서 저장합니다(i10A403T.ppk).', '개인키와 동일한 이름을 사용해도 무방합니다.']
  },
  {
    title: 'PuTTY 실행',
    imgNo: '7',
    details: [
      'PuTTY실행 후 다음과 같이 설정합니다.',
      'Host Name : ubuntu@i10a403.p.ssafy.io',
      'Port : 22',
      'Connection type : SSH'
    ]
  },
  {
    title: 'Category 설정 - 1',
    imgNo: '8',
    details: [
      'Category > Connection > SSH > Auth > Credential 로 이동합니다.',
      `위 사진에서처럼 Browse를 통해 키를 선택합니다.`
    ]
  },
  {
    title: 'Category 설정 - 2',
    imgNo: '9',
    details: ['저장한 SSH 키를 사용합니다.']
  },
  {
    title: 'Category 설정 - 3',
    imgNo: '10',
    details: [
      '위와 같이 Session에 해당 설정파일을 저장합니다.',
      'Saved Session : 저장하고 싶은 Session 설정명(아무거나 입력)',
      'Save : Session 설정 저장',
      'Open : 해당 설정으로 PuTTY 실행',
      '설정파일을 저장하지 않으면 PuTTY실행 시마다 위 과정을 반복해야 합니다.'
    ]
  },
  {
    title: '접속창 확인',
    imgNo: '11',
    details: ['설정이 정상적으로 되었다면 다음과 같은 접속창을 확인할 수 있습니다.']
  }
]
</script>
<template>
  <div id="git">
    <h1 class="mt-3 mb-3">Server</h1>
    <SideIndex :indexes="indexes" />
    <hr />

    <h2>Index</h2>
    <ol>
      <h4>
        <li class="p-2">
          <a href="#ssh">SSH 접속방법</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#directory">디렉토리 구조</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#jenkins">Jenkin 설정</a>
        </li>
      </h4>
    </ol>
    <hr />

    <!-- SSH 접속방법 -->
    <h2 id="ssh" class="pb-3">SSH 접속방법</h2>
    <ol>
      <template v-for="(ssh, index) in sshDocument" :key="index">
        <h4 class="ms-3">
          <li>{{ ssh.title }}</li>
        </h4>
        <img v-if="ssh.imgNo != undefined" class="m-3 border" :src="`/src/assets/img/server/ssh/${ssh.imgNo}.png`" />
        <h6>
          <ul>
            <template v-for="(detail, index2) in ssh.details" :key="index2">
              <li class="p-2">{{ detail }}</li>
            </template>
            <a v-if="ssh.link != undefined" class="p-2" :href="`${ssh.link}`">링크</a>
          </ul>
        </h6>
      </template>
    </ol>
    <hr />

    <!-- 디렉토리 구조 -->
    <h2 id="ssh" class="pb-3">디렉토리 구조</h2>
    <hr />

    <!-- Jenkin 설정 -->
    <h2 id="ssh" class="pb-3">Jenkin 설정</h2>
    <hr />
  </div>
</template>
<style scoped>
a {
  text-decoration: none;
}
img {
  width: 90%;
}
#git {
  width: 75%;
}
</style>
