<script setup>
const teamConventions = [
  {
    title: '디렉토리(directory), 파일(file), 패키지(package), 클래스(class)',
    details: [
      {
        rule: '디렉토리는 소문자와 하이픈(-)으로만 구성한다.',
        ex: 'my-directory'
      },
      {
        rule: '파일명은 소문자와 언더바(_)로만 구성한다. * 특정 파일형식 변경 필요시 내부 토의',
        ex: 'my_file'
      },
      {
        rule: '패키지명은 소문자로만 구성한다.',
        ex: 'mypackage'
      },
      {
        rule: '클래스명은 파스칼 표기법(PascalCase)으로 작성한다.',
        ex1: 'export default class LoginResponseDto{}',
        ex2: 'public class MemberController{}'
      }
    ]
  },
  {
    title: '함수(Function) / 메서드(Method)',
    details: [
      {
        rule: '함수 / 메서드명은 동사 및 전치사로 작성한다.',
        ex1: `playBasketball(ball){
  return ball - 1;
};`,
        ex2: `toString();`
      }
    ]
  },
  {
    title: '변수(Variables)',
    details: [
      {
        rule: 'boolean : is/has 등의 동사로 시작해야 한다.',
        ex1: 'const isStarted = true;',
        ex2: 'boolean hasInnerText = true;'
      },
      {
        rule: '1글자 이름은 메서드 이상 스코프에서는 사용하지 않는다.',
        ex1: `// forEach 내부에서만 존재하는 스코프이므로 1글자 허용
myList.forEach((l) => console.log(l));`,
        ex2: `public static void main() {
  //  main 메서드 내부에서 계속 존재하는 스코프이므로 2글자 이상 사용
  StringTokenizer st = new StringTokenizer();
}`
      }
    ]
  },
  {
    title: '주석(Comment)',
    details: [
      {
        rule: '주석은 1줄 주석과 여러줄 주석을 구분하여 사용한다.',
        ex1: '// 한줄 주석은 한줄만 사용!',
        ex2: `/**
 * 2줄 이상 작성시에는
 * 여러줄 주석 사용!
 */`
      }
    ]
  },
  {
    title: '중복(Duplicate)',
    details: [
      {
        rule: '패키지, 클래스 등에서 중복되는 이름은 생략한다.',
        ex: `// bad example
class Car {
  // Car 중복
  boolean isCarExtend;
}

// good example
class Car {
  boolean isExtend;
}`
      }
    ]
  }
]
const frontTree = `
(root)coachme
    ├── .env : 환경변수 저장(프로젝트 내부적으로 사용하면서 노출하고 싶지 않은 전역변수)
    ├── .eslintrc.cjs : Eslint 설정파일(팀 컨벤션에 맞게 설정 변경)
    ├── .gitignore : Git에서 추적하지 않는 파일 설정
    ├── .prettierrc.json : Prettier 설정파일(팀 컨벤션에 맞게 설정 변경)
    ├── .vscode : VsCode 설정정보 변경(팀 컨벤션에 맞게 설정 변경)
    │   └── extensions.json : 프로젝트에 필요한 Extensions 관리
    ├── README.md : 프론트엔드 공통 리드미 문서
    ├── index.html : 메인 app 탑재 html(SPA)
    ├── jsconfig.json : js 컴파일 옵션 설정(es6 vs common.js, 파일 경로지정)
    ├── package-lock.json : 의존성 버전관리 파일
    ├── package.json : 의존성 관리 파일(npm)
    ├── public : 정적으로 빌드 시 특정파일 포함 가능
    │   └── favicon.ico : 파비콘 아이콘
    ├── src : 앱 소스파일 관리
    │   ├── App.vue : main.js에서 마운트되는 메인 어플리케이션
    │   ├── assets : 이미지, css, 아이콘 등 저장
    │   │   └── ex) my_images.png
    │   ├── components : 커스텀 컴포넌트 파일 저장
    │   │   └── ex) MyCustomButton.vue : 커스텀 버튼 컴포넌트
    │   ├── layouts : 페이지 레이아웃 설정
    │   │   └── ex) MainLayout.vue : 메인 레이아웃
    │   ├── main.js : App.vue를 생성하고 필요한 하위 앱들을 탑재한 후 마운트하는 js파일
    │   ├── middlewares : router에 필요한 미들웨어 저장(특정 요청에 대한 필터링 작업)
    │   ├── router : vue-router 모음 경로
    │   │   ├── index.js : 라우터 세팅정보, url 매핑정보 등으로로 router 생성
    │   │   └── indexes : 라우터 url mapping에 사용할 인덱스 모음
    │   │       └── ex) test-index.js : 테스트를 위한 경로정보 모음
    │   ├── stores : 피니아 저장소 모음
    │   │   └── ex) member-store.js : 로그인한 멤버정보를 저장하는 저장소(id, 토큰 등) 
    │   ├── tests : 테스트를 위한 각종 요소 모음
    │   │   ├── components : 테스트를 위한 컴포넌트 모음
    │   │   ├── mocks : mocking(가짜) 서버 / 워커 생성 경로
    │   │   │   ├── worker.js : 가짜 서버 설정파일
    │   │   │   └── handlers.js : 가짜 서버 Request Handler
    │   │   └──  views : 테스트를 위한 view 모음
    │   ├── utils : 컴포넌트에서 활용가능한 js파일 모음
    │   │   ├── api : api호출 시 필요한 js 파일 모음
    │   │   │   ├── dto: dto 파일 모음
    │   │   │   │   └── ex) member-dto.js : member API 호출 시 필요한 dto
    │   │   │   ├── http-commons.js : 기본 http요청 Axios 설정 파일(base url, header 등)
    │   │   │   └── ex) member-api.js : member API를 호출하는 Axios 함수
    │   │   ├── functions : 컴포넌트에 작성에 필요한 js(component function) 정의 및 테스트
    │   │   │   ├── __test__ : component function을 테스트하기 위한 vitest 단위테스트 파일 작성
    │   │   │   │   └── ex) login.spec.js : 로그인을 위한 함수 테스트코드
    │   │   │   └── ex) login.js : 로그인 컴포넌트를 위한 함수
    │   └── views : 라우터 뷰를 통해 변경되는 화면 요소
    │       └── ex) HomeView.vue : 메인페이지 라우터 뷰
    ├── vite.config.js : Vite(비트) 설정정보 저장 - 포트주소, 
    └── vitest.config.js : Vitest 설정정보 저장
`
</script>
<template>
  <div id="convention">
    <h1 class="mt-3 mb-3">Conventions</h1>
    <hr />

    <h2>Index</h2>
    <ol>
      <h4>
        <li class="p-2">
          <a href="#version">프로젝트 버전관리</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#team-convention">코드 컨벤션(팀)</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#front-convention">코드 컨벤션(프론트엔드)</a>
        </li>
      </h4>
      <h4>
        <li class="p-2">
          <a href="#back-convention">코드 컨벤션(백엔드)</a>
        </li>
      </h4>
    </ol>
    <hr />

    <h2 id="version" class="pb-3">프로젝트 버전관리</h2>
    <ol>
      <h4><li>Vue.js : 3.3.11</li></h4>
      <h4><li>Quasar :</li></h4>
      <h4><li>Node.js : 20.11.0 LTS</li></h4>
      <h4><li>JAVA :</li></h4>
      <h4><li>SpringBoot :</li></h4>
      <h4><li>JPA :</li></h4>
    </ol>
    <hr />

    <!-- 코드 컨벤션(팀) -->
    <h2 id="convention" class="mt-3 mb-3">코드 컨벤션(팀)</h2>
    <ol>
      <template v-for="(convention, index) in teamConventions" :key="index">
        <h4 class="ps-3">
          <li>{{ convention.title }}</li>
        </h4>
        <ul class="mb-3">
          <template v-for="(detail, index2) in convention.details" :key="index2">
            <li class="p-2">
              <p>{{ detail.rule }}</p>
              <template v-if="detail.ex">
                <p class="mb-0">[ 예시 ]</p>
                <pre><code>{{ detail.ex }}</code></pre>
              </template>
              <template v-if="detail.ex1">
                <p class="mb-0">[ JS 예시 ]</p>
                <pre><code>{{ detail.ex1 }}</code></pre>
              </template>
              <template v-if="detail.ex2">
                <p class="mt-3 mb-0">[ Java 예시 ]</p>
                <pre><code>{{ detail.ex2 }}</code></pre>
              </template>
            </li>
          </template>
        </ul>
      </template>
    </ol>
    <hr />
  </div>

  <!-- 코드 컨벤션(프론트엔드) -->
  <h2 id="front-convention" class="mt-3 mb-3">프론트엔드 컨벤션</h2>
  <hr />
  <h3>디렉토리 구조</h3>
  <pre>{{ frontTree }}</pre>
  <!-- 코드 컨벤션(백엔드) -->
  <h2 id="back-convention" class="mt-3 mb-3">백엔드 컨벤션</h2>
  <hr />
</template>
<style scoped>
a {
  text-decoration: none;
}
code {
  color: green;
}
</style>
