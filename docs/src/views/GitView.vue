<script setup>
import SideIndex from '../components/SideIndex.vue'
const indexes = [
  { id: 'git-flow', title: 'Git Flow' },
  { id: 'mr-flow', title: 'MR Flow' },
  { id: 'ex', title: 'Git Examples' },
  { id: 'cheat', title: 'Git Cheat Sheet' }
]
const getCopy = (id) => {
  const r = document.createRange()
  r.selectNode(document.getElementById(id))
  window.getSelection().removeAllRanges()
  window.getSelection().addRange(r)
  document.execCommand('copy')
  window.getSelection().removeAllRanges()
  alert('복사 완료')
}

const gitFlows = [
  {
    title: '지라(Jira)에서 이슈번호 복사하기',
    imgNo: '1',
    details: [
      '작업할 지라(Jira) 스토리에 들어갑니다.',
      '해당 이슈의 브랜치 만들기 버튼을 누른 후 새 브랜치를 만들기 위한 명령어를 복사합니다.',
      '참고) 위 예시에서는 하위태스크로 브랜치를 생성했지만, 스토리로 브랜치를 생성하는 것으로 변경되었습니다.'
    ]
  },
  {
    title: '이슈번호 깃에 붙여넣기',
    imgNo: '2',
    details: [
      '복사한 이슈번호를 깃 터미널(Git CLI)에 붙여넣습니다.',
      '이때, 반드시 해당 이슈번호 뒤에 본인의 작업에 대한 정보를 입력합니다.',
      '/feat : 기능 추가, /release : 버전 관리, /hotfix : 긴급 수정',
      '추가) 해당 브랜치 뒤에는 본인 식별자를 추가합니다.[/1(김동현), /2(김지원), /3(남상엽), /4(박민아), /5(이준학), /6(이혜지)]',
      'ex) 남상엽이 새로운 기능 브랜치를 생성할경우 : <issue번호>/feat/3'
    ]
  },
  {
    title: '작업 진행하기',
    imgNo: '3',
    details: [
      '해당 이슈에 대해 작업을 진행합니다.',
      '예시에서는 작성한 화면설계서를 캡쳐해서 저장한 후 README.md에 추가하는 상황을 가정했습니다.'
    ]
  },
  {
    title: '깃 스테이징',
    imgNo: '4',
    details: [
      '현재 깃의 변경내역을 확인합니다.[git status]',
      '변경이 필요한 부분을 스테이징합니다.[git add <변경내역>]',
      '변경은 파일, 디렉토리 단위로 가능하며, 전체 변경이 필요할 경우, 모두 등록합니다.[git add .]'
    ]
  },
  {
    title: '커밋 메시지 복사',
    imgNo: '5',
    details: [
      '지라(Jira)에서 해당 이슈번호에 대한 커밋 메시지를 복사합니다.',
      '단, 추적이 필요하지 않은 단순 작업은 이슈번호와 연결하지 않아도 무방합니다.'
    ]
  },
  {
    title: '커밋 메시지 작성',
    imgNo: '6',
    details: [
      '복사한 커밋 메시지를 붙여넣습니다.',
      '지라(Jira)에서 복사한 메시지의 <messages> 란에 추가로 작업에 대한 커밋 메시지를 작성합니다.',
      '실수로 잘못 메시지를 작성했다면, [git commit --amend] 명령어로 가장 최근의 메시지를 수정할 수 있습니다.',
      '필요시, 하단 링크를 통해 우리팀의 Git 복사 메시지를 참조할 수 있습니다.'
    ],
    link: 'ex'
  },
  {
    title: '리모트 브랜치에 커밋 저장(push)',
    imgNo: '7',
    details: [
      '완료된 커밋을 리모트 브랜치에 저장합니다.[git push -u origin <현재 브랜치명>]',
      `이때, 최초 커밋이면 리모트 브랜치에 동일한 브랜치가 생성되며, '-u' 옵션을 지정하여 upstream을 새로운 브랜치에 연결할 수 있습니다.`,
      '현재 브랜치명은 [git branch] 명령어를 통해 확인할 수 있습니다.'
    ]
  },
  {
    title: '리모트 브랜치 커밋 확인',
    imgNo: '8',
    details: [
      '완료된 커밋이 정상적으로 리모트 브랜치에 반영되었는지 확인합니다.',
      '이때, 최초 커밋이면 리모트 브랜치에 동일한 이름의 브랜치가 생성되었음을 확인합니다.'
    ]
  }
]

