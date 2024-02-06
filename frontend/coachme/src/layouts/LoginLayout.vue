
<template>
  <div class="all">
    <div class="main-layout">
      <div class="nav-bar">
        <p>Navigation Bar</p>
      </div>
      <div class="login-outside">
        <div class="login-title">
          로그인
        </div>
        <div class="login-input">
          <div class="id-input">
            <div class="id-input-title">
              아이디
            </div>
            <div class="id-input-box">
            box
          </div>
        </div>
        <div class="password-input">
          <div class="password-input-title">
            비밀번호
          </div>
          <div class="password-input-box">
            box
          </div>
        </div>
        </div>
        <div class="login-and-new">
        <div class="login-button">
          로그인하기
        </div>
        <div class="new-and-find">
          <div class="new">
            회원가입하기
          </div>
          <div class="find">
            비번찾기
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
  margin-bottom: 100px;
  background-color: aqua;
  justify-content: center;
  text-align: center;
}

.login-outside {
  background-color: white;
  width: 500px;
  height: 600px;
  margin: auto;
  border-radius: 5%;
  display: flex;
  /* justify-content: center; */
  flex-direction: column;
}

.login-title {
  background-color: lightblue;
  width: 50%;
  margin: 50px auto;
}
.login-input {
  background-color: aquamarine;
  width: 60%;
  height: 37%;
  margin: 50px auto;
  display: flex;
  flex-direction: column;
}
.id-input {
  background-color: slateblue;
  width: 80%;
  margin: auto;
}

.id-input-title {
  background-color: yellowgreen;
  width: 100%;
  margin: auto;
}
.id-input-box {
  background-color:violet;
}

.password-input {
  background-color: wheat;
  width: 80%;
  margin: auto;
}

.password-input-title{
  background-color: yellow;
  width: 100%;
  margin: auto;
}
.password-input-box {
  background-color:cadetblue;
}


.login-and-new {
  background-color: darkolivegreen;
  width: 50%;
  margin: auto;

}
.login-button {
  width: 50%;
  background-color: silver;
  margin: 10px auto 20px;
}

.new-and-find {
  background-color: thistle;
  margin: auto;
  justify-content: center;
  display: flex;
}
.new {
  background-color: chocolate;
  display: inline-block;
}

.find {
  background-color: skyblue;
  display: inline-block;
}

.footer {
  width: 100%;
  height: 50px;
  background-color: #333;
  color: white;
  text-align: center;
  padding: 10px 0px;
  bottom: 0;
}

</style>
