<template>
  <div class="all">
    <div class="main-layout">
      <div class="nav-bar">
        <p>Navigation Bar</p>
      </div>
      <div class="find-outside">
        <div class="find-titlea">비밀번호찾기</div>
        <div class="find-input">
          <div class="id-input">
            <div class="id-input-title">아이디</div>
            <div class="id-input-box">box</div>
          </div>
          <div class="email-input">
            <div class="email-input-title">이메일</div>
            <div class="email-input-box">box</div>
          </div>
        </div>
        <div class="login-and-new">
          <div class="signup-button">비밀번호변경</div>
        </div>
      </div>
      <div class="footer">푸터자리</div>
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

.find-outside {
  background-color: white;
  width: 500px;
  height: 600px;
  margin: auto;
  border-radius: 5%;
  display: flex;
  /* justify-content: center; */
  flex-direction: column;
}

.find-title {
  background-color: lightblue;
  width: 50%;
  margin: 50px auto;
}
.find-input {
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
  background-color: violet;
}

.email-input {
  background-color: wheat;
  width: 80%;
  margin: auto;
}

.email-input-title {
  background-color: yellow;
  width: 100%;
  margin: auto;
}
.email-input-box {
  background-color: cadetblue;
}

.login-and-new {
  background-color: darkolivegreen;
  width: 50%;
  margin: auto;
}
.signup-button {
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
  padding: 10px 0px;
  bottom: 0;
}
</style>
