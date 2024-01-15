<script setup></script>
<template>
  <h1 class="mt-3 mb-3">프로젝트 기획서(Project Proposal)</h1>
  <hr />
  <table class="table">
    <th scope="col">What</th>
    <td>
      <tr>
        <td class="fixedCol">프로젝트명</td>
        <td>코치미(CoachME)</td>
      </tr>
      <tr>
        <td class="fixedCol">핵심기능</td>
        <td>일상에서 코칭을 받을 수 있는 화상 플랫폼</td>
      </tr>
      <tr>
        <td class="fixedCol" style="vertical-align: middle">팀구성</td>
        <td>
          <div id="team-carousel" class="carousel slide">
            <div class="carousel-indicators">
              <button
                type="button"
                data-bs-target="#team-carousel"
                data-bs-slide-to="0"
                aria-label="Slide 1"
                aria-current="true"
                class="active"
              ></button>
              <button type="button" data-bs-target="#team-carousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
              <button type="button" data-bs-target="#team-carousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
              <button type="button" data-bs-target="#team-carousel" data-bs-slide-to="3" aria-label="Slide 4"></button>
              <button type="button" data-bs-target="#team-carousel" data-bs-slide-to="4" aria-label="Slide 5"></button>
              <button type="button" data-bs-target="#team-carousel" data-bs-slide-to="5" aria-label="Slide 6"></button>
            </div>

            <!-- 팀원 정보 작성 -->
            <!-- 남상엽 -->
            <div class="carousel-inner">
              <div class="carousel-item active">
                <div class="card bg-secondary" style="width: 100%">
                  <div class="mt-2">
                    <div class="row">
                      <div class="col-4 text-center">
                        <img src="../assets/img/proposal/member/sangyeop.png" class="roundImg p-3" />
                        <h4 class="text-center text-white">남상엽</h4>
                      </div>
                      <div class="col d-flex text-white">
                        <div class="align-self-center">
                          <h5>역할 : 팀장, 프론트엔드, 기획, 프로젝트 관리</h5>
                          <p>다짐 : 팀장으로서 성공할 수 있는 프로젝트를 이끌겠습니다.</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- 이혜지 -->
              <div class="carousel-item">
                <div class="card bg-secondary" style="width: 100%">
                  <div class="mt-2">
                    <div class="row">
                      <div class="col-4 text-center">
                        <img src="../assets/img/proposal/member/hyeji.png" class="roundImg p-3" />
                        <h4 class="text-center text-white">이혜지</h4>
                      </div>
                      <div class="col d-flex text-white">
                        <div class="align-self-center">
                          <h5>역할 : 프론트엔드, FE팀장, 컴포넌트 제작</h5>
                          <p>다짐 : 적당히 넘기지 않고, 최선을 다하여 개발할 수 있도록 노력하겠습니다!</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- 김동현 -->
              <div class="carousel-item">
                <div class="card bg-secondary" style="width: 100%">
                  <div class="mt-2">
                    <div class="row">
                      <div class="col-4 text-center">
                        <img src="../assets/img/proposal/member/donghyeon.png" class="roundImg p-3" />
                        <h4 class="text-center text-white">김동현</h4>
                      </div>
                      <div class="col d-flex text-white">
                        <div class="align-self-center">
                          <h5>역할 : 프론트엔드, 회의록 관리, 레이아웃 설계</h5>
                          <p>다짐 : 팀원 모두가 즐겁게 할 수 있도록 밝은 모습으로 노력하겠습니다!</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- 박민아 -->
              <div class="carousel-item">
                <div class="card bg-secondary" style="width: 100%">
                  <div class="mt-2">
                    <div class="row">
                      <div class="col-4 text-center">
                        <img src="../assets/img/proposal/member/mina.png" class="roundImg p-3" />
                        <h4 class="text-center text-white">박민아</h4>
                      </div>
                      <div class="col d-flex text-white">
                        <div class="align-self-center">
                          <h5>역할 : 백엔드, 인프라, 발표</h5>
                          <p>다짐 : 많은 고민과 생각이 녹여진 프로젝트를 만들기 위해 노력하겠습니다.</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- 이준학 -->
              <div class="carousel-item">
                <div class="card bg-secondary" style="width: 100%">
                  <div class="mt-2">
                    <div class="row">
                      <div class="col-4 text-center">
                        <img src="../assets/img/proposal/member/joonhak.png" class="roundImg p-3" />
                        <h4 class="text-center text-white">이준학</h4>
                      </div>
                      <div class="col d-flex text-white">
                        <div class="align-self-center">
                          <h5>역할 : 백엔드, BE팀장, API설계</h5>
                          <p>다짐 : 효율적이고 안정적인 백엔드를 제공하겠습니다.</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- 김지원 -->
              <div class="carousel-item">
                <div class="card bg-secondary" style="width: 100%">
                  <div class="mt-2">
                    <div class="row">
                      <div class="col-4 text-center">
                        <img src="../assets/img/proposal/member/jiwon.png" class="roundImg p-3" />
                        <h4 class="text-center text-white">김지원</h4>
                      </div>
                      <div class="col d-flex text-white">
                        <div class="align-self-center">
                          <h5>역할 : 백엔드, 인프라, 지라 관리</h5>
                          <p>다짐 : 백엔드를 잘 설계하고 나아가 최적화 하겠습니다.</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <button class="carousel-control-prev" type="button" data-bs-target="#team-carousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
              </button>
              <button class="carousel-control-next" type="button" data-bs-target="#team-carousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
              </button>
            </div>
          </div>
        </td>
      </tr>
      <tr>
        <td class="fixedCol">설계도</td>
        <td><a href="/wireframe">화면명세서</a></td>
      </tr>
    </td>
    <th scope="col">Subject</th>
    <td>
      <tr>
        <td class="fixedCol">기획의도</td>
        <td>
          전문가와 비전문가를 연결하여 서로의 니즈를 만족시키고, 이러한 과정에서 서비스 이용자들의 편의를 제공하기 위함
        </td>
      </tr>
      <tr>
        <td class="fixedCol">요약</td>
        <td>사용자가 시공간의 제약없이 본인이 필요한 서비스를 제공하고, 제공받을 수 있는 플랫폼 제작</td>
      </tr>
    </td>
    <th scope="col">Problem</th>
    <td>
      <tr>
        <td class="fixedCol" style="vertical-align: middle">현실태</td>
        <td>
          <ol class="list-group list-group-flush list-group-numbered">
            <li class="list-group-item">본인의 기술과 능력을 원격으로 서비스할 수 있는 플랫폼 부재</li>
            <li class="list-group-item">전문가와 비전문가의 연결은 숨고(soomgo)와 같은 플랫폼을 통해 가능</li>
            <li class="list-group-item">그러나 이러한 관계는 보통 한번의 서비스 이후 단절됨(1회성)</li>
            <li class="list-group-item">
              또한, 일상에서 접하는 문제는 필요한 일정 시점이 지나면 니즈가 자연스레 사라지거나 해소되는 특성이 있음
            </li>
            <li class="list-group-item">실제 서비스를 위해서 별도의 일정을 조율하는 등 시간적 제약 발생</li>
            <li class="list-group-item">또한, 서비스 제공자(이하 판매자)와 멀리 떨어진 경우 공간적 제약 발생</li>
          </ol>
        </td>
      </tr>
      <tr>
        <td class="fixedCol" style="vertical-align: middle">니즈 분석</td>
        <td>
          <ol class="list-group list-group-flush list-group-numbered">
            <li class="list-group-item">
              판매자는 본인의 지적 재산을 홍보하고 판매하며 서비스 소비자(이하 소비자)와 긴밀하게 연결될 수 있는
              플랫폼에 대한 욕구가 있음
            </li>
            <li class="list-group-item">소비자는 판매자의 서비스를 불편한 상황에서 즉각 제공받고 싶은 욕구가 있음</li>
          </ol>
        </td>
      </tr>
    </td>
    <th scope="col">Solution</th>
    <td>
      <tr>
        <td class="fixedCol" style="vertical-align: middle">문제 해결</td>
        <td>
          <ol class="list-group list-group-flush list-group-numbered">
            <li class="list-group-item">판매자는 소비자에게 유료 실시간 강의를 제공</li>
            <li class="list-group-item">판매자는 소비자에게 유료 화상에 대한 녹화본을 제공</li>
            <li class="list-group-item">
              판매자는 본인을 홍보하기 위한 영상과 유료 화상에 대한 녹화본을 편집하여 업로드
            </li>
            <li class="list-group-item">소비자는 판매자를 직접 선정하여 컨택(1:1 채팅)할 수 있음</li>
            <li class="list-group-item">소비자는 판매자의 라이브 강의에 직접 참여하면서 어려움을 해소할 수 있음</li>
            <li class="list-group-item">
              소비자는 시간적 제약이나 장비의 부재 등으로 화상에 참여가 제한될 경우 녹화본을 시청할 수 있음
            </li>
          </ol>
        </td>
      </tr>
      <tr>
        <td class="fixedCol">구현</td>
        <td><a href="#">프로젝트 종료 후 설명영상 링크(예정)</a></td>
      </tr>
    </td>
    <th scope="col">Benefit</th>
    <td>
      <tr>
        <td class="fixedCol">비즈니스 모델</td>
        <td>소비자와 판매자의 연결을 중재하는 과정에서 발생하는 수익에 대한 커미션</td>
      </tr>
      <tr>
        <td class="fixedCol" style="vertical-align: middle">고객니즈 충족</td>
        <td>
          <ol class="list-group list-group-flush list-group-numbered">
            <li class="list-group-item">
              소비자는 라이브 및 녹화영상을 통해 발생하는 문제를 시간과 장소에 구애받지 않고 즉각 해소 가능
            </li>
            <li class="list-group-item">판매자는 본인의 능력과 지식을 활용해 자투리 시간에 수익을 창출할 수 있음</li>
          </ol>
        </td>
      </tr>
    </td>
  </table>
</template>
<style scoped>
th {
  font-size: 1.5rem;
}
a {
  text-decoration: none;
}
.fixedCol {
  font-weight: bold;
  width: 200px;
  min-width: 200px;
  max-width: 200px;
}
.roundImg {
  width: 10rem;
  height: 10rem;
  border-radius: 10rem;
  display: inline-block;
}
</style>