const mergeFlows = [
  {
    title: 'MR(Merge Request) 생성하기',
    imgNo: '9',
    details: [
      '모든 작업이 종료되었고, 커밋이 완료되었으면 Merge Request를 생성합니다.',
      '깃랩(GitLab) 프로젝트 초기화면 상단에 Merge Request 생성 버튼을 클릭합니다.'
    ]
  },
  {
    title: 'MR(Merge Request) 작성하기',
    imgNo: '10',
    details: [
      '병합하기 위한 branch를 선택합니다(통상 develop이나 예시에서는 임시 branch를 사용했습니다.).',
      '제목은 Issue 추적을 용이하게 하기 위해 이슈번호를 명시합니다.',
      '내용에는 해당 브랜치에 대한 설명과 함께 Merge Request가 필요한 이유를 작성합니다.',
      'Assignee에는 MR 담당자(팀장)를 선택하며, Reviewer에는 코드리뷰를 받기 위한 팀원(본인을 제외한 프론트 / 백 팀원)을 입력합니다.'
    ]
  },
  {
    title: 'MR(Merge Request) 옵션 선택하기',
    imgNo: '11',
    details: [
      `MR시 서버에 본인이 작성한 브랜치를 삭제하려면 'Delete source branch ~'를 선택합니다.`,
      `MR시 본인의 커밋 기록들을 압축(squash)하려면 'Squash commits ~'를 선택합니다.`,
      '팀 규칙상 커밋 기록을 최대 5개까지 남길 수 있으며, 본인이 커밋 기록을 정리한 것이 아니라면 해당 옵션을 사용합니다.',
      '여기까지 진행하면 MR은 완료되었으며, 아래부터는 코드리뷰 및 Merge 과정에 대한 설명입니다.'
    ]
  },
  {
    title: 'MR(Merge Request) 조회하기',
    imgNo: '12',
    details: [
      `Approval은 해당 Merge가 타당하고, 코드 품질이 만족스러우면 해당 Merge를 승인할 수 있는 기능입니다.`,
      `Resolve Conflicts 메시지와 버튼이 있으면 새로운 브랜치와 원본 브랜치를 병합하는 과정에서 충돌이 발생한 경우이며, 이를 해소해야 Merge가 가능합니다.`,
      'Preview는 해당 MR에 대한 리뷰를 남길 수 있습니다.'
    ]
  },
  {
    title: 'MR(Merge Request) 승인하기(Approval)',
    imgNo: '13',
    details: [
      'Reviewer로 등록된 사람은 MR을 승인할 수 있습니다.',
      `Approval버튼을 누르면 해당 MR을 승인할 수 있으며, 해당 버튼은 승인해제 버튼으로 변경됩니다.`,
      `MR을 승인하면 우측 사이드바에 본인의 계정에 체크표시가 생성됩니다.`
    ]
  },
  {
    title: 'MR(Merge Request) 충돌 해결하기(Resolve Conflict)',
    imgNo: '14',
    details: [
      `Resolve Conflicts 버튼을 누르면 충돌이 발생한 파일의 충돌 지점이 표시됩니다.`,
      `두 파일 중 원하는 파일의 버튼(Use ours / Use theirs)을 누르면 해당 파일의 코드로 충돌을 해결합니다.`,
      `모든 파일의 충돌을 해결한 후 커밋 메시지를 작성합니다.`,
      `참고) 충돌이 너무 많이 발생할 경우, 깃랩 자체에서 충돌 해결이 불가능하며(Resolve Conflicts 비활성화) 이때는 직접 충돌을 해소한 후 다시 MR을 수행해야 합니다.`
    ]
  },
  {
    title: 'MR(Merge Request) 리뷰하기(Preview)',
    imgNo: '15',
    details: [
      `코드리뷰를 작성한 후, preview에 남길 수 있습니다.`,
      `MR 요청자가 코드리뷰를 명확히 인지할 수 있도록 해당 코드의 위치를 표기하고, 본인의 의견을 코드예시와 함께 작성하면 좋습니다.`
    ]
  },
  {
    title: '병합(Merge)하기',
    imgNo: '16',
    details: [
      `승인, 충돌해결, 코드리뷰가 모두 완료되면 병합을 수행합니다.`,
      `해당 기능은 Assignee(팀장)가 수행할 수 있습니다.`,
      `병합이 완료되면 해당 커밋이 기록됩니다.`
    ]
  },
  {
    title: '삭제된 브랜치 확인',
    imgNo: '17',
    details: [
      `병합이 완료되면 작업중인 원격 브랜치는 삭제됩니다.(삭제옵션 선택 시)`,
      '로컬 브랜치는 이러한 작업 과정에서 삭제되지 않으니, 필요시 사용하거나 직접 삭제하시면 됩니다.[git checkout -d <브랜치명>]'
    ]
  },
  {
    title: '커밋 히스토리 확인',
    imgNo: '18',
    details: [
      `병합이 완료되면 해당 커밋이 히스토리에 기록됩니다.`,
      '명령어 [git log --graph --decorate --oneline]를 사용하여 커밋 히스토리를 직접 조회할 수 있습니다.',
      '위 사진에서 커밋 히스토리의 가장 위에 merge 커밋이 추가된 것을 확인할 수 있습니다.'
    ]
  }
]

