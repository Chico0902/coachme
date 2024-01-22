
<template>
  <div class="all">
    <div class="main-layout">
      <div class="nav-bar">
        <p>Navigation Bar</p>
      </div>
      <div class="signup-outside">
        <div class="signup-title">
          회원가입
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
          <div class="name-input">
            <div class="name-input-title">
              이름
            </div>
            <div class="name-input-box">
              box
            </div>
          </div>
          <div class="nickname-input">
            <div class="nickname-input-title">
              닉네임
            </div>
            <div class="nickname-input-box">
              box
            </div>
          </div>
          <div class="email-input">
            <div class="email-input-title">
              이메일
            </div>
            <div class="email-input-box">
              box
            </div>
          </div>
        
        </div>
        <div class="login-and-new">
        <div class="login-button">
          회원가입하기
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

.signup-outside {
  background-color: white;
  width: 500px;
  height: 600px;
  margin: 100px auto;
  border-radius: 5%;
  display: flex;
  /* justify-content: center; */
  flex-direction: column;
}

.signup-title {
  background-color: lightblue;
  width: 50%;
  margin: 50px auto;
}
.login-input {
  background-color: aquamarine;
  width: 60%;
  height: 60%;
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

.name-input {
  background-color: slateblue;
  width: 80%;
  margin: auto;
}

.name-input-title {
  background-color: yellowgreen;
  width: 100%;
  margin: auto;
}
.name-input-box {
  background-color:violet;
}

.nickname-input {
  background-color: slateblue;
  width: 80%;
  margin: auto;
}

.nickname-input-title {
  background-color: yellowgreen;
  width: 100%;
  margin: auto;
}
.nickname-input-box {
  background-color:violet;
}

.email-input {
  background-color: slateblue;
  width: 80%;
  margin: auto;
}

.email-input-title {
  background-color: yellowgreen;
  width: 100%;
  margin: auto;
}
.email-input-box {
  background-color:violet;
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
