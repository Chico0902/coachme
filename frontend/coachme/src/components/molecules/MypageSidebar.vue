<script setup>
import router from '@/router'
import { ref, onBeforeMount, watch } from 'vue'

const props = defineProps({ privilege: String })
const privilege = props.privilege
const changePrivilege = ref(true)
const sideButtons = ref([])
let showToggle

// 클릭하면 Button List에서 해당 버튼 색 변경하고 라우터링크 이동하는 로직
const changeView = (link, event) => {
  const clickedButton = event.target.innerText
  sideButtons.value.forEach((button) => {
    if (button.name === clickedButton) button.css = 'selected-button'
    else button.css = ''
  })
  router.push({ name: link })
}

// 코치일때 토글 누르면 버튼 변경
watch(
  () => changePrivilege.value,
  () => {
    switch (changePrivilege.value) {
      case true: {
        sideButtons.value = [
          { name: '포트폴리오', link: 'Desktop-5-2', css: 'selected-button' },
          { name: '코칭관리', link: 'Desktop-5-3' },
          { name: '라이브관리', link: 'Desktop-5-5' },
          { name: '영상관리', link: 'Desktop-5-7' }
        ]
        router.push({ name: 'Desktop-5-2' })
        break
      }
      case false: {
        sideButtons.value = [
          { name: '정보수정', link: 'Desktop-6', css: 'selected-button' },
          { name: '코칭일정', link: 'Desktop-7' },
          { name: '관심강의', link: 'Desktop-8' },
          { name: '영상보기', link: 'Desktop-9' },
          { name: '회원탈퇴', link: 'Desktop-10' }
        ]
        router.push({ name: 'Desktop-6' })
        break
      }
    }
  }
)

onBeforeMount(() => {
  switch (privilege) {
    case 'COAME': {
      showToggle = false
      sideButtons.value = [
        { name: '코치등록', link: 'Desktop-5-1', css: 'selected-button' },
        { name: '정보수정', link: 'Desktop-6' },
        { name: '코칭일정', link: 'Desktop-7' },
        { name: '내 강의', link: 'Desktop-8' },
        { name: '회원탈퇴', link: 'Desktop-10' }
      ]
      break
    }
    case 'COACH': {
      showToggle = true
      sideButtons.value = [
        { name: '포트폴리오', link: 'Desktop-5-2', css: 'selected-button' },
        { name: '코칭관리', link: 'Desktop-5-3' },
        { name: '라이브관리', link: 'Desktop-5-5' },
        { name: '영상관리', link: 'Desktop-5-7' }
      ]
      break
    }
    case 'ADMIN': {
      showToggle = false
      sideButtons.value = [
        { name: '회원목록', link: 'Desktop-11', css: 'selected-button' },
        { name: '권한요청', link: 'Desktop-12' }
      ]
      break
    }
  }
})
</script>
<template>
  <div class="sidebar">
    <q-toggle v-if="showToggle" color="primary" v-model="changePrivilege" icon="img:/assets/icons/1.png" size="100px" />
    <div
      v-for="button in sideButtons"
      :key="button.name"
      class="sidebar-button shadow-3"
      :class="button.css"
      @click="changeView(button.link, $event)"
    >
      <p>{{ button.name }}</p>
    </div>
  </div>
</template>
<style scoped>
p {
  margin: 0;
  font-size: 1rem;
  letter-spacing: 0.1rem;
}
.sidebar {
  width: 12%;
  height: 70vh;
  margin: auto;
  min-width: 90px;
}
.sidebar-button {
  width: 100%;
  height: 8vh;
  border-radius: 1rem;
  margin-top: 0;
  margin-bottom: 4.4vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.selected-button {
  background-color: #034c8c;
  opacity: 90%;
  color: #fff;
}
.sidebar-button:hover {
  background-color: #034c8c;
  opacity: 100%;
  color: #fff;
  cursor: pointer;
}
.side-toggle{
  display: flex;
  justify-content: center;
}
</style>
