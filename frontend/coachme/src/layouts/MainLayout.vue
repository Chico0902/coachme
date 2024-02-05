
<template>
  <div class="all">
    <div class="main-layout">
      <div class="nav-bar">
        <p>Navigation Bar</p>
      </div>
      <div class="crusell-outside">
        <div class="crusell">크루셀</div>
      </div>
      <div class="search-outside">
        <div class="search-title">타이틀</div>
        <div class="search">
          검색창
        </div>
      </div>
      <div class="category-outside">
        <div class="category-title">카테고리 문구
          </div>
        <div class="category-imoji">
          카테고리 이모지          
        </div>
      </div>
      <div class="coach-outside">
        <div class="coach-title">
        코치
        </div>
        <div class="coach-card-outside">
        <div class="coach-card">코치카드</div>
        <div class="coach-card">코치카드</div>
        <div class="coach-card">코치카드</div>
      </div>
      </div>
      
      <div class="coaching-outside">
        <div class="coaching-title">
        코칭
        </div>
        <div class="coaching-card-outside">
        <div class="coaching-card">코칭카드</div>
        <div class="coaching-card">코칭카드</div>
        <div class="coaching-card">코칭카드</div>
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
  margin-bottom: 100px;
  background-color: aqua;
  justify-content: center;
  text-align: center;
}

.crusell-outside {
  display: flex;
  justify-content: center;
}

.crusell {
  width: 1150px;
  height: 400px;
  flex-shrink: 0;
  border-radius: 45px;
  background: #fff;
  justify-content: center;
  text-align: center;
  margin: 50px;
}

.search-title {
  width: 70%;
  height: 71px;
  background-color: tomato;
  margin: auto;
  /* display: block; */
}

.search-outside {
  display: flex;
  justify-content: center;
  flex-direction: column;
  margin: 200px;
}
.search {
  width: 70%;
  height: 290px;
  background-color: skyblue;
  margin: auto;
  
}

.category-outside {
  display: flex;
  justify-content: center;
  flex-direction: column;
  margin: 300PX;
}
.category-imoji {
  width: 80%;
  height: 150px;
  background-color: tomato;
  margin: auto;
  /* display: block; */
}

.category-title {
  width: 80%;
  height: 80px;
  background-color: yellow;
  margin: auto;
}

.coach-card-outside {
  background-color: cornflowerblue;
  display: flex;
  justify-content: space-around;
}
.coach-outside{
  width: 80%;
  height:370px;
  background-color: greenyellow;
  margin: 100px auto;
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.coach-card {
  width: 200px;
  height: 200px;
  background-color: burlywood;
  margin: 30px;
}
.coach-title {
  height: 70px;
  width: 70%;
  background-color: rebeccapurple;
  }

.coaching-card-outside {
  background-color: cornflowerblue;
  display: flex;
  justify-content: space-around;
}
.coaching-outside{
  width: 80%;
  height:370px;
  background-color: greenyellow;
  margin: 100px auto;
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.coaching-card {
  width: 200px;
  height: 200px;
  background-color: burlywood;
  margin: 30px;
}
.coaching-title {
  height: 70px;
  width: 70%;
  background-color: rebeccapurple;
  }

.footer {
  width: 100%;
  height: 50px;
  background-color: #333;
  color: white;
  text-align: center;
}

</style>
