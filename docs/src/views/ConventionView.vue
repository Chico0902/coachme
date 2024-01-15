<script setup>
const teamConventions = [
  {
    title: '디렉토리(directory), 파일(file)',
    details: [
      {
        rule: '디렉토리는 소문자와 하이픈(-)으로만 구성한다.',
        ex: 'my-directory'
      },
      {
        rule: '파일명은 소문자와 언더바(_)로만 구성한다.',
        ex: 'my_file'
      }
    ]
  },
  {
    title: '패키지(package), 클래스(class)',
    details: [
      {
        rule: '패키지명은 소문자로만 구성한다.',
        ex: 'mypackage'
      },
      {
        rule: '클래스명은 파스칼 표기법(PascalCase)으로 작성한다.',
        ex1: 'LoginResponseDto',
        ex2: 'MemberController'
      }
    ]
  },
  {
    title: '함수(Function) / 메서드(Method)',
    details: [
      {
        rule: '함수 / 메서드명은 동사 및 전치사로 작성한다.',
        ex1: 'playBasketball();',
        ex2: 'toString();'
      },
      {
        rule: '클래스명은 파스칼 표기법(PascalCase)으로 작성한다.',
        ex1: 'LoginResponseDto',
        ex2: 'MemberController'
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
        ex1: 'myList.forEach((l) => console.log(l));',
        ex2: 'StringTokenizer st = new StringTokenizer();'
      }
    ]
  }
]
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
    <h2 class="mt-3 mb-3">코드 컨벤션(팀)</h2>
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
                <code>{{ detail.ex }}</code>
              </template>
              <template v-if="detail.ex1">
                <p class="mb-0">[ JS 예시 ]</p>
                <code>{{ detail.ex1 }}</code>
              </template>
              <template v-if="detail.ex2">
                <p class="mt-3 mb-0">[ Java 예시 ]</p>
                <code>{{ detail.ex2 }}</code>
              </template>
            </li>
          </template>
        </ul>
      </template>
    </ol>
    <hr />

    <h2 id="team-convention" class="pb-3">코드 컨벤션(팀)</h2>
    <ol>
      <!-- boolean -->
      <li>
        <h4>boolean</h4>

        <ul>
          <li>
            <p>boolean은 is/has 등 be동사로 시작해야 한다.</p>
          </li>
        </ul>
      </li>
      <hr />
      <!-- 들여쓰기 -->
      <li>
        <h4>들여쓰기 : 탭 vs 띄어쓰기</h4>

        <ul>
          <li>
            <p>들여쓰기는 띄어쓰기 2칸으로 통일한다.</p>
          </li>
          <li>
            <p>필요시 에디터에서 탭 키를 띄어쓰기 2칸으로 수정해서 사용한다.</p>
          </li>
        </ul>
      </li>
      <hr />
      <!-- 주석 -->
      <li>
        <h4>주석 : // vs /** */</h4>

        <ul>
          <li>
            <p>// : 1줄 주석 작성시에만 사용한다.</p>
          </li>
          <li>
            <p>/** */ : 2줄 이상 주석은 반드시 해당 주석을 사용한다.</p>
          </li>
        </ul>
      </li>
      <hr />
      <!-- 중복 -->
      <li>
        <h4>중복 : 클래스 내부 메서드, 변수 등</h4>

        <ul>
          <li>
            <div>중복 클래스는 생략한다.</div>
            <div>ex)</div>
            <div>X) class Car {</div>
            <div style="padding-left: 2em">boolean isCarExtend;</div>
            <div style="padding-left: 1em">}</div>
            <div>O) class Car {</div>
            <div style="padding-left: 2em">boolean isExtend;</div>
            <div style="padding-left: 1em">}</div>
          </li>
          <li>
            <p>기타 중복 규칙은 FE/BE 별로 정한다.</p>
          </li>
        </ul>
      </li>
      <hr />
      <!-- etc -->
      <li>
        <h4>기타</h4>

        <ul>
          <li>
            <p>클래스명, 변수, 메서드, 반복문, 조건문 등은 프론트 / 백엔드끼리 논의</p>
          </li>
        </ul>
      </li>
    </ol>
    <ul></ul>
  </div>
</template>
