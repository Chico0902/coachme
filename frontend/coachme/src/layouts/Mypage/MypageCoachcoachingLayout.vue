
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
        <div class="deviderouter-outside">
          <div class="deviderouter">포트폴리오</div>
          <div class="deviderouter">코칭관리</div>
          <div class="deviderouter">영상관리</div>
        </div>
        <div class="coach-intro-outside">
          <div class="coach-intro">
            코치 소개전체
            <div class="coach-intro-title">
              코치 소개전체
            </div>
            <div class="coach-intro-img">
              이미지
            </div>
            <div class="coach-intro-box">
              box
            </div>
            
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

.deviderouter-outside {
  background-color: wheat;
  display: flex;
  justify-content: space-around;

}

.deviderouter {
  background-color: yellow;
  display: inline-block;
}

.coach-intro-outside {
  background-color: springgreen;
  border-radius: 5%;
  width: 80%;
  height: 60%;
  margin: 50px auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

.coach-intro {
  background-color: steelblue;
  width: 90%;
  height: 90%;
  margin: auto;

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
