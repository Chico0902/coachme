<!-- layout/MainLayout.vue -->

<template>
  <div class = "all">
    <div class="main-layout">
      <div class ="nav-bar">
        <p>Navigation Bar</p>
      </div>
      <div class="crusell">
        크루셀
      </div>
      
      <div class="search">
        <div class="title">
          타이틀
        </div>
        검색창
      </div>
      <div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  // 필요한 경우 데이터, 메서드 등을 추가할 수 있습니다.
};
</script>

<style scoped>
/* 스타일 추가 */

.all{
  display: flex;
  justify-content: center;
}
.main-layout {
  width: 1280px;
  height:100vh;
  background: #D9D9D9;
  display: flex;
  justify-content: center;
  overflow-y: auto;

}

.nav-bar{
  width: 1146px;
  height: 50px;
  position: absolute;
  right: 112px;
  top: 32px;
  justify-content: center;
  text-align: center;
}

.crusell {
  width: 1150px;
  height: 400px;
  position: absolute;
  left: 128px;
  top: 215px;
  flex-shrink: 0;
  border-radius: 45px;
  background: #FFF;
  justify-content: center;
  text-align: center;
}

.title{
  width: 315px;
  height: 71px;

}

.search{
  width: 821px;
  height: 290px;
  position: absolute;
  right: 281px;
  top: 697px;

}
/* 나머지 스타일들을 필요에 따라 추가 */
</style>
