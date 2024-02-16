<script setup>
import CustomButton from '@/components/atoms/CustomButton.vue';

</script>
<template>
  <div class="resign">
    <div class="title">
      정말 탈퇴하시려구요..?
    </div>
  </div>
  <div class="btn-box">
    <div class="yes-btn">
      <button class="yes-yes">네 네</button>
  </div>
  <div class="no-btn">
    <button class="no-no">아니오 아니오</button>
  </div>
</div>
</template>
<style scoped lang="scss">
.resign {
  margin: 20vh;
}
.title {
  text-align: center;
  font-size: xx-large;
}
.btn-box {
  display: flex;
  justify-content: space-evenly;
}


  .no-no {
    font-weight: 600;
    color: #382b22;
    text-transform: uppercase;
    padding: 1.25em 2em;
    background: #fff0f0;
    border: 2px solid #b18597;
    border-radius: 0.75em;
    transform-style: preserve-3d;
    transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), background 150ms cubic-bezier(0, 0, 0.58, 1);
    &::before {
      position: absolute;
      content: '';
      width: 100%;
      height: 100%;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: #f9c4d2;
      border-radius: inherit;
      box-shadow: 0 0 0 2px #b18597, 0 0.625em 0 0 #ffe3e2;
      transform: translate3d(0, 0.75em, -1em);
      transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), box-shadow 150ms cubic-bezier(0, 0, 0.58, 1);
    }
    &:hover {
      background: #ffe9e9;
      transform: translate(0, 0.25em);
      &::before {
        box-shadow: 0 0 0 2px #b18597, 0 0.5em 0 0 #ffe3e2;
        transform: translate3d(0, 0.5em, -1em);
      }
    }
    &:active {
      background: #ffe9e9;
      transform: translate(0em, 0.75em);
      &::before {
        box-shadow: 0 0 0 2px #b18597, 0 0 #ffe3e2;
        transform: translate3d(0, 0, -1em);
      }
    }
  }

  .yes-yes {
    color: #808080;
    text-transform: uppercase;
    padding: 1.25em 2em;
    background: #bababa;
    border: 2px solid #2e2327;
    border-radius: 0.75em;
    transform-style: preserve-3d;
    transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), background 150ms cubic-bezier(0, 0, 0.58, 1);
    &::before {
      position: absolute;
      content: '';
      width: 100%;
      height: 100%;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background: #656565;
      border-radius: inherit;
      box-shadow: 0 0 0 2px #3f3f3f, 0 0.625em 0 0 #dcdcdc;
      transform: translate3d(0, 0.75em, -1em);
      transition: transform 150ms cubic-bezier(0, 0, 0.58, 1), box-shadow 150ms cubic-bezier(0, 0, 0.58, 1);
    }
    &:hover {
      background: #d3d3d3;
      transform: translate(0, 0.25em);
      &::before {
        box-shadow: 0 0 0 2px #414141, 0 0.5em 0 0 #d3d3d3;
        transform: translate3d(0, 0.5em, -1em);
      }
    }
    &:active {
      background: #c9c9c9;
      transform: translate(0em, 0.75em);
      &::before {
        box-shadow: 0 0 0 2px #5f5f5f, 0 0 #c2c2c2;
        transform: translate3d(0, 0, -1em);
      }
    }
  }
</style>