const gitEx = [
  {
    head: 'feat : 새로운 기능 추가',
    message1: 'feat: 회원가입 시 이메일 인증 기능 추가',
    message2:
      '새로운 사용자가 등록할 때, 이메일을 통한 인증 단계를 추가함. 이제 사용자는 등록 후 이메일 링크를 클릭하여 계정을 활성화할 수 있음.'
  },
  {
    head: 'fix : 버그 수정',
    message1: 'fix: 시작 시 응용 프로그램 충돌 버그 해결',
    message2:
      '응용 프로그램이 시작될 때 발생하는 특정 상황에서 발생하는 충돌 버그를 해결. 이제 응용 프로그램은 정상적으로 시작됨.'
  },
  {
    head: 'docs : 문서 수정',
    message1: 'docs: 새로운 API 엔드포인트에 대한 상세 문서 업데이트',
    message2:
      '새로운 API 엔드포인트의 기능, 파라미터 및 응답에 대한 상세한 문서를 추가. 개발자들은 이를 통해 새로운 기능을 더 쉽게 적용할 수 있음.'
  },
  {
    head: 'style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우',
    message1: 'style: main.js 파일의 코드 스타일 일관성 수정',
    message2: 'main.js 파일의 코드 스타일을 Prettier를 사용하여 일관성 있게 수정. 들여쓰기 및 세미콜론이 올바르게 적용.'
  },
  {
    head: 'refactor : 코드 리펙토링',
    message1: 'refactor: 사용자 유효성 검사를 위한 코드 리펙토링',
    message2: '사용자 유효성을 검사하는 부분을 단순화하고 중복 코드를 제거하여 유지보수성을 향상시킴.'
  },
  {
    head: 'test : 테스트 코드, 리펙토링 테스트 코드 추가',
    message1: 'test: 사용자 인증에 대한 새로운 통합 테스트 추가',
    message2: '보안 강화 및 안정성 향상을 위해 사용자 인증 부분에 대한 새로운 통합 테스트를 추가.'
  },
  {
    head: 'chore : 빌드 업무 수정, 패키지 매니저 수정',
    message1: 'chore: 프로덕션 배포를 위한 빌드 스크립트 업데이트',
    message2: '프로덕션 배포 시 필요한 빌드 스크립트를 최적화하여 배포 프로세스를 더욱 신속하고 안정적으로 만듦.'
  }
]
</script>
<template>
  <div id="git">
    <h1 class="mt-3 mb-3">Git</h1>
    <SideIndex :indexes="indexes" />
    <hr />

    <h2>Index</h2>
    <ol>
      <h4>
        <li class="p-2">
          <a href="#git-flow">깃(Git) 작업 진행순서</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#mr-flow">MR(Merge Request) 진행순서</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#ex">커밋(Commit) 예시</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#cheat">깃(Git) 가이드</a>
        </li>
      </h4>
    </ol>
    <hr />

    <!-- 깃 진행순서 -->
    <h2 id="git-flow" class="pb-3">Git Flow</h2>
    <ol>
      <template v-for="(flow, index) in gitFlows" :key="index">
        <h4 class="ms-3">
          <li>{{ flow.title }}</li>
        </h4>
        <img class="m-3 border" :src="`/src/assets/img/git/flow/${flow.imgNo}.png`" />
        <h6>
          <ul>
            <template v-for="(detail, index2) in flow.details" :key="index2">
              <li class="p-2">{{ detail }}</li>
            </template>
            <a v-if="flow.link != undefined" class="p-2" :href="`#${flow.link}`">링크</a>
          </ul>
        </h6>
      </template>
    </ol>
    <hr />

    <!-- MR(Merge Request) 진행순서 -->
    <h2 id="mr-flow" class="pb-3">MR Flow</h2>
    <ol>
      <template v-for="(flow, index) in mergeFlows" :key="index">
        <h4 class="ms-3">
          <li>{{ flow.title }}</li>
        </h4>
        <img class="m-3 border" :src="`/src/assets/img/git/flow/${flow.imgNo}.png`" />
        <h6>
          <ul>
            <template v-for="(detail, index2) in flow.details" :key="index2">
              <li class="p-2">{{ detail }}</li>
            </template>
            <a v-if="flow.link != undefined" class="p-2" :href="`#${flow.link}`">링크</a>
          </ul>
        </h6>
      </template>
    </ol>
    <hr />

    <!-- 깃 예시 템플릿 -->
    <h2 id="ex" class="pb-3">Git Examples</h2>
    <ol>
      <template v-for="(ex, index) in gitEx" :key="index">
        <h4 class="ms-3">
          <li>
            {{ ex.head }}
          </li>
        </h4>
        <ul>
          <li>
            <p>사용방법 : 지라 Issue번호 복사 후 &lt;message&gt; 내부에 작성</p>
            <div>head )</div>
            <p>
              <span :id="`ms1-${index + 1}`">
                <!-- 복사할 메시지 작성 -->
                {{ ex.message1 }}
              </span>
              <button class="btn btn-primary p-2 pt-0 pb-1 ms-2" @click="getCopy(`ms1-${index + 1}`)">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="12"
                  height="12"
                  fill="currentColor"
                  class="bi bi-copy"
                  viewBox="0 0 16 16"
                >
                  <path
                    fill-rule="evenodd"
                    d="M4 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2zm2-1a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM2 5a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1v-1h1v1a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h1v1z"
                  />
                </svg>
              </button>
            </p>
            <div>body )</div>
            <p>
              <span :id="`ms2-${index + 1}`">
                <!-- 복사할 메시지 작성 -->
                {{ ex.message2 }}
              </span>
              <button class="btn btn-primary p-2 pt-0 pb-1 ms-2" @click="getCopy(`ms2-${index + 1}`)">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="12"
                  height="12"
                  fill="currentColor"
                  class="bi bi-copy"
                  viewBox="0 0 16 16"
                >
                  <path
                    fill-rule="evenodd"
                    d="M4 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2zm2-1a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM2 5a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1v-1h1v1a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h1v1z"
                  />
                </svg>
              </button>
            </p>
          </li>
        </ul>
      </template>
    </ol>
    <hr />

    <h1 id="cheat" class="pb-3">Git Cheat Sheet</h1>
    <p>
      GitHub은 일반적으로 많이 사용되는 저장소 관련 작업을 위한 데스크톱 클라이언트와 함께, 더 복잡한 작업을 위해
      자동으로 업데이트되는 Git command line 에디션을 제공합니다.
    </p>

    <h2 id="환경-설정">환경 설정</h2>
    <p>모든 로컬 저장소에 적용할 사용자 정보를 설정합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git config --global user.name "[name]"</code>
    </p>

    <p>자신이 생성한 커밋(commit)에 들어갈 이름을 설정합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git config --global user.email "[email address]"</code>
    </p>

    <p>자신이 생성한 커밋에 들어갈 이메일 주소를 설정합니다</p>

    <h2 id="저장소-생성하기">저장소 생성하기</h2>
    <p>새로운 저장소를 만들거나, 다른 저장소의 URL을 이용해 저장소를 복사합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git init [project-name]</code>
    </p>

    <p>새로운 로컬 저장소를 생성하고 이름을 정합니다.</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git clone [url]</code>
    </p>

    <p>기존 프로젝트의 모든 커밋 내역을 가져와 저장소를 만듭니다</p>

    <h2 id="변경점을-저장하기">변경점을 저장하기</h2>
    <p>수정 사항을 검토하고 커밋을 생성합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git status</code>
    </p>

    <p>커밋할 수 있는 새로운 파일과 수정된 파일의 목록을 보여줍니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git diff</code>
    </p>

    <p>수정하였으나 아직 stage하지 않은 파일의 변경점을 보여줍니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git add [file]</code>
    </p>

    <p>커밋을 준비하기 위해 파일을 stage합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git diff --staged</code>
    </p>

    <p>stage하였으나 아직 커밋하지 않은 파일과 가장 최근에 커밋한 파일을 비교합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git reset [file]</code>
    </p>

    <p>파일의 내용은 유지한 채로 stage한 내역만을 제거합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git commit -m"[descriptive message]"</code>
    </p>

    <p>stage한 내용을 커밋으로 영구히 저장합니다</p>

    <h2 id="변경점을-묶어-관리하기">변경점을 묶어 관리하기</h2>
    <p>일련의 커밋에 이름을 붙이고 여러 변경점을 합칩니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git branch</code>
    </p>

    <p>현재 저장소의 모든 로컬 브랜치를 보여줍니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git branch [branch-name]</code>
    </p>

    <p>새로운 브랜치를 생성합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git switch -c [branch-name]</code>
    </p>

    <p>특정 브랜치로 전환하고 워킹 디렉토리를 업데이트합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git merge [branch-name]</code>
    </p>

    <p>현재 브랜치에 특정 브랜치의 히스토리를 병합시킵니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git branch -d [branch-name]</code>
    </p>

    <p>브랜치를 삭제합니다</p>

    <h2 id="파일-이름-바꾸기">파일 이름 바꾸기</h2>
    <p>버전 관리 중인 파일을 옮기거나 삭제합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git rm [file]</code>
    </p>

    <p>워킹 디렉토리에 있는 파일을 제거하고 삭제한 내역을 stage합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git rm --cached [file]</code>
    </p>

    <p>현재 파일은 그대로 두고 버전 관리 체계에서만 제거합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git mv [file-original] [file-renamed]</code>
    </p>

    <p>파일명을 변경하고 해당 내역을 stage합니다</p>

    <h2 id="특정-파일을-저장소에서-제외하기">특정 파일을 저장소에서 제외하기</h2>
    <p>임시 파일과 경로를 제외시킵니다</p>

    <div class="language-plaintext highlighter-rouge">
      <div class="highlight">
        <pre class="highlight"><code>*.log
      build/
      temp-*
      </code></pre>
      </div>
    </div>

    <p>
      <code class="language-plaintext highlighter-rouge">.gitignore</code>이라는 텍스트 파일에 제외할 문자열 패턴을
      지정하여 실수로 엉뚱한 파일이나 경로가 저장되지 않게 방지할 수 있습니다
    </p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git ls-files --others --ignored --exclude-standard</code>
    </p>

    <p>이 프로젝트에서 제외된 모든 파일을 보여줍니다</p>

    <h2 id="변경점-일부분을-저장하기">변경점 일부분을 저장하기</h2>
    <p>불완전한 변경 사항을 임시로 저장하거나 복원합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git stash</code>
    </p>

    <p>버전 관리 중인 모든 파일의 변경점을 임시로 저장합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git stash pop</code>
    </p>

    <p>가장 최근에 임시 저장한 내용을 복원합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git stash list</code>
    </p>

    <p>임시 저장된 모든 변경점의 목록을 보여줍니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git stash drop</code>
    </p>

    <p>가장 최근에 임시 저장한 내용을 지웁니다</p>

    <h2 id="변경-기록-검토">변경 기록 검토</h2>
    <p>프로젝트 내 파일의 변경 기록을 살펴보고 검토합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git log</code>
    </p>

    <p>현재 브랜치의 변경 기록을 보여줍니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git log --follow [file]</code>
    </p>

    <p>특정 파일의 변경 기록을 보여줍니다(파일명 변경 포함)</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git diff [first-branch]...[second-branch]</code>
    </p>

    <p>두 브랜치의 차이점을 비교합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git show [commit]</code>
    </p>

    <p>특정 커밋에 포함된 변경 사항과 메타데이터를 표시합니다</p>

    <h2 id="커밋-되돌리기">커밋 되돌리기</h2>
    <p>실수한 내용을 지우고 기록을 바꿉니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git reset [commit]</code>
    </p>

    <p>현재 파일의 변경 사항은 그대로 두고 ‘[커밋]’ 이후의 모든 커밋 내용을 되돌립니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git reset --hard [commit]</code>
    </p>

    <p>모든 변경점과 기록을 버리고 특정 커밋으로 되돌아갑니다</p>

    <h2 id="변경점을-동기화하기">변경점을 동기화하기</h2>
    <p>원격 저장소(의 URL)을 등록하고 저장소 기록을 주고받습니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git fetch [remote]</code>
    </p>

    <p>원격 저장소로부터 모든 기록을 받아옵니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git merge [remote]/[branch]</code>
    </p>

    <p>원격 브랜치를 현재 사용 중인 로컬 브랜치와 병합합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git push [remote] [branch]</code>
    </p>

    <p>모든 로컬 브랜치의 변경점을 GitHub에 업로드합니다</p>

    <p>
      <code class="language-plaintext highlighter-rouge">$ git pull</code>
    </p>

    <p>북마크된 원격 브랜치의 기록을 다운로드하여 변경점을 병합합니다</p>
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
