<!-- footer 컴포넌트
  필요한 정보 없음
  필요한 내역을 적당히 수정하기
-->

<script setup></script>

<template>
  <div class="footer-container">
    <!-- 로고 -->
    <img src="../../assets/icons/2.png" width="159px" height="40px" />

    <!-- 정보 -->
    <div class="address">
      <p>서울 강남구 테헤란로 212 역삼 멀티캠퍼스 12층 403</p>
      <p>© SSAFY A403 All Rights Reserved.</p>
    </div>
  </div>
</template>

<style scoped>
.footer-container {
  display: flex;
  justify-content: center;
  background-color: #fcbf17;
}
img {
  margin-top: 3vh;
  margin-right: 2rem;
}
p:first-child {
  margin-top: 1.2rem;
  margin-bottom: 0;
}
p {
  margin-top: 0.5rem;
  margin-bottom: 0;
}
</style>
