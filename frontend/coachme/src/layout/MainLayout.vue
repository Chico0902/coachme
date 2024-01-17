<!-- layout/MainLayout.vue -->

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
        <div class="search">
          <div class="search-title">타이틀</div>
          검색창
        </div>
      </div>
      <div class="category-outside">
        <div class="category">
          <div class="category-title">카테고리 문구</div>
          카테고리 이모지
        </div>
      </div>
      <div class="coach">
        코치
        <div class="coach-detail">코치카드</div>
        <div class="coach-detail">코치카드</div>
        <div class="coach-detail">코치카드</div>
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
  width: 100%;
  height: 71px;
  background-color: tomato;
  /* display: block; */
}

.search-outside {
  display: flex;
  justify-content: center;
  margin: 50px;
}
.search {
  width: 821px;
  height: 290px;
  
 
  background-color: skyblue;
 
  
}

.category-outside {
  display: flex;
  justify-content: center;
}
.category {
  width: 100%;
  height: 71px;
  background-color: tomato;
  /* display: block; */
}

.category-title {
  width: 30%;
  height: 80px;
  background-color: yellow;
}
</style>
