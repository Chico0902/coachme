
<template>
  <div class="all">
    <div class="main-layout">
      <div class="nav-bar">
        <p>Navigation Bar</p>
      </div>
      <div class="mypage-outside">
      <div class="sidebar">
        사이드바
        <div class="sidebar-button">button</div>
        <div class="sidebar-button">button</div>
        <div class="sidebar-button">button</div>
        <div class="sidebar-button">button</div>
        <div class="sidebar-button">button</div>
        <div class="sidebar-button">button</div>
      </div>
      <div class="mainpage">
        메인
        <div class="coach-register-title-outside">
          <div class="coach-register-title">
            코치 등록
          </div>
          <div class="coach-register-title-detail">
              상세설명 어쩌구
          </div>
        </div>
        <div class="intropage-outside">
          <div class="intropage-title">
            제목
          </div>
          <div class="intropage-box">
            박스
          </div>
        </div>
        <div class="career-outside">
          <div class="career-title">
            제목
          </div>
          <div class="career-box">
            박스
          </div>
        </div>
        <div class="coaching-detail-outside">
          <div class="coaching-detail-title">
            제목
          </div>
          <div class="coaching-detail-box">
            박스
          </div>
        </div>
        <div class="curriculum-outside">
          <div class="curriculum-title">
            제목
          </div>
          <div class="curriculum-box">
            박스
          </div>
        </div>
        
      </div>
    </div>
      
      <div class="footer">
        푸터자리
      </div>
    </div>
  </div>
</template>

<script>
export default {}
</script>

<style scoped>


.all {
  display: flex;
  justify-content: center;
}
.main-layout {
  width: 1280px;
  min-height: 100vh;
  background: #d9d9d9;
  justify-content: center;
  align-items: flex-start;
  overflow-y: auto;
  position: relative;
}

.nav-bar {
  width: 100%;
  height: 50px;
  margin-bottom: 50px;
  background-color: aqua;
  justify-content: center;
  text-align: center;
}

.mypage-outside{
  background-color: darkkhaki;
  display: flex;
  justify-content: space-between;
  
  
}
.sidebar {
  background-color: beige;
  width: 20%;
  min-height: 100%;
  margin: auto;
  border-radius: 5%;
  
}

.sidebar-button {
  background-color: tomato;
  width: 80%;
  height: 70px;
  border-radius: 5%;
  margin: 10px auto;  
}

.mainpage {
  background-color: aliceblue;
  width: 70%;
  border-radius: 5%;
  margin-right: 30px;
}

.coach-register-title-outside {
  background-color: dodgerblue;
  width: 80%;
  margin: auto;
}

.coach-register-title {
  background-color: violet;
  display: inline-block;
}

.coach-register-title-detail {
  background-color: tomato;
}

.intropage-outside{
  background-color: turquoise;
  margin: 20px auto;
  width: 80%;

}

.intropage-title{
  background-color: slateblue;
  display: inline-block;
}

.intropage-box{
  background-color: salmon;
}

.career-outside{
  background-color: turquoise;
  margin: 20px auto;
  width: 80%;
}

.career-title{
  background-color: slateblue;
  display: inline-block;
}

.career-box{
  background-color: salmon;
}

.coaching-detail-outside{
  background-color: turquoise;
  margin: 20px auto;
  width: 80%;
}

.coaching-detail-title{
  background-color: slateblue;
  display: inline-block;
}

.coaching-detail-box{
  background-color: salmon;
}

.curriculum-outside{
  background-color: turquoise;
  margin: 20px auto;
  width: 80%;
}

.curriculum-title{
  background-color: slateblue;
  display: inline-block;
}

.curriculum-box{
  background-color: salmon;
}

.footer {
  width: 100%;
  height: 50px;
  background-color: #333;
  color: white;
  text-align: center;
  bottom: 0;
  padding: 10px 0px;
}

</style>
